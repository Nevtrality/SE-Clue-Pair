package clueGame;

public class Room {
	private String name;
    private BoardCell centerCell;
    private BoardCell labelCell;
    private BoardCell secretPassage;

    public String getName() {
        return name;
    }

    public BoardCell getLabelCell() {
        return labelCell;
    }

    public BoardCell getCenterCell() {
        return centerCell;
    }
    
    public BoardCell getSecretPassage() {
        return secretPassage;
    }
}
