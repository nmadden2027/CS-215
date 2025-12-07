package main;

import javax.swing.*;

public class Main {
    
    public static void main(String[] args) {
        String[] options = {"Start Server", "Connect as Client", "Play Locally"};
        int choice = JOptionPane.showOptionDialog(
            null,
            "Choose mode:",
            "Chess Game",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            options,
            options[1]
        );
        
        if(choice == 0) {
            startServer();
        } else if(choice == 1) {
            startClient();
        } else if(choice == 2) {
            startLocalGame();
        }
    }
    
    private static void startServer() {
        JFrame serverFrame = new JFrame("Chess Server");
        JTextArea serverLog = new JTextArea(20, 50);
        serverLog.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(serverLog);
        serverFrame.add(scrollPane);
        serverFrame.pack();
        serverFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        serverFrame.setVisible(true);
        System.setOut(new java.io.PrintStream(new java.io.OutputStream() {
            @Override
            public void write(int b) {
                serverLog.append(String.valueOf((char) b));
                serverLog.setCaretPosition(serverLog.getDocument().getLength());
            }
        }));

        new Thread(() -> {
            try {
                server.ChessServer.main(null);
            } catch(Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
    
    private static void startClient() {
        String serverAddress = JOptionPane.showInputDialog(
            null,
            "Enter server address:",
            "localhost"
        );
      
        if(serverAddress == null || serverAddress.trim().isEmpty()) {
            serverAddress = "localhost";
        }
        
        JFrame window = new JFrame("Chess Game - Client");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        
        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);
        window.pack();
        
        window.setLocationRelativeTo(null);
        window.setVisible(true);

        gamePanel.connectToServer(serverAddress, 8888);
        gamePanel.launchGame();
    }
    
    private static void startLocalGame() {
        JFrame window = new JFrame("Chess Game - Local");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        
        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);
        window.pack();
        
        window.setLocationRelativeTo(null);
        window.setVisible(true);
        
        gamePanel.launchGame();
    }
}