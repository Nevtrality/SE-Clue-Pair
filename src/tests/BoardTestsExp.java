package tests;
import Experiment.TestBoard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BoardTestsExp {
    TestBoard board;

    @BeforeEach // Run before each test
    public void setUp(){
        // should create adjacency list
        board = new TestBoard();
    }

    // Test creation of adjacency lists for 4x4 board
    @Test
    public void testTopLeft(){

    }

    @Test
    public void testBottomRight(){

    }

    @Test
    public void testRightEdge(){

    }

    @Test
    public void testLeftEdge(){

    }

    // Test target creation on 4x4 board
    @Test
    public void testEmpty(){    }

    @Test
    public void testOccupied(){

    }

    @Test
    public void testRoom(){

    }

}
