package clueGame;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public abstract class Player {
	private String name;
	private Color color; 
	private int row;
	private int column;
	List<Card> hand = new ArrayList<>();
	
	public Player(String name, String color, int row, int column){
		this.name = name;
		this.color = Color.getColor(color);
		this.row = row;
		this.column = column;
		
	}
	
	public void updateHand(Card card) {
		hand.add(card);
	}
}
