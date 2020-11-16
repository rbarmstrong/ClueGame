package clueGame;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;

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
	
	public Player() {
		hand = new ArrayList<>();
		cardsSeen = new ArrayList<>();
		suggestion = new Solution();
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
		rand.setSeed(5); //TODO SEED
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
		return temp.get(rand.nextInt(temp.size()));
		
	}
	
	public ArrayList<Card> filterChoices(){
		filtered = new ArrayList<>();
		boolean seen;
		for(int i = 0; i < Board.deck.size(); i++) {
			seen = false;
			for (int j = 0; j < cardsSeen.size(); j++) {
				if (Board.deck.get(i).getCardName().equals(cardsSeen.get(j).getCardName())) {
					seen = true;
				}
			}
			if (!seen) {
				filtered.add(Board.deck.get(i));
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
	
	public ArrayList<Card> getCardsSeen() {
		return cardsSeen;
	}
}
