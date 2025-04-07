package tests;
import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.Player;
import clueGame.Card;
import clueGame.Solution;
import clueGame.CardType;

public class GameSolutionTest {

    private static Board board;

    @BeforeAll
	public static void setUp() {
		// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("ClueLayout306.csv", "ClueSetup306.txt");
		// Initialize will load BOTH config files
		board.initialize();
        //Set up players
        houseCard = new Card("Gregory House", Card.CardType.PERSON);
        cuddyCard = new Card("Gregory House", Card.CardType.PERSON);
	}

    @Test
    public void checkAccusation() {
        Solution solution = board.getSolution();
        //Solution that is correct
        Solution testCorrectSoln = solution;
        assertEquals(solution, testCorrectSoln);
        //Solution with wrong person

        //Solution with wrong weapon

        //Solution with wrong room

    }

    @Test
    public void disproveSuggestion() {
        //If player has only one matching card is should be returned

        //If players has >1 matching card, returned card should be chosen randomly

        //If player has no matching cards, null is returned

    }

    @Test
    public void handleSuggestion() {
        //Suggestion no one can disprove returns null

        //Suggestion only suggesting player can disprove returns null

        //Suggestion only human can disprove returns answer (i.e., card that disproves suggestion)

        //Suggestion that two players can disprove, correct player (based on starting with next player in list) returns answer
    }
    
    }
