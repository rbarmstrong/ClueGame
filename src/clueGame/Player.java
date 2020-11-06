package clueGame;

import java.awt.Color;
import java.util.ArrayList;

public abstract class Player {
	private String name;
	private Color color;
	protected int row, col;
	private ArrayList<Card> hand;
	private ArrayList<Card> cardsSeen;
	private Solution suggestion;
	
	public Player() {
		hand = new ArrayList<>();
	}

	public void updateHand(Card card) {
		hand.add(card);
	}
	
	public void updateSeen(Card card) {
		cardsSeen.add(card);
	}
	
	public Card disproveSuggestion(Card[] card) {
		for(int i = 0; i < 3; i++) {
			if(hand.contains(card[i])) {
				return card[i];
			}
		}
		return null;
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
	
	public void setLocation(int row, int col) {
		this.row = row;
		this.col = col;
	}
	
	public ArrayList<Card> getHand() {
		return hand;
	}
}
