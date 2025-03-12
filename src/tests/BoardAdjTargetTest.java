package tests;

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
		// MRI Room test (Multiple doors)
		// Surgery Room (Multiple doors, has secret room)
		// Test room cell that isn't center of room
	}
	
	// Ensure doors include rooms and also walkways
	// Cells are yellow on spreadsheet
	@Test
	public void testAdjacencyDoors() {
		// Test door to Surgery Room on [17,22]
		// Test door to Waiting Room on [23,14]
		// Test door to Morgue on [6,6]
	}
	
	// Test walkway scenarios
	// Cells are orange on spreadsheet
	@Test
	public void testAdjacencyWalkways() {
		// Test walkway imbedded in room and next to unused space ie [27,6]
		// Test walkway on edge of board ie [18,27]
		// Test open walkway ie [8,8]
		// Test walkway next to door ie [4,19]
	}
	
	// Test targets from walkways
	// Cells are pink on spreadsheet
	@Test
	public void testTargetsFromWalkways() {
		// Test targets other walkways
		// Test targets doors
	}
	
	// Test targets from room center
	// Cells are blue on spreadsheet
	@Test
	public void testTargetsFromRoom() {
		// Test targets leaving room without secret passage (various distances)
		// Test targets leaving room with secret passage (various distances)
	}
	
	// Tests targets when other player blocks path
	// cells are red on spreadsheet
	@Test
	public void testTargetsBlocking() {
		// place players on board and test paths that intersect with them
	}
	
}
