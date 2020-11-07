	package clueGame;

import java.util.ArrayList;
import java.util.Random;

public class Computer extends Player {

	public Computer() {
		super();

	}
	
	public void createSuggestion() {
		filtered = filterChoices();
		Random rand = new Random();
		boolean needPerson = true;
		boolean needWeapon = true;
		Card tempCard;
		boolean needRoom = false;
		
		if(!needRoom) {
			String name = Board.getInstance().getRoom(Board.getInstance().getCell(row, col)).getName();
			tempCard = new Card(name, CardType.ROOM);
			suggestion.setRoom(tempCard);
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
	}
	
	public Room selectTargetRoom() {
		Random rand = new Random();
		filtered = filterChoices();
		ArrayList<Card> visitedRooms = new ArrayList<>();
		for(Card c: filtered) {
			if(c.getType() == CardType.ROOM) {
				visitedRooms.add(c);
			}
		}
		return Board.getInstance().getRoom(visitedRooms.get(rand.nextInt(visitedRooms.size())).getRoomChar());
	}

}
