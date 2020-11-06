package clueGame;

import java.util.Random;

public class Computer extends Player {

	public Computer() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public void createSuggestion() {
		filtered = filterChoices();
		Random rand = new Random();
		boolean needPerson = true;
		boolean needWeapon = true;
		boolean needRoom = true;
		Card tempCard;
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
