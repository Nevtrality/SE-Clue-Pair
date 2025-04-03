package clueGame;
import java.awt.Color;
import java.util.HashSet;
import java.util.Set;

public abstract class Player {
	private String name;
	private Color color; 
	private int row;
	private int column;
	Set<Card> hand = new HashSet<Card>();
	
	public Player(String name, String color, int row, int column){
		this.name = name;
		this.color = Color.getColor(color);
		this.row = row;
		this.column = column;
		
	}
	
	public void updateHand(Card card) {
		hand.add(card);
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
	public Set<Card> getHand() {
		return hand;
	}
	
	public abstract String getType();
}
