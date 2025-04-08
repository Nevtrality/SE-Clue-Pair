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
import clueGame.ComputerPlayer;

public class GameSolutionTest {

    private static Board board;

    @BeforeEach
	public void setUp() {
		// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		// Initialize will load BOTH config files
		board.initialize();

	}

    @Test
    public void checkAccusation() {
        Solution solution = board.getSolution();
        Card solutionPerson = solution.getPerson();
        Card solutionWeapon = solution.getWeapon();
        Card solutionRoom = solution.getRoom();
        Card card = new Card(null, "Person");
        Solution trueAccusation = new Solution(solutionRoom, solutionPerson, solutionWeapon);
        Solution wrongPersonAcc = new Solution(solutionRoom, card, solutionWeapon);
        Solution wrongWeaponAcc = new Solution(solutionRoom, solutionPerson, card);
        Solution wrongRoomAcc = new Solution(card, solutionPerson, solutionWeapon);

        //Solution that is correct
        assertTrue(board.checkAccusation(trueAccusation));
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
        
        Player player = new ComputerPlayer("Dr. House", "Red", 3, 3);
        Card card = new Card("card", "Room");
        player.updateHand(card);
        Solution suggestion = new Solution(card, new Card("", ""), new Card("", "")); 
        //If player has only one matching card is should be returned
        assertEquals(card, player.disproveSuggestion(suggestion));
        
        //If players has >1 matching card, returned card should be chosen randomly
        Card secondCard = new Card("card 2", "Person");
        player.updateHand(secondCard);
        suggestion = new Solution(card, secondCard, new Card("", ""));
        Set<Card> seenCards = new HashSet<Card>();
        for(int i = 0; i <= 200; i++) {
            seenCards.add(player.disproveSuggestion(suggestion));
        }
        assertEquals(2, seenCards.size());
        //If player has no matching cards, null is returned
        suggestion = new Solution(player1Hand.get(0), player1Hand.get(1), player1Hand.get(2));
        assertEquals(player.disproveSuggestion(suggestion), null);
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
    
 
    
}
