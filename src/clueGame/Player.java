package clueGame;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

import javax.imageio.ImageIO;

import java.util.HashSet;

public abstract class Player {
	String name;
	Color color; 
	int row;
	int col;
	List<Card> hand = new ArrayList<Card>();
	Set<Card> seenCards = new HashSet<Card>();
	public boolean turnFinished = false;
	
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
		Board board = Board.getInstance();
		board.getCell(row, col).setOccupied(false);
		this.row = cell.getRow();
		this.col = cell.getCol();
		if(!cell.isRoom()) {
			cell.setOccupied(true);
		}
		if(cell.isRoom()&&turnFinished==false) {
			board.handleSuggestion(this, createSuggestion(board));
		}
		turnFinished = true;
	}
	public void forceMove(BoardCell cell) {
		Board board = Board.getInstance();
		board.getCell(row,col).setOccupied(false);
		this.row = cell.getRow();
		this.col = cell.getCol();
	}
	
	public Card disproveSuggestion(Solution suggestion) {
		List<Card> matchingCards = new ArrayList<Card>();
		// check if player holds a suggested card
		for (Card card : hand) {
	        if (card.equals(suggestion.getRoom()) || 
	            card.equals(suggestion.getPerson()) || 
	            card.equals(suggestion.getWeapon())) {
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
	public BoardCell getCell() {
		Board board = Board.getInstance();
		return board.getCell(row,col);
	}
	
	public void draw(Graphics graphics, int cellWidth, int cellHeight) {
		int x = cellWidth * col;
		int y = cellHeight * row;

		for(Player player: Board.getInstance().getPlayers()){
			if(player.name == this.name) {
				break;
			}
			else if(player.row == this.row && player.col == this.col) {
				y += cellHeight/5;
			}
		}

		graphics.setColor(color);
		graphics.fillOval(x, y, cellWidth, cellHeight);
		
		try {
			if(this.name.equals("Gregory House")) {
				BufferedImage house = ImageIO.read(new File("data/housejumpscare.jpeg"));
				graphics.drawImage(house, cellWidth*col, cellHeight*row, cellWidth, cellWidth, null);
			}
		}catch(Exception e) {}
	}
	
	
	public abstract String getType();

	public abstract Solution createSuggestion(Board board);
	
	public abstract Solution createAccusation(Board board);

	public abstract BoardCell selectTarget(Board board, int i);
	
	public abstract void setAccusation(Solution accusation);

}
