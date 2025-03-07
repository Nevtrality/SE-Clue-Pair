package clueGame;

public enum DoorDirection {
	UP ("^"),DOWN ("v"),LEFT ("<"),RIGHT (">"),NONE ("-");

	private String symbol;

	private DoorDirection(String symbol) {
		this.symbol = symbol;
	}

	
}