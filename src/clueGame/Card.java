package clueGame;

public class Card {
	private String cardName;
	private CardType type;
	private char roomChar;
	
	public Card(String cardName, CardType type) {
		super();
		this.cardName = cardName;
		this.type = type;
	}
	
	public Card(String cardName, CardType type, char roomChar) {
		super();
		this.cardName = cardName;
		this.type = type;
		this.roomChar = roomChar;
	}

	public String getCardName() {
		return cardName;
	}

//	public void setCardName(String cardName) {
//		this.cardName = cardName;
//	}

	public CardType getType() {
		return type;
	}

//	public void setType(CardType type) {
//		this.type = type;
//	}
	
	public char getRoomChar() {
		return roomChar;
	}
	
	public void setRoomChar(char roomChar) {
		this.roomChar = roomChar;
	}

	public boolean equals(Card target) {
		if((target.getCardName() == this.cardName) && (this.type == target.getType())) {
			return true;
		}
		return false;
	}
}
