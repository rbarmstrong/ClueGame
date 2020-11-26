	package clueGame;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;
import java.util.Set;

public class Computer extends Player {
	boolean accuseNext;

	public Computer() {
		super();
	}
	
	public Computer(Color color) {
		super();
		this.setColor(color);
		accuseNext = false;
	}
	
	public Computer(String name, int row, int col, Color color) {
		super();
		this.name = name;
		this.row = row;
		this.col = col;
		this.setColor(color);
		accuseNext = false;
	}
	
	public Solution createSuggestion() {
		accuseNext = false;
		filtered = filterChoices();
		Random rand = new Random();
		boolean needPerson = true;
		boolean needWeapon = true;
		Card tempCard;

		String name = Board.getInstance().getRoom(Board.getInstance().getCell(row, col)).getName();
		tempCard = new Card(name, CardType.ROOM);
		for(Card card: Board.getInstance().getDeck()) {
			if(card.equals(tempCard)) {
				suggestion.setRoom(card);
				break;
			}
		}
		
		while(needPerson || needWeapon) {
			tempCard = filtered.get(rand.nextInt(filtered.size()));
			if((tempCard.getType() == CardType.PERSON) && needPerson) {
				suggestion.setPerson(tempCard);
				needPerson = false;
				filtered.remove(tempCard);
			}else if((tempCard.getType() == CardType.WEAPON) && needWeapon) {
				suggestion.setWeapon(tempCard);
				needWeapon = false;
				filtered.remove(tempCard);
			}
		}
		return suggestion;
	}
	
	public BoardCell selectTargets() {
		if(accuseNext) { //Computer will accuse on this turn if it has solved the mystery. accuseNext is changed in Board nextTurn function
			Board.getInstance().clearHighlight();
			Board.getInstance().computerAccusation();
		}
		Random rand = new Random();
		filtered = filterChoices(); 
		ArrayList<Card> notVisitedRooms = new ArrayList<>();
		for(Card c: filtered) {
			if(c.getType() == CardType.ROOM) {
				notVisitedRooms.add(c);
			}
		}
		Set<BoardCell> targetList = Board.getInstance().getTargets();
		ArrayList<BoardCell> validRooms = new ArrayList<>();
		for (BoardCell target : targetList) {
			if (target.isRoomCenter()) {
				for(int i = 0; i < notVisitedRooms.size(); i++) {

					if(target.getRoomChar() == notVisitedRooms.get(i).getRoomChar()) {
						validRooms.add(target);
					}
				}
			}
			
		}
		if(validRooms.size() != 0) {
			int randInt = rand.nextInt(validRooms.size());
			if(Board.getInstance().getCurrPlayer().getInRoom()) {
				Board.getInstance().getRoom(Board.getInstance().getCurrPlayer().getLocationCell()).leaveRoom(Board.getInstance().getCurrPlayer());
			}
			Board.getInstance().getRoom(validRooms.get(randInt)).enterRoom(Board.getInstance().getCurrPlayer());
			Board.getInstance().getCurrPlayer().setInRoom(true);
			return validRooms.get(randInt);
		}
		
		ArrayList<BoardCell> targetListArray = new ArrayList<>();
		for (BoardCell target : targetList) {
			targetListArray.add(target);
		}
		int selection = rand.nextInt(targetListArray.size());
		if(Board.getInstance().getCurrPlayer().getInRoom()) {
			Board.getInstance().getCurrPlayer().setInRoom(false);
			//Player is removed from the list of players in room
			Board.getInstance().getRoom(Board.getInstance().getCurrPlayer().getLocationCell()).leaveRoom(Board.getInstance().getCurrPlayer()); 
		}
		return targetListArray.get(selection);
	}
	
	public void accuseNext() {
		accuseNext = true;
	}
}
