package clueGame;

public class Card {
	private CardType cardType;
	private String cardName;
	
	public Card(String cardName, String cardType) {
		this.cardName = cardName;
		if(cardType == "Room") {
			this.cardType = CardType.ROOM;
		}
		else if(cardType == "Person") {
			this.cardType = CardType.PERSON;
		}
		else if(cardType == "Weapon") {
			this.cardType = CardType.WEAPON;
		}
	}
	
	public boolean equals(Card target) {
		return false;
	}
}
