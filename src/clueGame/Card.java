package clueGame;

public class Card {
	private CardType cardType;
	private String cardName;
	
	public Card(String cardName, String cardType) {
		this.cardName = cardName;
		if(cardType.equals("Room")) {
			this.cardType = CardType.ROOM;
		}
		else if(cardType.equals("Person")) {
			this.cardType = CardType.PERSON;
		}
		else if(cardType.equals("Weapon")) {
			this.cardType = CardType.WEAPON;
		}
	}

	public String getCardName(){
		return cardName;
	}

	public CardType getCardType(){
		return cardType;
	}
	
	public boolean equals(Card target) {
		if(this.cardName == target.getCardName() && this.cardType == target.getCardType()) {
			return true;
		}
		return false;
	}
}
