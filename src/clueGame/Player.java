package clueGame;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

public abstract class Player {
	private String name;
	private Color color; 
	private int row;
	private int column;
	List<Card> hand = new ArrayList<Card>();
	Set<Card> seenCards = new HashSet<Card>();
	
	public Player(String name, String color, int row, int column){
		this.name = name;
		this.color = Color.getColor(color);
		this.row = row;
		this.column = column;
		
	}
	
	public void updateHand(Card card) {
		hand.add(card);
	}
	
	public void updateSeen(Card seenCard) {
		seenCards.add(seenCard);
	}
	
	public Card disproveSuggestion(Solution suggestion) {
		// loop through cards in suggestion
			// if matches card in hand
			// return that card (if multiple return random one)
	}
	
	public String getName() {
		return name;
	}

	public Color getColor() {
		return color;
	}

	public int getHandSize() {
		return hand.size();
	}
	public List<Card> getHand() {
		return hand;
	}
	
	public abstract String getType();
}
