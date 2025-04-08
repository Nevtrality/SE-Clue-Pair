package tests;
import static org.junit.Assert.*;


import java.util.List;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.ComputerPlayer;
import clueGame.Player;
import clueGame.Room;
import clueGame.Card;
import clueGame.Solution;
import clueGame.CardType;

public class ComputerAITest {
	
	private static Board board;

    @BeforeEach
	public void setUp() {
		// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		// Initialize will load BOTH config files
		board.initialize();
        //Set up cards
        Card houseCard = new Card("Gregory House", "Person");
        Card cuddyCard = new Card("Lisa Cuddy", "Person");
        Card caneCard = new Card("House's cane", "Weapon");
        Card scalpelCard = new Card("Scalpel", "Weapon");
        Card waitingRoomCard = new Card("Waiting Room", "Room");
        Card morgueCard = new Card("Morgue", "Room");

	}

	
	 @Test
	    public void testComputerSuggestion() {
	    	List<Player> players = board.getPlayers();
	    	// Make sure suggestion's room is the same as the computer's location
	    	Room room = board.getCell(0, 0).getRoom();
	    	Solution suggestion = players.get(1).createSuggestion(board);
	    	assertEquals(suggestion.getRoom().getCardName(), room.getName());
	    	
	    	// If one weapon not seen, it's selected
	    	Player player = new ComputerPlayer("Dr. House", "Red", 9, 6);
	    	boolean firstCard = true;
	    	Card weaponCheck = null;
	    	for(Card card : board.getDeck()) {
	    		if(card.getCardType() == CardType.WEAPON) {
	    			Solution soln = board.getSolution();
	    			if(card == soln.getWeapon()) {
	    				continue;
	    			}
	    			if(!firstCard) {
	    				player.updateSeen(card);
	    			}
	    			else {
	    				firstCard = false;
	    				weaponCheck = card;
	    			}
	    		}
	    		
	    	}
	      
	    	suggestion = player.createSuggestion(board);
	    	assertEquals(suggestion.getWeapon(), weaponCheck);
	    	
	    	
	    	// if only one person not seen, its selected
	    	player = new ComputerPlayer("Dr. House", "Red", 9, 6);
	    	firstCard = true;
	    	Card personCheck = null;
	    	for(Card card : board.getDeck()) {
	    		if(card.getCardType() == CardType.PERSON) {
	    			Solution soln = board.getSolution();
	    			if(card == soln.getPerson()) {
	    				continue;
	    			}
	    			if(!firstCard) {
	    				player.updateSeen(card);
	    			}
	    			else {
	    				firstCard = false;
	    				personCheck = card;
	    			}
	    		}
	    		
	    	}
	      
	    	suggestion = player.createSuggestion(board);
	    	assertEquals(suggestion.getPerson(), personCheck);
	    	
	    	// if multiple weapons not seen, one is randomly selected
	    	Set<Card> seenCards = new HashSet<Card>();
	        for(int i = 0; i <= 100; i++) {
	            seenCards.add(player.createSuggestion(board).getWeapon());
	        }
	        int numWeaponCards = 0;
	        for(Card card : board.getDeck()) {
	        	if(card.getCardType() == CardType.WEAPON) {
	        		numWeaponCards++;
	        	}
	        }
	        assertEquals(seenCards.size(), numWeaponCards);
	    	
	    	// if multiple persons not seen, one is randomly selected
	        seenCards = new HashSet<Card>();
	        for(int i = 0; i <= 100; i++) {
	            seenCards.add(player.createSuggestion(board).getPerson());
	        }
	        int numPersonCards = 0;
	        for(Card card : board.getDeck()) {
	        	if(card.getCardType() == CardType.PERSON) {
	        		numPersonCards++;
	        	}
	        }
	        assertEquals(seenCards.size(), numPersonCards);
	    }
	    
	    @Test
	    public void testComputerSelectTarget() {
	    	//See if random location is part of accessible locations from current point
	    	//if no rooms in list, select randomly
	        Player player = new ComputerPlayer("Dr. House", "Red", 1, 8);
	        board.calcTargets(board.getCell(1,8), 4);
	        
	        Set<BoardCell> targets = new HashSet<BoardCell>();
	        for (int i = 0; i <= 500; i++) {
	            targets.add(player.selectTarget(board, 4));
	        }
	        assertEquals(board.getTargets().size(), targets.size());
	        
	        //if room in list that has not been seen, select it
	        player = new ComputerPlayer("Dr. House", "Red", 6, 8);
	        board.calcTargets(board.getCell(6,8), 3);
	        assertEquals(board.getCell(3, 3), player.selectTarget(board, 3));
	        
	        //if room in list that has been seen, each target (including room) selected randomly
	        player = new ComputerPlayer("Dr. House", "Red", 6, 8);
	        board.calcTargets(board.getCell(6,8), 3);
	        Card morgueCard = null;
	        for(Card card : board.getDeck()) {
	        	if(card.getCardName().equals("Morgue")) {
	        		morgueCard = card;
	        	}
	        }
	        player.updateSeen(morgueCard);
	        targets = new HashSet<BoardCell>();
	        for (int i = 0; i <= 500; i++) {
	            targets.add(player.selectTarget(board, 3));
	        }
	        assertEquals(board.getTargets().size(), targets.size());
	 
	    }

}
