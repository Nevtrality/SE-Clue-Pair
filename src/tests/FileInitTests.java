package tests;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.DoorDirection;
import clueGame.Room;

public class FileInitTests {
	// Constants used to test whether the file was loaded correctly
	public static final int LEGEND_SIZE = 11;
	public static final int NUM_ROWS = 30;
	public static final int NUM_COLUMNS = 28;

	private static Board board;

	// implement BeforeEach method
	@BeforeEach
	public void setUp(){
		// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		// Initialize will load BOTH config files
		board.initialize();
	}

	// ensure layout and setup file loaded correctly
	@Test
	public void testRoomLabels() {
		// To ensure data is correctly loaded, test retrieving a few rooms
		// include first, last, and a few others
		assertEquals("Waiting Room", board.getRoom('W').getName() );
		assertEquals("Breakroom", board.getRoom('B').getName());
		assertEquals("Psych Ward", board.getRoom('P').getName());
		assertEquals("Morgue", board.getRoom('G').getName());
		assertEquals("Hallway", board.getRoom('H').getName());
	}

	//ensure correct number of rows/columns have been read
	@Test
	public void testBoardDimensions() {
		// Ensure have the proper number of rows and columns
		assertEquals(NUM_ROWS, board.getNumRows());
		assertEquals(NUM_COLUMNS, board.getNumColumns());
	}

	// verify at least one doorway in each direction. verify cells dont contain doorways return false for isDoorway()
	@Test
	public void FourDoorDirections() {
		// test not doorway
		BoardCell cell = board.getCell(11,6);
		assertFalse(cell.isDoorway());
		// test left
		cell = board.getCell(11,5);
		assertTrue(cell.isDoorway());
		assertEquals(DoorDirection.LEFT, cell.getDoorDirection());
		// test up
		cell = board.getCell(6, 13);
		assertTrue(cell.isDoorway());
		assertEquals(DoorDirection.UP, cell.getDoorDirection());
		// test right
		cell = board.getCell(13,19);
		assertTrue(cell.isDoorway());
		assertEquals(DoorDirection.RIGHT, cell.getDoorDirection());
		// test down
		cell = board.getCell(23, 14);
		assertTrue(cell.isDoorway());
		assertEquals(DoorDirection.DOWN, cell.getDoorDirection());
	}

	// check correct number of doors have been loaded
	@Test
	public void testNumberOfDoorways() {
		int doors = 0;
		for (int i = 0; i < board.getNumRows(); i++)
			for (int j = 0; j < board.getNumColumns(); j++) {
				BoardCell cell = board.getCell(i, j);
				if (cell.isDoorway())
					doors++;
			}
		Assert.assertEquals(13, doors);
	}


	// check some of the cells to ensure they have correct initial
	@Test
	public void testInitial() {
		// test G and Morgue
		BoardCell cell = board.getCell(4, 5);
		Room room = board.getRoom(cell);
		assertEquals(room.getName(), board.getRoom('G').getName());
		
		// test B and Breakroom
		cell = board.getCell(17, 15);
		room = board.getRoom(cell);
		assertEquals(room.getName(), board.getRoom('B').getName());
		
		// test D and Day Care
		cell = board.getCell(24, 23);
		room = board.getRoom(cell);
		assertEquals(room.getName(), board.getRoom('D').getName());
		
		// test W and Walkway
		cell = board.getCell(0, 8);
		room = board.getRoom(cell);
		assertEquals(room.getName(), board.getRoom('W').getName());
		
		// test X and Unused
		cell = board.getCell(0, 10);
		room = board.getRoom(cell);
		assertEquals(room.getName(), board.getRoom('X').getName());
	}
	
	// check rooms have proper center cell and label cell
	@Test
	public void testRooms() {
		// test standard room cell 
		BoardCell cell = board.getCell(3,11);
		Room room = board.getRoom(cell);
		assertTrue(room != null);
		assertEquals( room.getName(), "Psych Ward" ) ;
		assertFalse( cell.isLabel() );
		assertFalse( cell.isRoomCenter() ) ;
		assertFalse( cell.isDoorway()) ;

		// test label cell 
		cell = board.getCell(12,23);
		room = board.getRoom(cell);
		assertTrue(room != null);
		assertEquals( room.getName(), "Surgery Room" ) ;
		assertTrue( cell.isLabel() );
		assertTrue( room.getLabelCell() == cell );

		// test label cell 
		cell = board.getCell(24,24);
		room = board.getRoom(cell);
		assertTrue(room != null);
		assertEquals( room.getName(), "Day Care" ) ;
		assertTrue( cell.isRoomCenter() );
		assertTrue( room.getCenterCell() == cell );

		// test secret passage cell
		cell = board.getCell(0,0);
		room = board.getRoom(cell);
		assertTrue(room != null);
		assertEquals( room.getName(), "Morgue" ) ;
		assertTrue( cell.getSecretPassage() == 'O' );
		
		// test walkway
		cell = board.getCell(13, 9);
		room = board.getRoom(cell);
		assertTrue(room != null);
		assertEquals( room.getName(), "Hallway" ) ;
		assertFalse( cell.isRoomCenter() );
		assertFalse( cell.isLabel() );
		
		// test unused
		cell = board.getCell(11, 10);
		room = board.getRoom(cell);
		assertTrue(room != null);
		assertEquals(room.getName(), "Unused");
		assertFalse(cell.isRoomCenter());
		assertFalse(cell.isLabel());
	}

}
