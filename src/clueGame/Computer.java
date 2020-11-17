	package clueGame;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;
import java.util.Set;

public class Computer extends Player {

	public Computer() {
		super();
	}
	
	public Computer(Color color) {
		super();
		this.setColor(color);
	}
	
	public Computer(String name, int row, int col, Color color) {
		super();
		this.name = name;
		this.row = row;
		this.col = col;
		this.setColor(color);
	}
	
	public Solution createSuggestion() {
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
						//System.out.println(target.getRoomChar());
						validRooms.add(target);
					}
				}
			}
			
		}
		if(validRooms.size() != 0) {
			return validRooms.get(rand.nextInt(validRooms.size()));
		}
		
		ArrayList<BoardCell> targetListArray = new ArrayList<>();
		for (BoardCell target : targetList) {
			targetListArray.add(target);
		}
		return targetListArray.get(rand.nextInt(targetListArray.size()));		
	}
}
