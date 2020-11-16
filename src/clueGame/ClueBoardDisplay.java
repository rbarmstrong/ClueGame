package clueGame;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;


public class ClueBoardDisplay extends JFrame{
	Board board;
	GameCardPanel cardPanel;
	GameControlPanel controlPanel;
	JOptionPane splash;

	public ClueBoardDisplay(){
		setSize(800, 800);
		setTitle("Clue Game - CSCI306");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		board = Board.getInstance();
		cardPanel = new GameCardPanel(board.getPlayers().get(0), board.getPlayers());
		controlPanel = new GameControlPanel();
		add(board, BorderLayout.CENTER);
		//cardPanel
		add(cardPanel, BorderLayout.EAST);
		add(controlPanel, BorderLayout.SOUTH);
		splash = new JOptionPane();
		Object[] options = {"OK"};
		JOptionPane.showOptionDialog(null, "You are " + Board.getInstance().getFirstTurnName() + ". Can you find the solution before the Computer players?", "Welcome To Clue", JOptionPane.PLAIN_MESSAGE, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
		
		

	}
	
	public static void main(String[] args) {
		Board board;
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("data/ClueLayout.csv", "data/ClueSetup.txt");		
		// Initialize will load config files 
		board.initialize();
		ClueBoardDisplay game = new ClueBoardDisplay();
		board.firstTurn();
		game.setVisible(true);
		
	}
}
