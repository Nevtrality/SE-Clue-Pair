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
    private Set<TestBoardCell> targetList;
    private Set<TestBoardCell> visited;
    private TestBoardCell[][] testboard;

    // Max range for calcTargets range
    private static int MAX_ROW_RANGE = 4;
    private static int MAX_COL_RANGE = 4;

    public TestBoard(){
        // set up 2d array of 
        testboard = new TestBoardCell[MAX_COL_RANGE][MAX_ROW_RANGE];
    }

    public void calcTargets(TestBoardCell startCell, int pathlength){
        // reset targetList
        targetList = new HashSet<TestBoardCell>();
        visited = new HashSet<TestBoardCell>();
        // Top Right
        verifier(startCell, pathlength, 1, 1);
    }

    private void verifier(TestBoardCell startCell, int pathlength, int rowChange, int colChange){
    	
        int row = startCell.row;
        int column = startCell.column;
        
        // append if pathlength = 0 && not already in set, break
        if(pathlength == 0){
            if(testboard[column][row] == null){
            	testboard[column][row] = new TestBoardCell(row,column);
                //targetList.add(testboard[column][row]);
                return;
            }
        }
        
        testboard[column][row] = new TestBoardCell(row,column);
        // make sure stays in bounds of row +change
        if((row+rowChange)<MAX_ROW_RANGE && (row+rowChange)>=0){
        	if(testboard[column][row+rowChange] == null) {
        		// verifier( cell + rowChange, pathlength-1)
        		verifier(new TestBoardCell(row+rowChange, column), pathlength-1, rowChange, colChange);
        	} else if(testboard[column][row+rowChange].getOccupied()) {
        		// break if next row index is player
            } else if(testboard[column][row+rowChange].isRoom()) {
            	// if next row index is room, append next row index, break
                //targetList.add(testboard[column][row+rowChange]);
            }            
        }
        
        
        
     // make sure stays in bounds of row -change
        if((row-rowChange)<MAX_ROW_RANGE && (row-rowChange)>=0){
        	if(testboard[column][row-rowChange] == null) {
        		// verifier( cell + rowChange, pathlength-1)
        		verifier(new TestBoardCell(row-rowChange, column), pathlength-1, rowChange, colChange);
        	} else if(testboard[column][row-rowChange].getOccupied()) {
        		// break if next row index is player
            } else if(testboard[column][row-rowChange].isRoom()) {
            	// if next row index is room, append next row index, break
                //targetList.add(testboard[column][row+rowChange]);
            }            
        }
        

        // make sure stays in bounds of column +change
        if((column+colChange)<MAX_COL_RANGE && (column+colChange)>=0){
        	if(testboard[column+colChange][row] == null) {
        		// verifier( cell + rowChange, pathlength-1)
        		verifier(new TestBoardCell(row, column+colChange), pathlength-1, rowChange, colChange);
        	} else if(testboard[column+colChange][row].getOccupied()){
        		// break if next column index is player
            } else if(testboard[column+colChange][row].isRoom()){
            	// if next column index is room, append next column index, break
                //targetList.add(testboard[column+colChange][row]);
            }
        }
        
     // make sure stays in bounds of column -change
        if((column-colChange)<MAX_COL_RANGE && (column-colChange)>=0){
        	if(testboard[column-colChange][row] == null) {
        		// verifier( cell + rowChange, pathlength-1)
        		verifier(new TestBoardCell(row, column-colChange), pathlength-1, rowChange, colChange);
        	} else if(testboard[column-colChange][row].getOccupied()){
        		// break if next column index is player
            } else if(testboard[column-colChange][row].isRoom()){
            	// if next column index is room, append next column index, break
                //targetList.add(testboard[column-colChange][row]);
            }
        }
    }

    public TestBoardCell getCell(int row, int col){
        // make sure not overriding existing cell
    	if(testboard[col][row] == null) {
    		testboard[col][row] = new TestBoardCell(row,col);
    	}
    	// return cell at specified position
        return testboard[col][row];
    }

    public Set<TestBoardCell> getTargets(){
        // return set/list created by calcTargets
        return targetList;
    }
}
