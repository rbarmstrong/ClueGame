package clueGame;

public class Card {
	private String cardName;
	private CardType type;
	
	public Card(String cardName, CardType type) {
		super();
		this.cardName = cardName;
		this.type = type;
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

	public boolean equals(Card target) {
		return false;
	}
}
