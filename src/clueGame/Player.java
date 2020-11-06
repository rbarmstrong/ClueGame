package clueGame;

import java.awt.Color;
import java.util.ArrayList;

public abstract class Player {
	private String name;
	private Color color;
	protected int row, col;
	private ArrayList<Card> hand;
	private ArrayList<Card> cardsSeen;
	protected ArrayList<Card> filtered;
	protected Solution suggestion;
	
	public Player() {
		hand = new ArrayList<>();
		cardsSeen = new ArrayList<>();
	}

	public void updateHand(Card card) {
		hand.add(card);
		cardsSeen.add(card);
	}
	
	public void updateSeen(Card card) {
		cardsSeen.add(card);
	}
	
	public Card disproveSuggestion(Solution suggestion) {
		if(hand.contains(suggestion.person)) {
			return suggestion.person;
		}else if(hand.contains(suggestion.room)) {
			return suggestion.room;
		}else if(hand.contains(suggestion.weapon)) {
			return suggestion.weapon;
		}else {
			return null;
		}
	}
	
	public ArrayList<Card> filterChoices(){
		filtered = new ArrayList<>();
		for(int i = 0; i < Board.deck.size(); i++) {
			filtered.add(Board.deck.get(i));
		}
		for(int i = 0; i < cardsSeen.size(); i++) {
			filtered.remove(cardsSeen.get(i));
		}
		return filtered;
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
