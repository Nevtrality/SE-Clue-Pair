package clueGame;
import java.util.Set;
import java.util.HashSet;

public class Room {
	private String name;
    private BoardCell centerCell;
    private BoardCell labelCell;
    private BoardCell secretPassage;
    private Set<BoardCell> doorways;
    
    public void setName(String name) {
    	this.name = name;
    	doorways = new HashSet<BoardCell>();
    }

    public String getName() {
        return name;
    }
    
    public void setLabelCell(BoardCell labelCell) {
    	this.labelCell = labelCell;
    }

    public BoardCell getLabelCell() {
        return labelCell;
    }
    
    public void setCenterCell(BoardCell centerCell){
    	this.centerCell = centerCell;
    }

    public BoardCell getCenterCell() {
        return centerCell;
    }
    
    public void setSecretPassage(BoardCell secretPassage) {
    	this.secretPassage = secretPassage;
    }
    
    public BoardCell getSecretPassage() {
        return secretPassage;
    }
    
    public void addDoorway(BoardCell doorway) {
    	doorways.add(doorway);
    }
    
    public Set<BoardCell> getDoorways(){
    	return doorways;
    }
    
}
