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
import clueGame.Player;
import clueGame.Room;
import clueGame.Card;
import clueGame.Solution;
import clueGame.CardType;

public class GameSolutionTest {

    private static Board board;

    @BeforeEach
	public static void setUp() {
		// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("ClueLayout306.csv", "ClueSetup306.txt");
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
    public void checkAccusation() {
        Solution solution = board.getSolution();
        Card solutionPerson = solution.getPerson();
        Card solutionWeapon = solution.getWeapon();
        Card solutionRoom = solution.getRoom();
        Card card = new Card(null, null);
        Solution trueAccusation = new Solution(solutionRoom, solutionPerson, solutionWeapon);
        Solution wrongPersonAcc = new Solution(solutionRoom, card, solutionWeapon);
        Solution wrongWeaponAcc = new Solution(solutionRoom, solutionPerson, card);
        Solution wrongRoomAcc = new Solution(card, solutionPerson, solutionWeapon);

        //Solution that is correct
        assertEquals(solution, trueAccusation);
        //Solution with wrong person
        assertFalse(board.checkAccusation(wrongPersonAcc));
        //Solution with wrong weapon
        assertFalse(board.checkAccusation(wrongWeaponAcc));
        //Solution with wrong room
        assertFalse(board.checkAccusation(wrongRoomAcc));

    }

    @Test
    public void disproveSuggestion() {
        List<Player> players = board.getPlayers();
        Solution solution = board.getSolution();
        Card solutionPerson = solution.getPerson();
        Card solutionWeapon = solution.getWeapon();
        Card solutionRoom = solution.getRoom();
        List<Card>  player1Hand = players.get(1).getHand();
        
        Solution suggestion = new Solution(null, null, player1Hand.get(0)); 
        //If player has only one matching card is should be returned
        assertEquals(player1Hand.get(0), players.get(1).disproveSuggestion(suggestion));
        //If players has >1 matching card, returned card should be chosen randomly
        suggestion = new Solution(player1Hand.get(2), player1Hand.get(1), player1Hand.get(0));
        Set<Card> seenCards = new HashSet<Card>();
        for(int i = 0; i <= 20; i++) {
            seenCards.add(players.get(1).disproveSuggestion(suggestion));
        }
        assertEquals(3, seenCards.size());
        //If player has no matching cards, null is returned
        suggestion = new Solution(null, null, null);
        assertEquals(players.get(1).disproveSuggestion(suggestion), null);
    }

    @Test
    public void handleSuggestion() {
    	List<Player> players = board.getPlayers();
    	Solution solution = board.getSolution();
        //Suggestion no one can disprove returns null
    	assertEquals(board.handleSuggestion(players.get(0), solution), null);

        //Suggestion only suggesting player can disprove returns null
    	Card playersCard = players.get(0).getHand().get(0);
    	Card suggestionRoom = solution.getRoom();
    	Card suggestionPerson = solution.getPerson();
    	Card suggestionWeapon = solution.getWeapon();
    		// replace one solution card with player's card
    	if(playersCard.getCardType() == CardType.ROOM) {
    		suggestionRoom = playersCard;
    	} else if (playersCard.getCardType() == CardType.PERSON) {
    		suggestionPerson = playersCard;
    	} else {
    		suggestionWeapon = playersCard;
    	}
    	Solution suggestion = new Solution(suggestionRoom, suggestionPerson, suggestionWeapon);
    	assertEquals(board.handleSuggestion(players.get(0), suggestion), null); 

        //Suggestion only human can disprove returns answer (i.e., card that disproves suggestion)
    		// uses same code as test above
    	assertEquals(board.handleSuggestion(players.get(1), suggestion), playersCard);

        //Suggestion that two players can disprove, correct player (based on starting with next player in list) returns answer
    	players.get(1).updateHand(suggestionRoom);
    	players.get(2).updateHand(playersCard);
    	assertEquals(board.handleSuggestion(players.get(0), suggestion), suggestionRoom);
    }
    
    @Test
    public void testComputerSuggestion() {
    	List<Player> players = board.getPlayers();
    	// Make sure suggestion's room is the same as the computer's location
    	Room room = players.get(1).getCell().getRoom();
    	Solution suggestion = players.get(1).createSuggestion(board);
    	assertEquals(suggestion.getRoom().getCardName(), room.getName());
    	
    	// If one weapon not seen, it's selected
    	
    	// if only one person not seen, its selected
    	
    	// if multiple weapons not seen, one is randomly selected
    	
    	// if multiple persons not seen, one is randomly selected
    }
    
    @Test
    public void testComputerSelectTarget() {
    	//See if random location is part of accessible locations from current point
        Player player = new Player("Dr. House", "Red", 9, 6);
        board.calcTargets(board.getCell(9,6), 4);
        Set<BoardCell> targets = new HashSet<BoardCell>();
        for (int i = 0; i <= 500; i++) {
            targets.add(players.get(1).selectTarget());
        }
        assertEquals(board.getTargets(), targets.size());
    }
    
}
