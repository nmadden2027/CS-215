package server;

import java.io.*;
import java.net.*;
import java.util.concurrent.*;

public class ChessServer {
    private static BlockingQueue<Client> waiting = new LinkedBlockingQueue<>();
    
    public static void main(String[] args) throws Exception {
        ServerSocket server = new ServerSocket(8888);
        System.out.println("Chess Server ready on port 8888");
        
        while (true) {
            Socket socket = server.accept();
            Client client = new Client(socket);
            Client opponent = waiting.poll();
            
            if (opponent == null) {
                waiting.add(client);
                client.send("WAITING");
            } else {
                new Game(opponent, client).start();
            }
        }
    }

    static class Game {
        Client white, black;
        int turn = 0; 
        
        Game(Client w, Client b) {
            white = w; 
            black = b;
            white.game = this;
            black.game = this;
            white.color = 0;
            black.color = 1;
        }
        
        void start() {
            white.send("MATCHED:0");
            black.send("MATCHED:1");
            new Thread(white).start();
            new Thread(black).start();
            broadcast("GAME_START");
        }
        synchronized boolean move(int color, String msg) {
            if (color != turn) return false;
            turn = 1 - turn; 
            broadcast(msg);
            broadcast("TURN:" + turn);
            return true;
        }
        
        void broadcast(String msg) {
            white.send(msg);
            black.send(msg);
        }
    }

    static class Client implements Runnable {
        Socket socket;
        PrintWriter out;
        BufferedReader in;
        Game game;
        int color;
        
        Client(Socket s) {
            socket = s;
            try {
                out = new PrintWriter(s.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            } catch (Exception e) {}
        }
        
        public void run() {
            try {
                String msg;
                while ((msg = in.readLine()) != null) {
                    if (msg.startsWith("MOVE:")) {
                        game.move(color, msg); 
                    }
                }
            } catch (Exception e) {
                waiting.remove(this);
                if (game != null) {
                    Client opp = (this == game.white) ? game.black : game.white;
                    opp.send("OPPONENT_DISCONNECTED");
                }
            }
        }
        
        void send(String msg) {
            if (out != null) out.println(msg);
        }
    }
}