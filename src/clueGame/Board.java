package clueGame;

import java.util.HashSet;
import java.util.Map;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;
import java.io.File;
import java.io.FileNotFoundException;

public class Board {
	private Set<BoardCell> targetList;
    private Set<BoardCell> visited;
    private BoardCell[][] grid;
    int numRows;
    int numColumns;
    String layoutConfigFile;
    String setupConfigFile;
    Map<String, String> roomMap; // stores room name and character
    Map<String, String> cellTypeMap; // stores cell type and character


    // Max range for calcTargets range
    private static int MAX_ROW_RANGE;
    private static int MAX_COL_RANGE;
    
    // variable and methods used for singleton pattern
    private static Board theInstance = new Board();
    //constructor private to ensure only 1 created
    private Board() {
    	super();
    	// set up 2d array of cells
      grid = new BoardCell[MAX_ROW_RANGE][MAX_COL_RANGE];
      for (int i = 0; i<MAX_ROW_RANGE; i++){
          for (int j = 0; j<MAX_COL_RANGE; j++){
              grid[i][j] =  new BoardCell(i,j);
          }
      }
      // create full adjacency lists
      createAdjList();
    }
    // method returns only board
    public static Board getInstance() {
    	return theInstance;
    }
    //Initiates board
    public void initialize() {
    	loadSetupConfig();
    	loadLayoutConfig();
    }

    public void setConfigFiles(String csvFile, String dataFile) {
    	this.layoutConfigFile = csvFile;
    	this.setupConfigFile = dataFile;
    }

    public void loadSetupConfig() {
    	roomMap = new HashMap<String, String>();
    	cellTypeMap = new HashMap<String, String>();
    	
    	// open data file and put data in hashmap
    	try {
	    	File file = new File(setupConfigFile);
	    	Scanner reader = new Scanner(file);
	    	while(reader.hasNextLine()) {
	    		String data = reader.nextLine();
	    		String[] dataArray = data.split(",");
	    		roomMap.put(dataArray[2], dataArray[1]);
	    		cellTypeMap.put(dataArray[0], dataArray[2]);
	    	}
	    	reader.close();
    	} catch (FileNotFoundException e) {
    		System.out.println("Error occurred when reading setup file");
    		e.printStackTrace();
    	}
    }

    public void loadLayoutConfig() {
    	// get board size
    	int rowCount = 0;
		int columnCount = 0;
    	try {
    		File file = new File(layoutConfigFile);
    		Scanner reader = new Scanner(file);
    		while(reader.hasNextLine()) {
    			String data = reader.nextLine();
    			String[] dataArray = data.split(",");
    			columnCount = 0;
    			for(String cell : dataArray) {
    				columnCount++;
    			}
    			rowCount++;
    		}
    		reader.close();
    	} catch (FileNotFoundException e) {
    		System.out.println("Error occurred when reading layout file");
    		e.printStackTrace();
    	}
    	
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
    			String data = reader.nextLine();
    			String[] dataArray = data.split(",");
    			columnCount = 0;
    			// loop through each string in line separated by comma
    			for(String string : dataArray) {
    				grid[rowCount][columnCount] = new BoardCell(rowCount,columnCount);
    				BoardCell cell = grid[rowCount][columnCount];
    				// set cell's name and type
    				if (string.length()>1) {
    					cell.setName(string.charAt(0));
    					cell.setType(string.charAt(1));
    				} else {
    					cell.setName(string.charAt(0));
    				}
    				columnCount++;
    			}
    			rowCount++;
    		}
    		reader.close();
    	} catch (FileNotFoundException e) {
    		System.out.println("Error occurred when reading layout file");
    		e.printStackTrace();
    	}
    }

    public int getNumRows() {
        return numRows;
    }

    public int getNumColumns() {
        return numColumns;
    }

    public Room getRoom(char letter) {
        return new Room();
    }

    public Room getRoom(BoardCell cell) {
        return new Room();
    }


//    public Board(){
//        // set up 2d array of cells
//        testboard = new BoardCell[MAX_ROW_RANGE][MAX_COL_RANGE];
//        for (int i = 0; i<MAX_ROW_RANGE; i++){
//            for (int j = 0; j<MAX_COL_RANGE; j++){
//                testboard[i][j] =  new BoardCell(i,j);
//            }
//        }
//        // create full adjacency lists
//        createAdjList();
//    }

    public void calcTargets(BoardCell startCell, int pathlength){
        // reset targetList
        targetList = new HashSet<BoardCell>();
        visited = new HashSet<BoardCell>();
        // add start cell
        visited.add(startCell);
        verifier(startCell, pathlength);
        if(targetList.size()==0){targetList.add(startCell);} // base case for no valid moves
    }

    private void verifier(BoardCell startCell, int pathlength){
    	// go through cells in adjacency list
        for (BoardCell cell : startCell.getAdjList()){
            // if visited or occupied, skip over
            if (visited.contains(cell) || cell.getOccupied()){
            }else{
                // if the path ends here or cell is a room, end
                if(pathlength==1 || cell.isRoom()){
                    targetList.add(cell);
                } else{
                    // otherwise add the cell
                    visited.add(cell);
                    verifier(cell, pathlength-1);
                    visited.remove(cell);
                }
            }
        }
    }


    // make adjacency list for the board of cells
    private void createAdjList(){
        for (int i = 0; i<MAX_ROW_RANGE; i++){
            for (int j = 0; j<MAX_COL_RANGE; j++){
                if(j>0){
                    grid[i][j].addAdj(getCell(i,j-1));
                }
                if(i>0){
                    grid[i][j].addAdj(getCell(i-1,j));
                }
                if(i<MAX_ROW_RANGE-1){
                    grid[i][j].addAdj(getCell(i+1,j));
                }
                if(j<MAX_COL_RANGE-1){
                    grid[i][j].addAdj(getCell(i,j+1));
                }
            }
        }
    }

    public BoardCell getCell(int row, int col){
    	// return cell at specified position
        return grid[row][col];
    }

    public Set<BoardCell> getTargets(){
        // return set/list created by calcTargets
        return targetList;
    }
}
