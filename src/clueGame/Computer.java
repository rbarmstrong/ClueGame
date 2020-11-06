	package clueGame;

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
		
		while(needPerson || needWeapon || needRoom) {
			tempCard = filtered.get(rand.nextInt(filtered.size()));
			if((tempCard.getType() == CardType.PERSON) && needPerson) {
				suggestion.setPerson(tempCard);
				needPerson = false;
				filtered.remove(tempCard);
			}else if((tempCard.getType() == CardType.WEAPON) && needWeapon) {
				suggestion.setWeapon(tempCard);
				needWeapon = false;
				filtered.remove(tempCard);
			}else if((tempCard.getType() == CardType.ROOM) && needRoom) {
				suggestion.setRoom(tempCard);
				needRoom = false;
				filtered.remove(tempCard);
			}
		}
	}

}
