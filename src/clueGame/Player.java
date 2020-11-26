package clueGame;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JPanel;

public abstract class Player {
	protected String name;
	private Color color;
	protected int row, col;
	private ArrayList<Card> hand;
	private ArrayList<Card> cardsSeen;
	protected ArrayList<Card> filtered;
	protected Solution suggestion;
	protected int pathLength;
	protected boolean finishedTurn;
	protected boolean movedThisTurn;
	protected boolean inRoom;
	protected boolean stayInRoom;

	public Player() {
		hand = new ArrayList<>();
		cardsSeen = new ArrayList<>();
		suggestion = new Solution();
		inRoom = false;
	}

	public Player(int length) {
		pathLength = length;
	}

	public void updateHand(Card card) {
		hand.add(card);
		cardsSeen.add(card);
	}

	public void updateSeen(Card card) {
		cardsSeen.add(card);
	}

	public Card disproveSuggestion(Solution suggestion) {
		Random rand  = new Random();
		if(Board.getInstance().testMode) {
			rand.setSeed(5);
		}
		ArrayList<Card> temp = new ArrayList<>();

		if(hand.contains(suggestion.person)) {
			temp.add(suggestion.person);
		}
		if(hand.contains(suggestion.room)) {
			temp.add(suggestion.room);
		}
		if(hand.contains(suggestion.weapon)) {
			temp.add(suggestion.weapon);
		}
		if(temp.size() == 0) {
			return null;
		}
		int randVal = rand.nextInt(temp.size());
		if(!Board.getInstance().testMode) { //Guess result in control panel is updated depending on the result of suggestion and who suggested
			if(Board.getInstance().getPlayers().get(Board.getInstance().turn).getClass().getName().equals("clueGame.Human")) {
				GameControlPanel.setGuessResult(name + " disproved with " + temp.get(randVal).getCardName());
			}else {
				GameControlPanel.setGuessResult(name + " disproved.");
			}		
		}
		return temp.get(randVal);

	}

	public ArrayList<Card> filterChoices(){
		filtered = new ArrayList<>();
		boolean seen;
		for(int i = 0; i < Board.getInstance().getDeck().size(); i++) {
			seen = false;
			for (int j = 0; j < cardsSeen.size(); j++) {
				if (Board.getInstance().getDeck().get(i).getCardName().equals(cardsSeen.get(j).getCardName())) {
					seen = true;
				}
			}
			if (!seen) {
				filtered.add(Board.getInstance().getDeck().get(i));
			}
		}		
		return filtered;
	}

	public void drawSelf(int height, int width, Graphics g) {
		int row = getLocation()[0];
		int col = getLocation()[1];
		g.setColor(getColor());
		g.fillOval(col * height, row * width, height, width);
		g.setColor(Color.BLACK);
		g.drawOval(col * height, row * width, height, width);
	}

	public void drawSelf(int height, int width, Graphics g, int numPlayers) {
		int row = getLocation()[0];
		int col = getLocation()[1];
		int offset = height*3/4;
		g.setColor(getColor());
		g.fillOval(col * height + offset*numPlayers, row * width , height, width);
		g.setColor(Color.BLACK);
		g.drawOval(col * height + offset*numPlayers, row * width , height, width);
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setColor(Color color) {
		this.color = color;
	}


	public String getName() {
		return name;
	}

	public Color getColor() {
		return color;
	}

	public int[] getLocation() {
		int[] location = {row, col};
		return location;
	}
	public BoardCell getLocationCell() {
		return Board.getInstance().getCell(row,col);
	}
	public Room getRoom() {
		return Board.getInstance().getRoom(getLocationCell());
	}
	public void setLocation(int row, int col) {
		this.row = row;
		this.col = col;
		finishedTurn = true;
		movedThisTurn = true;
	}

	public void setPathLength(int length) {
		pathLength = length;
	}

	public ArrayList<Card> getHand() {
		return hand;
	}

	public void setInRoom(boolean set) {
		inRoom = set;
	}

	public boolean getInRoom() {
		return inRoom;
	}

	public ArrayList<Card> getCardsSeen() {
		return cardsSeen;
	}
}
