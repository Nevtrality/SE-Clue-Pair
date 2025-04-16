package clueGame;

import java.awt.Color;
import java.awt.Graphics;
import java.util.HashSet;
import java.util.Set;

public class BoardCell {
	// initialize class variables
	private int row, col;
	private char initial, secretPassage;
	private DoorDirection doorDirection;
	private boolean roomLabel, isRoom, isDoorway, roomCenter, occupied, unused;
	private Room room;

	// List of adjacent cells
	private Set<BoardCell> adjList;


	public BoardCell(int row, int column){
		room = new Room();
		this.row = row;
		this.col = column;
		adjList = new HashSet<BoardCell>();

		this.isRoom = false;
		this.occupied = false;
		roomLabel = false;
		isDoorway = false;
		roomCenter = false;
		unused = false;
		secretPassage = '\0';
	}

	public  void addAdj( BoardCell cell , Board board){
		// make sure only valid cells are in adjacency list
		if(!cell.isRoom() && !cell.getUnused()) {
			// check to make sure the intended cell is not a room cell or an unused cell
			adjList.add(cell);
		} else if (isDoorway == true && cell.isRoom()) {
			//Make sure the arrow is facing the door of a particular room
			if((cell==board.getCell(row, col-1) && doorDirection == DoorDirection.LEFT) || (cell==board.getCell(row, col+1) && doorDirection == DoorDirection.RIGHT) || (cell==board.getCell(row+1, col) && doorDirection == DoorDirection.DOWN) || ((cell==board.getCell(row-1, col) && doorDirection == DoorDirection.UP) )) {
				// if the intended cell is a room cell, grab the room's center cell and add it to list
				if(!adjList.contains(cell.getRoom().getCenterCell())) {
					adjList.add(cell.getRoom().getCenterCell());
				}
			}
		} else if (roomCenter == true) {
			// check if current room is a room center
			// grab any doorways to the room
			Set<BoardCell> doorways = room.getDoorways();
			for(BoardCell door: doorways) {
				if(!adjList.contains(door)) {
					adjList.add(door);
				}
			}
			if(room.getSecretPassage() != null) {
				// check if room has a secret passage
				// if so, add passage to list
				char passage = room.getSecretPassage().getSecretPassage();
				if (!adjList.contains(board.getRoom(passage).getCenterCell())){
					adjList.add(board.getRoom(passage).getCenterCell());
				}
			}
		}
	}

	public Set<BoardCell> getAdjList(){
		return adjList;
	}


	public void setType(char type) {
		switch (type) {
		case '#':
			room = Board.getInstance().getRoom(initial);
			isRoom = true;
			// label cell
			roomLabel = true;
			room.setLabelCell(this);
			break;
		case '*':
			room = Board.getInstance().getRoom(initial);
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

	public void drawDoor(Graphics graphics, int cellWidth, int cellHeight) {
		int x = col * cellWidth;
		int y = row * cellHeight;

		if (doorDirection == DoorDirection.UP) {
			graphics.setColor(Color.black);
			graphics.fillRect(x, y, cellWidth, (int)(cellHeight * 0.2));
		}
		else if (doorDirection == DoorDirection.DOWN) {
			graphics.setColor(Color.black);
			graphics.fillRect(x, y + cellHeight - 1, cellWidth, (int)(cellHeight * 0.2));
		}
		else if (doorDirection == DoorDirection.LEFT) {
			graphics.setColor(Color.black);
			graphics.fillRect(x, y, (int)(cellWidth * 0.2), cellHeight);
		}
		else if (doorDirection == DoorDirection.RIGHT) {
			graphics.setColor(Color.black);
			graphics.fillRect(x + cellWidth - 1, y, (int)(cellWidth * 0.2), cellHeight);
		}
	}

	public void draw(Graphics graphics, int cellWidth, int cellHeight) {


		if(isDoorway) {
			graphics.setColor(Color.yellow);
		}
		else if (secretPassage != '\0') {
			graphics.setColor(Color.red);
		}
		else if(isRoom()) {
			graphics.setColor(Color.magenta);
		}
		else {
			graphics.setColor(Color.blue);
		}

		int x = col * cellWidth;
		int y = row * cellHeight;

		graphics.drawRect(x, y, cellWidth, cellHeight);
		graphics.fillRect(x, y, cellWidth, cellHeight);

		if(!isRoom) {
			graphics.setColor(Color.black);
			graphics.drawRect(x, y, cellWidth, cellHeight);
		}

	}

	public void drawRoomName(Graphics graphics, int cellWidth, int cellHeight, String roomName) {
		graphics.setColor(Color.black);
		graphics.drawString(roomName, cellWidth, cellHeight);
	}

	public int getRow(){
		return row;
	}

	public int getCol(){
		return col;
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

	public void setUnused(boolean unused) {
		this.unused = unused;
	}

	public boolean getUnused() {
		return unused;
	}
}
