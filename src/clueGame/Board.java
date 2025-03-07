package clueGame;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Board {
	private Set<BoardCell> targetList;
    private Set<BoardCell> visited;
    private BoardCell[][] grid;
    int numRows;
    int numColumns;
    String layoutConfigFile;
    Map<Character, Room> roomMap;


    // Max range for calcTargets range
    private static int MAX_ROW_RANGE = 30;
    private static int MAX_COL_RANGE = 28;
    
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
    	
    }

    public void setConfigFiles(String csvFile, String dataFile) {

    }

    public void loadSetupConfig() {

    }

    public void loadLayoutConfig() {

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
