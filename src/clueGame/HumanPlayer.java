package clueGame;

public class HumanPlayer extends Player{
	public HumanPlayer(String name, String color, int row, int column) {
		super(name, color, row, column);
	}

	public String getType(){
		return "Human";
	}

	@Override
	public Solution createSuggestion(Board board) {
		// TODO Auto-generated method stub
		PromptDialog dialogBox = new PromptDialog(board.getCell(row, col).getRoom());
		return dialogBox.getDialogSolution();
	}

	@Override
	public BoardCell selectTarget(Board board, int i) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void setAccusation(Solution accusation) {
		// do nothing
	}
}
