package clueGame;

public class Room {
	private String name;
    private BoardCell centerCell;
    private BoardCell labelCell;
    private BoardCell secretPassage;
    
    public void setName(String name) {
    	this.name = name;
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
}
