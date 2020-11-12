package clueGame;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class ClueBoardDisplay extends JFrame{
	Board board;
	GameCardPanel cardPanel;
	GameControlPanel controlPanel;

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
	}
	
	public static void main(String[] args) {
		Board board;
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("data/ClueLayout.csv", "data/ClueSetup.txt");		
		// Initialize will load config files 
		board.initialize();
		ClueBoardDisplay game = new ClueBoardDisplay();
		game.setVisible(true);
	}
}
