package tests;
import Experiment.TestBoard;
import static org.junit.Assert.assertEquals;
import Experiment.TestBoardCell;
import java.util.Set;
import static org.junit.Assert.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BoardTestsExp {
    private TestBoard board;

    @BeforeEach // Run before each test
    public void setUp(){
        // should create adjacency list
        board = new TestBoard();
    }

    // Test creation of adjacency lists for 4x4 board
    @Test
    public void testTopLeft(){
        TestBoardCell cell = board.getCell(0,0);
        Set<TestBoardCell> testList = cell.getAdjList();
        assertTrue(testList.contains(board.getCell(1,0)));
        assertTrue(testList.contains(board.getCell(0,1)));
        assertEquals(2,testList.size());
    }

    @Test
    public void testBottomRight(){
        TestBoardCell cell = board.getCell(3,3);
        Set<TestBoardCell> testList = cell.getAdjList();
        assertTrue(testList.contains(board.getCell(2, 3)));
        assertTrue(testList.contains(board.getCell(3, 2)));
        assertEquals(2,testList.size());
    }

    @Test
    public void testRightEdge(){
        TestBoardCell cell = board.getCell(1,3);
        Set<TestBoardCell> testList = cell.getAdjList();
        assertTrue(testList.contains(board.getCell(0, 3)));
        assertTrue(testList.contains(board.getCell(1, 2)));
        assertEquals(2,testList.size());
    }
    
    @Test
    public void testLeftEdge(){
        TestBoardCell cell = board.getCell(3,0);
        Set<TestBoardCell> testList = cell.getAdjList();
        assertTrue(testList.contains(board.getCell(2, 0)));
        assertTrue(testList.contains(board.getCell(3, 1)));
        assertEquals(2,testList.size());
    }

    @Test
    public void testCenter(){
        TestBoardCell cell = board.getCell(2,2);
        Set<TestBoardCell> testList = cell.getAdjList();
        assertTrue(testList.contains(board.getCell(1,2)));
        assertTrue(testList.contains(board.getCell(2,1)));
        assertTrue(testList.contains(board.getCell(3,2)));
        assertTrue(testList.contains(board.getCell(2,3)));
        assertEquals(4,testList.size());
    }

    // Test target creation on 4x4 board
    @Test
    public void testEmpty(){    
        TestBoardCell cell = board.getCell(0,0);
        board.calcTargets(cell, 3);
        Set<TestBoardCell> targets = board.getTargets();
        assertEquals(6, targets.size());
        assertTrue(targets.contains(board.getCell(3,0)));
        assertTrue(targets.contains(board.getCell(2,1)));
        assertTrue(targets.contains(board.getCell(0,1)));
        assertTrue(targets.contains(board.getCell(1,2)));
        assertTrue(targets.contains(board.getCell(0,3)));
        assertTrue(targets.contains(board.getCell(1,0)));
    }
    @Test
    public void testOccupied(){
        // set up occupied cell
        board.getCell(1,1).setOccupied(true);
        TestBoardCell cell = board.getCell(0,1);
        board.calcTargets(cell, 3);
        Set<TestBoardCell> targets = board.getTargets();
        assertEquals(3, targets.size());
        assertTrue(targets.contains(board.getCell(2,0)));
        assertTrue(targets.contains(board.getCell(2,2)));
        assertTrue(targets.contains(board.getCell(1,3)));
    }

    @Test
    public void testRoom(){
        board.getCell(2,2).setRoom(true);
        TestBoardCell cell = board.getCell(1, 1);
        board.calcTargets(cell, 2);
        Set<TestBoardCell> targets = board.getTargets();
        assertEquals(2, targets.size());
        assertTrue(targets.contains(board.getCell(2,1)));
        assertTrue(targets.contains(board.getCell(1,2)));
    }

}
