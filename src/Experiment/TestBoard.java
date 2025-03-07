//Authors: Chloe Millham, James Towle, written 2/27/2025
//This class creates TestBoard, intializing a board for various tests to be conducted on the structure
//of the generated board. The maximum dimensions of the board are set as 4x4, which is then used to create
//the 2-dimensional array that becomes the test board. 
//calcTargets() is used to make sure that movements from the initial spot to another spot is valid
//verifier() ensures that any moves made from a specific cell does not go out of bounds of the board dimensions
//getCell() allows access to the traits of the current cell
//getTargets() returns the list of possible moves created by calcTargets
package Experiment;
import java.util.Set;
import java.util.HashSet;

public class TestBoard {
    private Set<BoardCell> targetList;
    private Set<BoardCell> visited;
    private BoardCell[][] testboard;

    // Max range for calcTargets range
    private static int MAX_ROW_RANGE = 4;
    private static int MAX_COL_RANGE = 4;

    public TestBoard(){
        // set up 2d array of cells
        testboard = new BoardCell[MAX_ROW_RANGE][MAX_COL_RANGE];
        for (int i = 0; i<MAX_ROW_RANGE; i++){
            for (int j = 0; j<MAX_COL_RANGE; j++){
                testboard[i][j] =  new BoardCell(i,j);
            }
        }
        // create full adjacency lists
        createAdjList();
    }

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
                    testboard[i][j].addAdjacency(getCell(i,j-1));
                }
                if(i>0){
                    testboard[i][j].addAdjacency(getCell(i-1,j));
                }
                if(i<MAX_ROW_RANGE-1){
                    testboard[i][j].addAdjacency(getCell(i+1,j));
                }
                if(j<MAX_COL_RANGE-1){
                    testboard[i][j].addAdjacency(getCell(i,j+1));
                }
            }
        }
    }

    public BoardCell getCell(int row, int col){
    	// return cell at specified position
        return testboard[row][col];
    }

    public Set<BoardCell> getTargets(){
        // return set/list created by calcTargets
        return targetList;
    }
}
