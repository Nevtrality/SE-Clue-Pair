package clueGame;

public class BadConfigFormatException extends Exception{
	BadConfigFormatException(){
		super("Invalid board format");
	}

	BadConfigFormatException(String reason){
		super(reason);
	}
}