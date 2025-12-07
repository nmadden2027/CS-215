package main;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;

import piece.Bishop;
import piece.King;
import piece.Knight;
import piece.Pawn;
import piece.Piece;
import piece.Queen;
import piece.Rook;
import server.ClientHandler;

public class GamePanel extends JPanel implements Runnable {
	
	public static final int WIDTH = 1100;
	public static final int HEIGHT = 800;
	final int FPS = 60;
	Thread gameThread;
	Board board = new Board();
	Mouse mouse = new Mouse();

	private ClientHandler networkManager;
	private boolean networkMode = false;
	private int myColor = -1; 
	private String statusMessage = "";
	
	public static ArrayList<Piece> pieces = new ArrayList<>();
	public static ArrayList<Piece> simPieces = new ArrayList<>();
	Piece activeP;

	public static final int White = 0;
	public static final int Black = 1;
	int currentColor = White;

	boolean canMove;
	boolean validSquare;
	boolean gameActive = true;
	
	public GamePanel() {
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setBackground(Color.black);
		addMouseMotionListener(mouse);
		addMouseListener(mouse);
		
		setPieces();
		copyPieces(pieces, simPieces);
	}
	
	public void connectToServer(String serverAddress, int port) {
		networkMode = true;
		networkManager = new ClientHandler(serverAddress, port, this);
		statusMessage = "Connecting...";
	}
	

	public void startNetworkGame(int assignedColor) {
		myColor = assignedColor;
		currentColor = White; 
		gameActive = true;
		
		String colorName = (myColor == White) ? "White" : "Black";
		statusMessage = "Game started! You are " + colorName;
		
		System.out.println("Network game started. My color: " + colorName);
	}
	

	public void applyOpponentMove(int fromCol, int fromRow, int toCol, int toRow) {
		Piece movingPiece = null;
		for(Piece p : pieces) {
			if(p.col == fromCol && p.row == fromRow) {
				movingPiece = p;
				break;
			}
		}
		
		if(movingPiece != null) {
			Piece captured = null;
			for(Piece p : pieces) {
				if(p.col == toCol && p.row == toRow && p != movingPiece) {
					captured = p;
					break;
				}
			}

			if(captured != null) {
				pieces.remove(captured);
				System.out.println("Captured piece at " + toCol + "," + toRow);
			}
			
			movingPiece.col = toCol;
			movingPiece.row = toRow;
			movingPiece.updatePosition();

			copyPieces(pieces, simPieces);

			if (Piece.isCheckmate(myColor)) {
				String opponentName = (myColor == White) ? "Black" : "White";
				statusMessage = "Checkmate! " + opponentName + " wins!";
				endGame();
			} else if (Piece.isKingInCheck(myColor)) {
				statusMessage = "You are in check!";
			}
			
			System.out.println("Applied opponent move: " + fromCol + "," + fromRow + " -> " + toCol + "," + toRow);
		} else {
			System.out.println("ERROR: Could not find piece at " + fromCol + "," + fromRow);
		}
	}
	
	public void setCurrentTurn(int color) {
		currentColor = color;
		String colorName = (color == White) ? "White" : "Black";
		
		if(networkMode && color == myColor) {
			statusMessage = "Your turn (" + colorName + ")";
		} else if(networkMode) {
			statusMessage = "Opponent's turn (" + colorName + ")";
		}
	}
	
	public void setStatus(String message) {
		this.statusMessage = message;
	}
	
	public void endGame() {
		gameActive = false;
	}
	
	public void handleConnectionLost() {
		statusMessage = "Connection to server lost";
		networkMode = false;
		gameActive = false;
	}
	
	public void launchGame() {
		gameThread = new Thread(this);
		gameThread.start();
	}
	
	public void setPieces() {
		pieces.add(new Pawn(White, 0, 6));
		pieces.add(new Pawn(White, 1, 6));
		pieces.add(new Pawn(White, 2, 6));
		pieces.add(new Pawn(White, 3, 6));
		pieces.add(new Pawn(White, 4, 6));
		pieces.add(new Pawn(White, 5, 6));
		pieces.add(new Pawn(White, 6, 6));
		pieces.add(new Pawn(White, 7, 6));
		pieces.add(new Rook(White, 0, 7));
		pieces.add(new Rook(White, 7, 7));
		pieces.add(new Knight(White, 1, 7));
		pieces.add(new Knight(White, 6, 7));
		pieces.add(new Bishop(White, 2, 7));
		pieces.add(new Bishop(White, 5, 7));
		pieces.add(new Queen(White, 3, 7));
		pieces.add(new King(White, 4, 7));
		
		pieces.add(new Pawn(Black, 0, 1));
		pieces.add(new Pawn(Black, 1, 1));
		pieces.add(new Pawn(Black, 2, 1));
		pieces.add(new Pawn(Black, 3, 1));
		pieces.add(new Pawn(Black, 4, 1));
		pieces.add(new Pawn(Black, 5, 1));
		pieces.add(new Pawn(Black, 6, 1));
		pieces.add(new Pawn(Black, 7, 1));
		pieces.add(new Rook(Black, 0, 0));
		pieces.add(new Rook(Black, 7, 0));
		pieces.add(new Knight(Black, 1, 0));
		pieces.add(new Knight(Black, 6, 0));
		pieces.add(new Bishop(Black, 2, 0));
		pieces.add(new Bishop(Black, 5, 0));
		pieces.add(new Queen(Black, 3, 0));
		pieces.add(new King(Black, 4, 0));
	}
	
	private void copyPieces(ArrayList<Piece> source, ArrayList<Piece> target) {
		target.clear();
		for(int i = 0; i < source.size(); i++) {
			target.add(source.get(i));
		}
	}
	
	@Override
	public void run() {
		double drawInterval = 1000000000 / FPS;
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;
		
		while(gameThread != null) {
			currentTime = System.nanoTime();
			delta += (currentTime - lastTime) / drawInterval;
			lastTime = currentTime;
			
			if(delta >= 1) {
				update();
				repaint();
				delta--;
			}
		}
	}
	
	public void update() {
		if(!gameActive) return;
	
		if(networkMode && currentColor != myColor) {
			return; 
		}

		if(mouse.pressed) {
			if(activeP == null) {
				for(Piece piece : simPieces) {
					if(networkMode && piece.color != myColor) {
						continue;
					}
					
					if(piece.color == currentColor && 
							piece.col == mouse.x / Board.SQUARE_SIZE && 
							piece.row == mouse.y / Board.SQUARE_SIZE) {
						activeP = piece;
					}
				}
			} else {
				simulate();
			}
		}

		if(mouse.pressed == false) {
			if(activeP != null) {
				if(validSquare) {
					int fromCol = activeP.preCol;
					int fromRow = activeP.preRow;
					int toCol = activeP.col;
					int toRow = activeP.row;

					copyPieces(simPieces, pieces);
					activeP.updatePosition();
					
					int opponentColor = (currentColor == White) ? Black : White;
					
					if (Piece.isCheckmate(opponentColor)) {
						String winner = (currentColor == White) ? "White" : "Black";
						statusMessage = "Checkmate! " + winner + " wins!";
						
						if (networkMode && networkManager != null) {
							networkManager.sendMove(fromCol, fromRow, toCol, toRow);
						} else {
							endGame();
						}
					} else if (Piece.isKingInCheck(opponentColor)) {
						String colorName = (opponentColor == White) ? "White" : "Black";
						statusMessage = colorName + " is in check!";

						if(networkMode && networkManager != null) {
							networkManager.sendMove(fromCol, fromRow, toCol, toRow);
						} else {
							currentColor = (currentColor == White) ? Black : White;
						}
					} else {
						if(networkMode && networkManager != null) {
							networkManager.sendMove(fromCol, fromRow, toCol, toRow);
						} else {
							currentColor = (currentColor == White) ? Black : White;
						}
					}
					
					activeP = null;
				} else {
					// Invalid move 
					copyPieces(pieces, simPieces);
					activeP.resetPosition();
					activeP = null;
				}
			}
		}
	}
	
	private void simulate() {
		canMove = false;
		validSquare = false;
		copyPieces(pieces, simPieces);
		
		activeP.x = mouse.x - Board.HALF_SQUARE_SIZE;
		activeP.y = mouse.y - Board.HALF_SQUARE_SIZE;
		activeP.col = activeP.getCol(activeP.x);
		activeP.row = activeP.getRow(activeP.y);
		
		if(activeP.canMove(activeP.col, activeP.row)) {
			canMove = true;
			
			if(activeP.hittingP != null) {
				System.out.println("Capturing piece: " + activeP.hittingP.getClass().getSimpleName() + 
						" at " + activeP.hittingP.col + "," + activeP.hittingP.row);
				simPieces.remove(activeP.hittingP.getIndex());
			}
			validSquare = true;
		}
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		
		board.draw(g2);
		
		for(Piece p : simPieces) {
			p.draw(g2);
		}
		
		if(activeP != null) {
			if(canMove) {
				g2.setColor(Color.white);
				g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f));
				g2.fillRect(activeP.col * Board.SQUARE_SIZE, activeP.row * Board.SQUARE_SIZE, 
						Board.SQUARE_SIZE, Board.SQUARE_SIZE);
				g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
			}
			activeP.draw(g2);
		}

		if(!statusMessage.isEmpty()) {
			g2.setColor(Color.WHITE);
			g2.setFont(new Font("Arial", Font.BOLD, 20));
			g2.drawString(statusMessage, 850, 50);
		}

		if(gameActive) {
			String turnText = (currentColor == White) ? "White's Turn" : "Black's Turn";
			g2.setColor((currentColor == White) ? Color.WHITE : Color.GRAY);
			g2.setFont(new Font("Arial", Font.PLAIN, 16));
			g2.drawString(turnText, 850, 100);
		}
	}
}