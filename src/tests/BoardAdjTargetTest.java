package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.BoardCell;

public class BoardAdjTargetTest {

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
	
	// Ensure that player does not move around within room
	// Cells are yellow on spreadsheet
	@Test
	public void testAdjacenciesRooms() {
		// Clinic test (One door, has secret room)
		Set<BoardCell> testList = board.getAdjList(23,3);
		assertEquals(2, testList.size());
		assertTrue(testList.contains(board.getCell(23, 9)));
		assertTrue(testList.contains(board.getCell(13, 24)));
		
		// MRI Room test (Multiple doors, no secret room)
		testList = board.getAdjList(14,3);
		assertEquals(2, testList.size());
		assertTrue(testList.contains(board.getCell(11, 5)));
		assertTrue(testList.contains(board.getCell(16, 5)));
		
		// Surgery Room (Multiple doors, has secret room)
		testList = board.getAdjList(13,24);
		assertEquals(3, testList.size());
		assertTrue(testList.contains(board.getCell(13, 19)));
		assertTrue(testList.contains(board.getCell(17, 22)));
		assertTrue(testList.contains(board.getCell(23, 3)));
		
		// Test room cell that isn't center of room
		testList = board.getAdjList(14,24);
		assertEquals(0, testList.size());
	}
	
	// Ensure doors include rooms and also walkways
	// Cells are yellow on spreadsheet
	@Test
	public void testAdjacencyDoors() {
		// Test door to Surgery Room on [17,22]
		Set<BoardCell> testList = board.getAdjList(17,22);
		assertEquals(2, testList.size());
		assertTrue(testList.contains(board.getCell(18, 22)));
		assertTrue(testList.contains(board.getCell(13, 24)));
		
		// Test door to Waiting Room on [23,14]
		testList = board.getAdjList(23,14);
		assertEquals(4, testList.size());
		assertTrue(testList.contains(board.getCell(23, 13)));
		assertTrue(testList.contains(board.getCell(23, 15)));
		assertTrue(testList.contains(board.getCell(22, 14)));
		assertTrue(testList.contains(board.getCell(27, 13)));
		
		// Test door to Morgue on [6,6]
		testList = board.getAdjList(6,6);
		assertEquals(3, testList.size());
		assertTrue(testList.contains(board.getCell(7, 6)));
		assertTrue(testList.contains(board.getCell(6, 7)));
		assertTrue(testList.contains(board.getCell(3, 3)));
	}
	
	// Test walkway scenarios
	// Cells are orange on spreadsheet
	@Test
	public void testAdjacencyWalkways() {
		// Test walkway imbedded in room and next to unused space ie [27,6]
		Set<BoardCell> testList = board.getAdjList(27,6);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCell(27,7)));
		
		// Test walkway on edge of board ie [18,27]
		testList = board.getAdjList(18,27);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCell(18, 26)));
		
		// Test open walkway ie [8,8]
		testList = board.getAdjList(8,8);
		assertEquals(4, testList.size());
		assertTrue(testList.contains(board.getCell(7, 8)));
		assertTrue(testList.contains(board.getCell(9, 8)));
		assertTrue(testList.contains(board.getCell(8, 7)));
		assertTrue(testList.contains(board.getCell(8, 9)));
		
		// Test walkway next to door ie [4,19]
		testList = board.getAdjList(4,19);
		assertEquals(3, testList.size());
		assertTrue(testList.contains(board.getCell(3, 19)));
		assertTrue(testList.contains(board.getCell(5, 19)));
		assertTrue(testList.contains(board.getCell(4, 18)));
	}
	
	// Test targets from walkways (rolls 1,3,4)
	// Cells are pink on spreadsheet
	@Test
	public void testTargetsFromWalkways() {
		// Test targets other walkways and enters a room via a door
			// Roll 1
		board.calcTargets(board.getCell(20, 19), 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(4, targets.size());
		assertTrue(targets.contains(board.getCell(19, 19)));
		assertTrue(targets.contains(board.getCell(20, 18)));
		assertTrue(targets.contains(board.getCell(21, 19)));
		assertTrue(targets.contains(board.getCell(20, 20)));
		
			// Roll 3
			// tests that it contains door
		board.calcTargets(board.getCell(20, 19), 3);
		targets= board.getTargets();
		assertEquals(15, targets.size());
		assertTrue(targets.contains(board.getCell(19, 17)));
		assertTrue(targets.contains(board.getCell(20, 22)));
		assertTrue(targets.contains(board.getCell(23, 19))); // door cell
		assertTrue(targets.contains(board.getCell(20, 20)));
		
			// Roll 4
			// tests entering room
		board.calcTargets(board.getCell(20, 19), 4);
		targets= board.getTargets();
		assertEquals(20, targets.size());
		assertTrue(targets.contains(board.getCell(24, 19)));
		assertTrue(targets.contains(board.getCell(19, 18)));
		assertTrue(targets.contains(board.getCell(24, 24))); // enter room
		assertTrue(targets.contains(board.getCell(21, 22)));
	}
	
	// Test targets from room center (rolls 1,3,4)
	// Cells are blue on spreadsheet
	@Test
	public void testTargetsFromRoom() {
		// Test targets leaving room without secret passage (various distances) (Breakroom)
			// Roll 1
		board.calcTargets(board.getCell(17, 13), 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCell(14, 12)));
		assertTrue(targets.contains(board.getCell(20, 13)));
		assertTrue(targets.contains(board.getCell(20, 14)));
		
			// Roll 3
		board.calcTargets(board.getCell(17, 13), 3);
		targets= board.getTargets();
		assertEquals(14, targets.size());
		assertTrue(targets.contains(board.getCell(14, 14)));
		assertTrue(targets.contains(board.getCell(21, 15)));
		assertTrue(targets.contains(board.getCell(22, 13)));
		assertTrue(targets.contains(board.getCell(20, 11)));
		
			// Roll 4
		board.calcTargets(board.getCell(17, 13), 4);
		targets= board.getTargets();
		assertEquals(27, targets.size());
		assertTrue(targets.contains(board.getCell(23, 14)));
		assertTrue(targets.contains(board.getCell(14, 15)));
		assertTrue(targets.contains(board.getCell(13, 10)));
		assertTrue(targets.contains(board.getCell(20, 10)));
		
		// Test targets leaving room with secret passage (various distances) (House Office)
			// Roll 1
		board.calcTargets(board.getCell(5, 24), 1);
		targets= board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCell(3, 3)));
		assertTrue(targets.contains(board.getCell(5, 19)));
		
			// Roll 3
		board.calcTargets(board.getCell(5, 24), 3);
		targets= board.getTargets();
		assertEquals(6, targets.size());
		assertTrue(targets.contains(board.getCell(3, 3)));
		assertTrue(targets.contains(board.getCell(3, 19)));
		assertTrue(targets.contains(board.getCell(4, 18)));
		assertTrue(targets.contains(board.getCell(7, 19)));
		
			// Roll 4
		board.calcTargets(board.getCell(5, 24), 4);
		targets= board.getTargets();
		assertEquals(11, targets.size());
		assertTrue(targets.contains(board.getCell(3, 18)));
		assertTrue(targets.contains(board.getCell(8, 19)));
		assertTrue(targets.contains(board.getCell(3, 20)));
		assertTrue(targets.contains(board.getCell(5, 16)));
	}
	
	// Tests targets when other player blocks path
	// cells are red on spreadsheet
	@Test
	public void testTargetsBlocking() {
		// players in path of walkway
		board.getCell(16, 8).setOccupied(true);
		board.getCell(17, 7).setOccupied(true);
		board.calcTargets(board.getCell(17, 8), 2);
		board.getCell(16, 8).setOccupied(false);
		board.getCell(17, 7).setOccupied(false);
		Set<BoardCell> targets = board.getTargets();
		assertEquals(4, targets.size());
		assertTrue(targets.contains(board.getCell(16, 9)));
		assertTrue(targets.contains(board.getCell(18, 9)));
		assertTrue(targets.contains(board.getCell(18, 7)));
		assertTrue(targets.contains(board.getCell(19, 8)));
		
		// player in room we want to go in
		board.getCell(13, 24).setOccupied(true);
		board.calcTargets(board.getCell(13, 19), 1);
		board.getCell(13, 24).setOccupied(false);
		targets = board.getTargets();
		assertEquals(4, targets.size());
		assertTrue(targets.contains(board.getCell(13, 24)));
		
		// Leaving room with blocked doorway
		board.getCell(11, 5).setOccupied(true);
		board.calcTargets(board.getCell(14, 3), 4);
		board.getCell(11, 5).setOccupied(false);
		targets = board.getTargets();
		assertEquals(7, targets.size());
		assertTrue(targets.contains(board.getCell(18, 4)));
		assertTrue(targets.contains(board.getCell(16, 6)));
		assertTrue(targets.contains(board.getCell(18, 6)));
		assertTrue(targets.contains(board.getCell(19, 5)));
	}
	
}
