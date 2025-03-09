package clueGame;

import java.util.HashSet;
import java.util.Set;

public class BoardCell {
	 // initialize class variables
    private int row, col;
    private char initial, secretPassage;
    private DoorDirection doorDirection;
    private boolean roomLabel, isRoom, isDoorway, roomCenter, occupied;

    // List of adjacent cells
    private Set<BoardCell> adjList;
    

	public BoardCell(int row, int column){
        this.row = row;
        this.col = column;
        adjList = new HashSet<BoardCell>();

        // setting initial booleans in case set__ function is not called
        this.isRoom = false;
        this.occupied = false;
    }

    public  void addAdj( BoardCell cell ){
    	adjList.add(cell);
    }

    public Set<BoardCell> getAdjList(){
        return adjList;
    }
    
    public void setType(char type) {
    	
    }

    public void setRoom(boolean room){
        this.isRoom = true;
    }

    public boolean isRoom(){
        return isRoom;
    }
    
    public void setName(char name) {
    	initial = name;
    }

    public char getName() {
        return initial;
    }

    public boolean isDoorway() {
        return isDoorway;
    }

    public boolean isLabel() {
        return roomLabel;
    }

    public boolean isRoomCenter() {
        return roomCenter;
    }

    public DoorDirection getDoorDirection() {
        return doorDirection;
    }
    
    public char getSecretPassage() {
        return secretPassage;
    }

    public void setOccupied(boolean occupied){
        this.occupied = occupied;
    }

    public boolean getOccupied(){
        return occupied;
    }
}
