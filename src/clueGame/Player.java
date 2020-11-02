package clueGame;

import java.awt.Color;
import java.util.ArrayList;

public abstract class Player {
	private String name;
	private Color color;
	protected int row, col;
	private ArrayList<Card> hand;
	
	
	public Player() {
		hand = new ArrayList<>();
	}

	void updateHand(Card card) {
		hand.add(card);
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
