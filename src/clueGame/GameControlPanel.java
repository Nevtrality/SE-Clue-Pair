package clueGame;

import javax.swing.JFrame;
import javax.swing.JPanel;
import clueGame.Card;
import clueGame.CardType;

public class GameControlPanel extends JPanel {
	/**
	 * Constructor for the panel, it does 90% of the work
	 */
	public GameControlPanel()  {
		
	
	}
	
	public void setGuess(String guess) {
	    theGuess.setText(guess);
	}
	
	public void updatePanels() {
		updatePanel(peoplePanel, Card.CardType.PERSON);
		updatePanel(roomPanel, Card.CardType.ROOM);
		updatePanel(weaponPanel, Card.CardType.WEAPON);
	}

	
	 //Main to test the panel
	public static void main(String[] args) {
		GameControlPanel panel = new GameControlPanel();  // create the panel
		JFrame frame = new JFrame();  // create the frame 
		frame.setContentPane(panel); // put the panel in the frame
		frame.setSize(750, 180);  // size the frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
		frame.setVisible(true); // make it visible
		
		// test filling in the data
		panel.setTurn(new ComputerPlayer( "Col. Mustard", 0, 0, "orange"), 5);
		panel.setGuess( "I have no guess!");
		panel.setGuessResult( "So you have nothing?");
	}
}
