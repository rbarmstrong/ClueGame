package clueGame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class GameControlPanel extends JPanel implements ActionListener{
	private static JTextField currName;
	private static JTextField roll;
	private static JTextField guess;
	private static JTextField guessResult;
	private JButton nextButton;
	private JButton accuseButton;
	
	public GameControlPanel() { //Constructor 
		// Create a layout with 2 rows
		setLayout(new GridLayout(2,0));
		JPanel panel = createTopPanel();
		add(panel);
		panel = createBottomPanel();
		add(panel);
	}
	
	public GameControlPanel(Player start) { //Constructor 
		// Create a layout with 2 rows
		setLayout(new GridLayout(2,0));
		JPanel panel = createTopPanel();
		add(panel);
		panel = createBottomPanel();
		add(panel);
		Random rand = new Random();
		int length = rand.nextInt(7);
		start.setPathLength(length);
		setTurn(start,length);
	}

	private JPanel createTopPanel() { //Creates the top panel and calls functions to make sub panel
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1,4));
		panel.add(createTurnPanel());
		panel.add(createRollPanel());
		panel.add(createAccusationButton());
		panel.add(createNextButton());

		return panel;
	}

	private JPanel createTurnPanel() { //Sub panel that shows whos turn it is
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(2,0));
		JLabel turnLabel = new JLabel("Whose turn?");
		panel.add(turnLabel);
		currName = new JTextField(20);
		currName.setEditable(false);
		panel.add(currName);
		return panel;
	}

	private JPanel createRollPanel() { //Sub panel that shows the roll
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1,2));
		JLabel turnLabel = new JLabel("Roll: ");
		turnLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		
		turnLabel.setFont(new Font("Font", Font.PLAIN, 30));
		
		panel.add(turnLabel);
		roll = new JTextField(20);
		
		roll.setHorizontalAlignment(SwingConstants.CENTER);
		roll.setFont(new Font("Font", Font.PLAIN, 30));
		roll.setEditable(false);
		
		panel.add(roll);
		return panel;
	}

	private JPanel createAccusationButton() { //sub panel that creates accusation button
		accuseButton = new JButton("Make Accusation");
		accuseButton.setFont(new Font("Font", Font.BOLD, 15));
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1,0));
		accuseButton.addActionListener(this);
		panel.add(accuseButton);
		return panel;
	}

	private JPanel createNextButton() { //sub panel that creates next turn button
		nextButton = new JButton("NEXT!");
		nextButton.setFont(new Font("Font", Font.PLAIN, 30));
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1,0));
		nextButton.addActionListener(this);
		panel.add(nextButton);
		return panel;
	}


	private JPanel createBottomPanel() { //creates bottom panel and calls functions to make sub panels
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0,2));
		panel.add(createGuessPanel());
		panel.add(createGuessResultPanel());
		return panel;
	}

	private JPanel createGuessPanel() { //creates panel to display guess
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1,0));
		guess = new JTextField(20);
		guess.setEditable(false);
		panel.setBorder(new TitledBorder (new EtchedBorder(), "Guess"));
		panel.add(guess);
		return panel;
	}
	
	private JPanel createGuessResultPanel() { //creates panel to display result of guess
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1,0));
		guessResult = new JTextField(20);
		guessResult.setEditable(false);
		panel.setBorder(new TitledBorder (new EtchedBorder(), "Guess Result"));
		panel.add(guessResult);
		return panel;
	}
	
	public static void setTurn(Player player, int rollVal) { //sets turn, updates displayed name and color, along with roll
		currName.setText(player.getName());
		currName.setBackground(player.getColor());
		roll.setText(Integer.toString(rollVal));
	}
	
	public static void setGuess(String guessMade) { //setter
		guess.setText(guessMade);
	}

	public static void setGuessResult(String result) { //setter
		guessResult.setText(result);
	}
	
	public void setCurrName(String name) { //setter
		currName.setText(name);
	}
	
	public void setRoll(int rollVal) { //setter
		roll.setText(Integer.toString(rollVal));
	}

	public static void main(String[] args) {
		GameControlPanel panel = new GameControlPanel();  // create the panel
		JFrame frame = new JFrame();  // create the frame
		frame.setContentPane(panel); // put the panel in the frame
		frame.setSize(750, 180);  // size the frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
		frame.setVisible(true); // make it visible


		// test filling in the data
		GameControlPanel.setTurn(new Computer( "Col. Mustard", 0, 0, Color.ORANGE), 5);
		panel.setGuess("I have no guess!");
		panel.setGuessResult("So you have nothing?");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == nextButton) {
			Board.getInstance().nextTurn();
		}if(e.getSource() == accuseButton) {
			Board.getInstance().newAccusation();
		}
		
	}


}
