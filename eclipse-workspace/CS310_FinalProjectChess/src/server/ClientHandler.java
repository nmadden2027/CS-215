package server;

import java.io.*;
import java.net.*;

import main.GamePanel;

public class ClientHandler extends Thread {
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private GamePanel panel;
    
    public ClientHandler(String host, int port, GamePanel panel) {
        this.panel = panel;
        try {
            socket = new Socket(host, port);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            start();
        } catch (Exception e) {
            System.err.println("Connection failed");
        }
    }
    
    public void run() {
        try {
            String msg;
            while ((msg = in.readLine()) != null) {
                handle(msg);
            }
        } catch (Exception e) {
            panel.handleConnectionLost();
        }
    }
    
    private void handle(String msg) {
        String[] parts = msg.split(":", 2);
        String cmd = parts[0];
        
        switch (cmd) {
            case "WAITING":
                panel.setStatus("Waiting for opponent...");
                break;
            case "MATCHED":
                panel.startNetworkGame(Integer.parseInt(parts[1]));
                break;
            case "MOVE":
                String[] m = msg.split(":");
                panel.applyOpponentMove(
                    Integer.parseInt(m[1]), 
                    Integer.parseInt(m[2]),
                    Integer.parseInt(m[3]), 
                    Integer.parseInt(m[4])
                );
                break;
            case "TURN":
                panel.setCurrentTurn(Integer.parseInt(parts[1]));
                break;
            case "GAME_OVER":
                panel.setStatus(parts[1]);
                panel.endGame();
                break;
            case "OPPONENT_DISCONNECTED":
                panel.setStatus("Opponent left. You win!");
                panel.endGame();
                break;
            case "ERROR":
                panel.setStatus("Error: " + parts[1]);
                break;
        }
    }
    
    public void sendMove(int fc, int fr, int tc, int tr) {
        out.println("MOVE:" + fc + ":" + fr + ":" + tc + ":" + tr);
    }

}