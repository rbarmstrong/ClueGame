package clueGame;

public class Solution {
	public Card person;
	public Card room;
	public Card weapon;
	
	public Solution(Card person, Card room, Card weapon) {
		this.person = person;
		this.room = room;
		this.weapon = weapon;
	}
	
	public Solution() {
		
	}
	
	public Card getPerson() {
		return person;
	}
	public void setPerson(Card person) {
		this.person = person;
	}
	public Card getRoom() {
		return room;
	}
	public void setRoom(Card room) {
		this.room = room;
	}
	public Card getWeapon() {
		return weapon;
	}
	public void setWeapon(Card weapon) {
		this.weapon = weapon;
	}
	
	public String stringSolution() {
		return (person.getCardName() + " with " + weapon.getCardName() + " in " + room.getCardName());
	}
	
}
