package clueGame;

import java.util.HashSet;
import java.util.Set;

public class BoardCell {
	 // initialize class variables
    private int row, col;
    private char initial, secretPassage;
    private DoorDirection doorDirection;
    private boolean roomLabel, isRoom, isDoorway, roomCenter, occupied;
    private Room room;

    // List of adjacent cells
    private Set<BoardCell> adjList;
    

	public BoardCell(int row, int column){
		room = new Room();
        this.row = row;
        this.col = column;
        adjList = new HashSet<BoardCell>();

        // setting initial booleans in case set__ function is not called
        this.isRoom = false;
        this.occupied = false;
        roomLabel = false;
        isDoorway = false;
        roomCenter = false;
    }

    public  void addAdj( BoardCell cell ){
    	adjList.add(cell);
    }

    public Set<BoardCell> getAdjList(){
        return adjList;
    }
    
    public void setType(char type) {
    	switch (type) {
    	case '#':
    		isRoom = true;
    		// label cell
    		roomLabel = true;
    		room.setLabelCell(this);
    		break;
    	case '*':
    		isRoom = true;
    		// center cell
    		roomCenter = true;
    		room.setCenterCell(this);
    		break;
    	case '^':
    		isRoom = false;
    		// door up cell
    		isDoorway = true;
    		doorDirection = DoorDirection.UP;
    		break;
    	case '>':
    		isRoom = false;
    		// door right cell
    		isDoorway = true;
    		doorDirection = DoorDirection.RIGHT;
    		break;
    	case 'v':
    		isRoom = false;
    		// door down cell
    		isDoorway = true;
    		doorDirection = DoorDirection.DOWN;
    		break;
    	case'<':
    		isRoom = false;
    		// door left cell
    		isDoorway = true;
    		doorDirection = DoorDirection.LEFT;
    		break;
    	default:
    		isRoom = true;
    		//secret passage
    		secretPassage = type;
    		room.setSecretPassage(this);
    		
    	}
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
    
    public void setRoom(Room room) {
    	this.room = room;
    }
    
    public Room getRoom() {
    	return room;
    }
}
