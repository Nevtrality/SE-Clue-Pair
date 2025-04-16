package clueGame;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.Random;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.Graphics;
import java.io.File;
import java.io.FileNotFoundException;

public class Board extends JPanel{
	private Set<BoardCell> targetList;
	private Set<BoardCell> visited;
	private BoardCell[][] grid;
	private List<Card> deck;
	private List<Player> players;
	int numRows;
	int numColumns;
	int DECKSIZE = 21;
	int PLAYERCOUNT = 6;
	String layoutConfigFile;
	String setupConfigFile;
	Solution solution;
	Map<Character, Room> roomMap; // stores room name and character


	// Max range for calcTargets range
	private static int MAX_ROW_RANGE;
	private static int MAX_COL_RANGE;

	// variable and methods used for singleton pattern
	private static Board theInstance = new Board();
	//constructor private to ensure only 1 created
	private Board() {
		super();
	}
	// method returns only board
	public static Board getInstance() {
		return theInstance;
	}

	//NEW STUFF
	// draws board and players
	public void paintComponent(Graphics boardGraphic) {
		// call paintComponent's super
		super.paintComponent(boardGraphic);

		// get size of cell based on JFrame's dimensions
		// get width of cell
		int cellWidth = this.getWidth() / MAX_COL_RANGE;
		// get height of cell
		int cellHeight = this.getHeight() / MAX_ROW_RANGE;

		// loop through each row in grid
		for(BoardCell[] row : grid) {
			// loop through each cell on row
			for(BoardCell cell : row) {
				// call cell's draw function
				cell.draw(boardGraphic, cellWidth, cellHeight);
			}
		}

		//loop through players
		for (Player player : players) {
			//draw a player figure for each player
			player.draw(boardGraphic, cellWidth, cellHeight);
		}

		for(Character roomChar : roomMap.keySet()) {
			// call cell's draw function
			if (roomChar != 'W' && roomChar != 'X') {
				Room room = roomMap.get(roomChar);
				BoardCell cell = room.getLabelCell();
				cell.drawRoomName(boardGraphic, cellWidth * cell.getCol(), cellHeight * cell.getRow(), room.getName());

			}
		}
		
		// loop through each row in grid
				for(BoardCell[] row : grid) {
					// loop through each cell on row
					for(BoardCell cell : row) {
						// call cell's draw function
						cell.drawDoor(boardGraphic, cellWidth, cellHeight);
					}
				}



	}

	//Initiates board
	public void initialize() {
		try {
			loadSetupConfig();
			loadLayoutConfig();
			deal();
			theInstance.createAdjList();
		} catch (BadConfigFormatException e) {
			e.printStackTrace();
		}
	}

	public void setConfigFiles(String csvFile, String dataFile) {
		this.layoutConfigFile = "data/"+csvFile;
		this.setupConfigFile = "data/"+dataFile;
	}

	public void loadSetupConfig() throws BadConfigFormatException{
		roomMap = new HashMap<Character, Room>();
		deck = new ArrayList<Card>();
		players = new ArrayList<Player>();

		// open data file and put data in hashmap
		try {
			File file = new File(setupConfigFile);
			Scanner reader = new Scanner(file);
			int countDeck = 0;
			int countPlayer = 0;
			// loop through each row
			while(reader.hasNextLine()) {
				// grab next line
				String line = reader.nextLine();
				// ignore commented parts of file
				if(!(line.charAt(0) == '/')) {
					// split line into usable strings
					String[] splitLine = line.split(", ");
					if(!splitLine[0].equals( "Room" ) && !splitLine[0].equals( "Space") && !splitLine[0].equals( "Person") && !splitLine[0].equals( "Weapon")) {
						throw new BadConfigFormatException("Area must be a room or a space");
					}
					if(splitLine[0].equals( "Room") || splitLine[0].equals( "Space")) {
						roomMap.put(splitLine[2].charAt(0), new Room());
						roomMap.get(splitLine[2].charAt(0)).setName(splitLine[1]); // set room name
					}
					if(!splitLine[0].equals("Space")) {
						deck.add(new Card(splitLine[1], splitLine[0]));
						countDeck++;
					}
					if(splitLine[0].equals("Person")) {
						if(splitLine[3].equals("Human")) {
							players.add(new HumanPlayer(splitLine[1], splitLine[2], Integer.valueOf(splitLine[4]), Integer.valueOf(splitLine[5])));
						} else {
							players.add(new ComputerPlayer(splitLine[1], splitLine[2], Integer.valueOf(splitLine[4]), Integer.valueOf(splitLine[5])));
						}
						countPlayer++;
					}
				}
			}
			reader.close();
		} catch (FileNotFoundException e) {
			System.out.println("Error occurred when reading setup file");
			e.printStackTrace();
		}
	}

	public void loadLayoutConfig() throws  BadConfigFormatException{
		// get board size
		int rowCount = 0;
		int columnCount = 0;
		try {
			File file = new File(layoutConfigFile);
			Scanner reader = new Scanner(file);
			// loop through each line of board
			while(reader.hasNextLine()) {
				String line = reader.nextLine();
				String[] splitLine = line.split(",");
				columnCount = 0;
				// loop through each column of board
				for(String cell : splitLine) {
					columnCount++;
				}
				rowCount++;
			}
			reader.close();
			// catch in place for the case of a file not found error
		} catch (FileNotFoundException e) {
			System.out.println("Error occurred when reading layout file");
			e.printStackTrace();
		}

		//set maximum row and column values
		MAX_ROW_RANGE = rowCount;
		MAX_COL_RANGE = columnCount;

		// fill board from file
		grid = new BoardCell[rowCount][columnCount];
		try {
			// open file and reader
			File file = new File(layoutConfigFile);
			Scanner reader = new Scanner(file);
			rowCount = 0;
			// loop through file's lines
			while(reader.hasNextLine()) {
				String line = reader.nextLine();
				String[] splitLine = line.split(",");
				columnCount = 0;
				// loop through each string in line separated by comma
				for(String string : splitLine) {
					grid[rowCount][columnCount] = new BoardCell(rowCount,columnCount);
					BoardCell cell = grid[rowCount][columnCount];
					//Check if room exists
					if(roomMap.get(string.charAt(0)) == null) {
						throw new BadConfigFormatException("Room is not in setup file");
					}
					// set cell's name and type
					cell.setRoom(roomMap.get(string.charAt(0)));
					if (string.length()==2 && !(string.charAt(1)==' ')) {
						cell.setName(string.charAt(0));
						cell.setType(string.charAt(1));
						// 
					} else if((string.charAt(0)=='W')) {
						cell.setName(string.charAt(0));
					} else if((string.charAt(0)=='X')){
						cell.setName(string.charAt(0));
						cell.setUnused(true); 
					} else {
						cell.setName(string.charAt(0));
						cell.setRoom(true);
					}
					columnCount++;
				}

				if(columnCount != MAX_COL_RANGE) {
					throw new BadConfigFormatException("Does not have the same number of columns in every row");
				}

				rowCount++;
			}
			// loops through board to add all doorways that lead to a particular room to the corresponding room's list
			for(int row = 0; row < MAX_ROW_RANGE; row++) {
				for(int col = 0; col < MAX_COL_RANGE; col++) {
					BoardCell cell = theInstance.getCell(row, col);
					if(cell.isDoorway()) {
						switch(cell.getDoorDirection()) {
						case DoorDirection.UP:
							theInstance.getCell(row - 1, col).getRoom().addDoorway(cell);
							break;
						case DoorDirection.RIGHT:
							theInstance.getCell(row, col + 1).getRoom().addDoorway(cell);
							break;
						case DoorDirection.DOWN:
							theInstance.getCell(row + 1, col).getRoom().addDoorway(cell);
							break;
						case DoorDirection.LEFT:
							theInstance.getCell(row, col - 1).getRoom().addDoorway(cell);
							break;
						default:
						}
					}
				}
			}
			reader.close();

		} catch (FileNotFoundException e) {
			System.out.println("Error occurred when reading layout file");
			e.printStackTrace();
		}
	}

	public boolean checkAccusation(Solution accusation) {
		// if room = solution.room, person = solution.person, and weapon = solution.weapon
		if (solution.getRoom().equals(accusation.getRoom()) && solution.getPerson().equals(accusation.getPerson()) && solution.getWeapon().equals(accusation.getWeapon())) {
			// return true
			return true;
		} else {
			// else publicly execute player
			return false;
		}
	}

	public Card handleSuggestion(Player suggester, Solution suggestion) {
		Card proof;
		// calls players disprove solution to see if they have card to disprove
		for(Player player:players) {
			// skip over player that suggested the solution
			if(player != suggester) {
				// temp variable to hold result of disproveSuggestion
				proof = player.disproveSuggestion(suggestion);
				// if it returned a card, return that card and exit function
				if(proof != null) {
					return proof;
				}
			}
		}
		// No players could disprove, so return null
		return null;
	}

	//create get functions
	public int getNumRows() {
		return MAX_ROW_RANGE;
	}

	public int getNumColumns() {
		return MAX_COL_RANGE;
	}

	public List<Player> getPlayers() {
		return players;
	}

	public List<Card> getDeck(){
		return deck;
	}

	public Solution getSolution(){
		return solution;
	}

	public Room getRoom(char letter) {
		return roomMap.get(letter);
	}

	public Room getRoom(BoardCell cell) {
		return cell.getRoom();
	}

	public void calcTargets(BoardCell startCell, int pathlength){
		// reset targetList
		targetList = new HashSet<BoardCell>();
		visited = new HashSet<BoardCell>();
		// add start cell
		visited.add(startCell);
		cellCheck(startCell, pathlength);
		if(targetList.size() == 0){targetList.add(startCell);} // base case for no valid moves
	}

	private void cellCheck(BoardCell startCell, int pathlength){
		// go through cells in adjacency list
		for (BoardCell cell : startCell.getAdjList()){
			// if visited or occupied, skip over
			if (visited.contains(cell) || (cell.getOccupied()&& !cell.isRoom())){
			} else {
				// if the path ends here or cell is a room, end
				if(pathlength==1 || cell.isRoom()){
					targetList.add(cell);
				} else {
					// otherwise add the cell
					visited.add(cell);
					cellCheck(cell, pathlength-1);
					visited.remove(cell);
				}
			}
		}
	}


	// make adjacency list for the board of cells
	private void createAdjList(){
		for (int row = 0; row < MAX_ROW_RANGE; row++){
			for (int col = 0; col < MAX_COL_RANGE; col++){
				if(col > 0){
					grid[row][col].addAdj(getCell(row,col-1), theInstance);
				}
				if(row > 0){
					grid[row][col].addAdj(getCell(row-1,col), theInstance);
				}
				if(row < MAX_ROW_RANGE-1){
					grid[row][col].addAdj(getCell(row+1,col), theInstance);
				}
				if(col < MAX_COL_RANGE-1){
					grid[row][col].addAdj(getCell(row,col+1), theInstance);
				}
			}
		}
	}

	public Set<BoardCell> getAdjList(int row, int col){
		// return adjacency list for current position in grid
		return grid[row][col].getAdjList();
	}

	public BoardCell getCell(int row, int col){
		// return cell at specified position
		return grid[row][col];
	}

	public Set<BoardCell> getTargets(){
		// return set/list created by calcTargets
		return targetList;
	}

	public void deal() {
		if(deck.size() < 10) {
			return;
		}
		Random rand = new Random();
		// deal deck to solution
		List<Card> tempdeck = new ArrayList<Card>();
		for(Card card : deck) {
			tempdeck.add(card);
		}
		int roomLoc = rand.nextInt(0,9);
		int personLoc = rand.nextInt(9,15);
		int weaponLoc = rand.nextInt(15,21);
		solution = new Solution(deck.get(roomLoc), deck.get(personLoc), deck.get(weaponLoc));
		tempdeck.remove(roomLoc);
		tempdeck.remove(personLoc-1);
		tempdeck.remove(weaponLoc-2);
		// deal deck to players
		for(int i = DECKSIZE - 3; i > 0; i--) {
			int num = rand.nextInt(0,i);
			Card card = tempdeck.get(num);
			while(card==null) {
				num = rand.nextInt(0,21);
				System.out.println(num);
				card = tempdeck.get(num);
			}
			players.get(i%6).updateHand(card);
			tempdeck.remove(num);
		}
	}

}
