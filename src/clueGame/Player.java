package clueGame;
import java.awt.Color;
import java.lang.reflect.Field;
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
		try {
		    Field field = Color.class.getField(color);
		    this.color = (Color)field.get(null);
		} catch (Exception e) {
		    this.color = null; // Not defined
		}
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
		// check if player holds a suggested card
		for (Card card : hand) {
	        if (card.equals(suggestion.getPerson()) || 
	            card.equals(suggestion.getWeapon()) || 
	            card.equals(suggestion.getRoom())) {
	            matchingCards.add(card);
	        }
	    }
		// return a random card in matchingCards (should return null if list is empty)
		Random rand = new Random();
		if(matchingCards.size() == 0) {
			return null;
		}
		if (matchingCards.size() == 1) {
			return matchingCards.get(0);
		}
		return matchingCards.get(rand.nextInt(matchingCards.size()));
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
	public Set<Card> getSeen(){
		return seenCards;
	}
	
	public abstract String getType();

	public abstract Solution createSuggestion(Board board);

	public abstract BoardCell selectTarget(Board board, int i);

}
