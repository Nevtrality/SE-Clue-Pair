package clueGame;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.HashSet;

public abstract class Player {
	String name;
	Color color; 
	int row;
	int col;
	List<Card> hand = new ArrayList<Card>();
	Set<Card> seenCards = new HashSet<Card>();
	boolean turnFinished = false;
	
	public Player(String name, String color, int row, int col){
		this.name = name;
		try {
		    this.color = convertColor(color);
		} catch (Exception e) {
			this.color = convertColor(color);
		}
		this.row = row;
		this.col = col;
		
	}
	
	 public Color convertColor(String colorStr) {
        switch (colorStr.toLowerCase()) {
        case "red":
            color = Color.red;
            break;
        case "blue":
            color = Color.blue;
            break;
        case "cyan":
            color = Color.cyan;
            break;
        case "green":
            color = Color.green;
            break;
        case "yellow":
            color = Color.yellow;
            break;
        case "magenta":
            color = Color.magenta;
            break;
        case "orange":
            color = Color.orange;
            break;
        case "pink":
            color = Color.pink;
            break;
        case "white":
        	color = Color.white;
        	break;
        default:
            color = Color.black;
    	}
        
        return color;
	}
	
	public void updateHand(Card card) {
		hand.add(card);
	}
	
	public void updateSeen(Card seenCard) {
		seenCards.add(seenCard);
	}
	
	public void updatePosition(BoardCell cell) {
		this.row = cell.getRow();
		this.col = cell.getCol();
		if(cell.isRoom()) {
			Board board = Board.getInstance();
			board.handleSuggestion(this, createSuggestion(board));
		}
		turnFinished = true;
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

	public boolean isFinished(){
		return turnFinished;
	}
	
	public void setTurnStatus(boolean status) {
		turnFinished = status;
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
	
	public void draw(Graphics graphics, int cellWidth, int cellHeight) {
		int x = cellWidth * col;
		int y = cellHeight * row;
		graphics.setColor(color);
		graphics.fillOval(x, y, cellWidth, cellHeight);
		
	}
	
	
	public abstract String getType();

	public abstract Solution createSuggestion(Board board);

	public abstract BoardCell selectTarget(Board board, int i);

}
