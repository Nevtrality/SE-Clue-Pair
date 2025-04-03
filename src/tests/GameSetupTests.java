package tests;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import java.awt.Color;
import java.util.HashSet;
import java.util.Set;
import java.util.List;

import clueGame.Board;
import clueGame.Player;
import clueGame.Card;
import clueGame.Solution;
import clueGame.CardType;

public class GameSetupTests {
	// static because can only load one at a time
		private static Board board;
		
		@BeforeAll
		public static void setUp() {
			// Board is singleton, get the only instance
			board = Board.getInstance();
			// set the file names to use my config files
			board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");		
			// Initialize will load config files 
			board.initialize();
		}
		
		@Test
		public void testPeopleLoaded() {
			Player[] players = board.getPlayers();
			// Make sure all 6 players loaded in
			assertEquals(players.length, 6);
			
			// Make sure names and attributes are correct
			assertEquals(players[0].getName(),"Gregory House");
			assertEquals(players[1].getName(),"Dr. Wilson");
			assertEquals(players[2].getName(),"Allison Cameron");
			assertEquals(players[3].getName(),"Lisa Cuddy");
			assertEquals(players[4].getName(),"Thirteen");
			assertEquals(players[5].getName(),"Eric Foreman");

			assertEquals(players[1].getColor(), Color.getColor("Blue"));
			assertEquals(players[4].getColor(), Color.getColor("Green"));
		}
		
		@Test
		public void testPlayerLoaded() {
			Player[] players = board.getPlayers();
			// Make sure players have correct player and computer attributes
			assertEquals(players[0].getType(),"Human");
			assertEquals(players[1].getType(),"Computer");
			assertEquals(players[2].getType(),"Computer");
			assertEquals(players[3].getType(),"Computer");
			assertEquals(players[4].getType(),"Computer");
			assertEquals(players[5].getType(),"Computer");
			
		}
		
		@Test
		public void testDeckLoaded() {
			// Make sure deck is correct size
			List<Card> testdeck = board.getDeck();
			assertEquals(testdeck.size(), 21);
			// Make sure deck has correct cards in it
			assertEquals(testdeck.get(0).getCardName(), "Waiting Room");
			assertEquals(testdeck.get(0).getCardType(), CardType.ROOM);
			assertEquals(testdeck.get(1).getCardName(), "House Office");
			assertEquals(testdeck.get(1).getCardType(), CardType.ROOM);
			assertEquals(testdeck.get(2).getCardName(), "Break Room");
			assertEquals(testdeck.get(2).getCardType(), CardType.ROOM);
			assertEquals(testdeck.get(3).getCardName(), "MRI Room");
			assertEquals(testdeck.get(3).getCardType(), CardType.ROOM);
			assertEquals(testdeck.get(4).getCardName(), "Surgery Room");
			assertEquals(testdeck.get(9).getCardName(), "Gregory House");
			assertEquals(testdeck.get(9).getCardType(), CardType.PERSON);
			assertEquals(testdeck.get(12).getCardName(), "Lisa Cuddy");
			assertEquals(testdeck.get(12).getCardType(), CardType.PERSON);
			assertEquals(testdeck.get(16).getCardName(), "Stethoscope");
			assertEquals(testdeck.get(16).getCardType(), CardType.WEAPON);
			assertEquals(testdeck.get(18).getCardName(), "Scalpel");
			assertEquals(testdeck.get(18).getCardType(), CardType.WEAPON);
		}
		
		@Test
		public void testDeckDealt() {
			Solution testSolution = board.getSolution();
			Player[] players = board.getPlayers();
			// test if solution contains 3 cards, one room, one person, one weapon
			assertEquals(testSolution.getRoom().getCardType(), CardType.ROOM);
			assertEquals(testSolution.getPerson().getCardType(), CardType.PERSON);
			assertEquals(testSolution.getWeapon().getCardType(), CardType.WEAPON);

			// test if whole deck dealt
				// add all cards held by players and solution to see if it same as deck size
			assertEquals(players[0].getHandSize(), 3);
			assertEquals(players[1].getHandSize(), 3);
			assertEquals(players[2].getHandSize(), 3);
			assertEquals(players[3].getHandSize(), 3);
			assertEquals(players[4].getHandSize(), 3);
			assertEquals(players[5].getHandSize(), 3);
			// test that no duplicate cards
			Set<Card> found = new HashSet<Card>(); 
			for(Player person : players) {
				for(Card card : person.getHand()) {
					assertFalse(found.contains(card));
					found.add(card);
				}
			}
			assertFalse(found.contains(testSolution.getRoom()));
			assertFalse(found.contains(testSolution.getPerson()));
			assertFalse(found.contains(testSolution.getWeapon()));
		}
}
