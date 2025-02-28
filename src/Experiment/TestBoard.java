package Experiment;
import java.util.Set;
import java.util.HashSet;

public class TestBoard {
    private Set<TestBoardCell> targetList;

    private TestBoard board;
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
        // Top Right
        verifier(startCell, pathlength, 1, 1);
        // Top Left
        verifier(startCell, pathlength, -1, 1);
        // Bottom Right
        verifier(startCell, pathlength, 1, -1);
        // Bottom Left
        verifier(startCell, pathlength, -1, -1);
    }

    private void verifier(TestBoardCell startCell, int pathlength, int rowChange, int colChange){
        int row = startCell.row;
        int column = startCell.column;
        
        // append if pathlength = 0 && not already in set, break
        if(pathlength == 0){
            if(testboard[column][row] == null){
            	testboard[column][row] = new TestBoardCell(row,column);
                targetList.add(testboard[column][row]);
                return;
            }
        }
        
        
        // make sure stays in bounds of row
        if((row+rowChange)<MAX_ROW_RANGE && (row+rowChange)>=0){
        	if(testboard[column][row+rowChange] == null) {
        		// verifier( cell + rowChange, pathlength-1)
        		verifier(new TestBoardCell(row+rowChange, column), pathlength-1, rowChange, colChange);
        		return;
        	}
            // break if next row index is player
            if(testboard[column][row+rowChange].getOccupied()){
                return;
            }
            // if next row index is room, append next row index, break
            if(testboard[column][row+rowChange].isRoom()){
                targetList.add(testboard[column][row+rowChange]);
                return;
            }            
        }

        // make sure stays in bounds of column
        if((column+colChange)<MAX_COL_RANGE && (column+colChange)>=0){
        	if(testboard[column+colChange][row] == null) {
        		// verifier( cell + rowChange, pathlength-1)
        		verifier(new TestBoardCell(row, column+colChange), pathlength-1, rowChange, colChange);
        		return;
        	}
            // break if next column index is player
            if(testboard[column+colChange][row].getOccupied()){
                return;
            }
            // if next column index is room, append next column index, break
            if(testboard[column+colChange][row].isRoom()){
                targetList.add(testboard[column][row+rowChange]);
                return;
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
