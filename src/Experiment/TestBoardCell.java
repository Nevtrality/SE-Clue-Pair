package Experiment;
import java.util.Set;
import java.util.HashSet;

public class TestBoardCell {
    // initialize class variables
    public int row;
    public int column;
    private boolean room;
    private boolean occupied;

    // List of adjacent cells
    private Set<TestBoardCell> adjList;
    

	public TestBoardCell(int row, int column){
        this.row = row;
        this.column = column;
        adjList = new HashSet<TestBoardCell>();

        // setting initial booleans in case set__ function is not called
        this.room = false;
        this.occupied = false;
    }

    public  void addAdjacency( TestBoardCell cell ){
        adjList.add(cell);
    }

    public Set<TestBoardCell> getAdjList(){
        return adjList;
    }

    public void setRoom(boolean room){
        this.room = room;
    }

    public boolean isRoom(){
        return room;
    }

    public void setOccupied(boolean occupied){
        this.occupied = occupied;
    }

    public boolean getOccupied(){
        return occupied;
    }
}