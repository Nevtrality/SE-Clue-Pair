package clueGame;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.HashSet;

public abstract class Player {
	String name;
	Color color; 
	int row;
	int column;
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
		List<Card> matchingCards = new ArrayList<Card>();
		// check room in suggestion
		if (hand.contains(suggestion.getRoom())) {
			matchingCards.add(suggestion.getRoom());
		}
		// check person in suggestion
		if (hand.contains(suggestion.getPerson())) {
			matchingCards.add(suggestion.getRoom());
		}
		// check weapon in suggestion
		if (hand.contains(suggestion.getWeapon())) {
			matchingCards.add(suggestion.getRoom());
		}
		// return a random card in matchingCards (should return null if list is empty)
		Random rand = new Random();
		if(matchingCards.size() == 0) {
			return null;
		}
		return matchingCards.get(rand.nextInt(0,matchingCards.size()));
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

	public abstract Solution createSuggestion(Board board);

	public abstract BoardCell selectTarget(Board board, int i);

}
