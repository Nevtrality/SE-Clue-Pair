package clueGame;

import java.util.List;
import java.util.Random;
import java.util.Set;

public class ComputerPlayer extends Player{
	public ComputerPlayer(String name, String color, int row, int column) {
		super(name, color, row, column);
	}

	public String getType(){
		return "Computer";
	}
	
	public Solution createSuggestion(Board board) {
		// grab deck
		List<Card> deck = board.getDeck();
		// create empty array to hold solution temporarily
		Card[] suggestionList = new Card[3];
		Random rand = new Random();
		// loop through each suggestion card type
		for(int i = 0; i<3; i++) {
			// grab random card within the suggestion's card type range
			if (i == 0) {
				suggestionList[i] = deck.get(rand.nextInt(0,9));
			} else {
				suggestionList[i] = deck.get(rand.nextInt(9 + (6*(i-1)), 9+(6*i)));
			} 
			// while the random card is in either the computer's hand or seen cards, reroll the card
			while(hand.contains(suggestionList[i]) || seenCards.contains(suggestionList[i])) {
				if (i == 0) {
					suggestionList[i] = deck.get(rand.nextInt(0,9));
				} else {
					suggestionList[i] = deck.get(rand.nextInt(9 + (6*(i-1)), 9+(6*i)));
				} 
			}
		}
		// return the solution stored in the suggestion list
		Card currRoom = null;
		BoardCell cell = board.getCell(row, column);
			String roomName = cell.getRoom().getName();
			for(Card card : board.getDeck()) {
				if(card.getCardName().equals(roomName)) {
					currRoom = card;
				}
			}
		return new Solution(currRoom, suggestionList[1], suggestionList[2]);
	}
	
	public BoardCell selectTarget(Board board, int rollNum) {
		// calculate targets from the starting cell
		board.calcTargets(board.getCell(row, column), rollNum);
		// grab the target list
		Set<BoardCell> targets = board.getTargets();
		// set up random and get a random number from 0 to targets size
		Random rand = new Random();
		int targetNum = rand.nextInt(0,targets.size());
		Card roomCard = null;
		//check each cell in targets and if it is a room center and hasn't been seen, return the cell
		for(BoardCell cell : targets) {
			String roomName = cell.getRoom().getName();
			for(Card cards : board.getDeck()) {
				if(cards.getCardName().equals(roomName)) {
					roomCard = cards; 
				}
			}
			if(cell.isRoomCenter() && !seenCards.contains(roomCard)) {
				return cell;
			}
		}
		// create iterating variable
		int i = 0;
		for(BoardCell target: targets) {
			// if i is the number of the target cell, return that cell
			if(i==targetNum) {
				return target;
			} else {
				// otherwise move on to next target
				i++;
			}
		}
		// if somehow a target was not chosen, return null to symbolize error
		return null;
	}

}
