	package clueGame;

import java.util.ArrayList;
import java.util.Random;
import java.util.Set;

public class Computer extends Player {

	public Computer() {
		super();

	}
	
	public Solution createSuggestion() {
		filtered = filterChoices();
		Random rand = new Random();
		boolean needPerson = true;
		boolean needWeapon = true;
		Card tempCard;

		String name = Board.getInstance().getRoom(Board.getInstance().getCell(row, col)).getName();
		tempCard = new Card(name, CardType.ROOM);
		suggestion.setRoom(tempCard);

		
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
		ArrayList<Card> visitedRooms = new ArrayList<>();
		for(Card c: filtered) {
			if(c.getType() == CardType.ROOM) {
				visitedRooms.add(c);
			}
		}
		Set<BoardCell> targetList = Board.getInstance().getTargets();
		ArrayList<BoardCell> validRooms = new ArrayList<>();
		for (BoardCell target : targetList) {
			if (target.isDoorway()) {
				for(int i = 0; i < visitedRooms.size(); i++) {
					if(target.getRoomChar() != visitedRooms.get(i).getRoomChar()) {
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
