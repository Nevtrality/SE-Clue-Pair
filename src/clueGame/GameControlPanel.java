package clueGame;

import javax.swing.JFrame;
import javax.swing.JPanel;
import clueGame.Card;
import clueGame.CardType;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class GameControlPanel extends JPanel {
	private JTextField turnIndicator;
	private JTextField rollIndicator;
	private JTextField inputtedGuess;
	private JTextField guessResult;
	/**
	 * Constructor for the panel, it does 90% of the work
	 */
	public GameControlPanel()  {
		// contains outer 2 panels
		setLayout(new GridLayout(2,0)); // create a 2x0 layout
		
			// contains first row of panels
			JPanel panel = new JPanel();
			panel.setLayout(new GridLayout(1,4));
			
				// contains turn indicator
				JPanel innerPanel = new JPanel();
				innerPanel.setLayout(new GridLayout(3,0));
					createJLabel(innerPanel, "Whose turn?");
					turnIndicator = new JTextField();
					innerPanel.add(turnIndicator); // add text field
			panel.add(innerPanel);
				// contains roll indicator
				innerPanel = new JPanel();
				innerPanel.setLayout(new GridLayout(3,0));
					JPanel innerInnerPanel = new JPanel();
					innerInnerPanel.setLayout(new GridLayout(0,4));
					innerInnerPanel.add(new JPanel());
					createJLabel(innerInnerPanel, "Roll:");
					rollIndicator = new JTextField();
					innerInnerPanel.add(rollIndicator); // add text field
					innerPanel.add(innerInnerPanel);
			panel.add(innerPanel);
				// contains make accusation button
				innerPanel = new JPanel();
				innerPanel.setLayout(new GridLayout(1,0));
					JButton makeAccusation = new JButton("Make Accusation");
					innerPanel.add(makeAccusation);
			panel.add(innerPanel);
				// contains next turn button
				innerPanel = new JPanel();
				innerPanel.setLayout(new GridLayout(1,0));
					JButton nextTurn = new JButton("NEXT");
					innerPanel.add(nextTurn);
			panel.add(innerPanel);
			
		add(panel);
		
			// contains second row of panels
			panel = new JPanel();
			panel.setLayout(new GridLayout(0,2));
				// contains guess
				innerPanel = new JPanel();
				innerPanel.setLayout(new GridLayout(1,0));
					inputtedGuess = new JTextField();
					innerPanel.add(inputtedGuess); // add text field
					innerPanel.setBorder(new TitledBorder(new EtchedBorder(), "Guess"));
			panel.add(innerPanel);
				innerPanel = new JPanel();
				innerPanel.setLayout(new GridLayout(1,0));
					guessResult = new JTextField();
					innerPanel.add(guessResult); // add text field
					innerPanel.setBorder(new TitledBorder(new EtchedBorder(), "Guess Result"));
			panel.add(innerPanel);
		add(panel);
	}
	
	private void createJLabel(JPanel currentPanel, String labelText) {
		JLabel label = new JLabel(labelText, SwingConstants.CENTER);
		currentPanel.add(label); // add label field
	}
	
	public void setGuess(String guess) {
	    inputtedGuess.setText(guess);
	}
	
	public void setGuessResult(String result) {
		guessResult.setText(result);
	}
	
	public void setTurn(ComputerPlayer cpu, int roll) {
		turnIndicator.setText(cpu.getName());
		rollIndicator.setText(String.valueOf(roll));
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
		panel.setTurn(new ComputerPlayer( "Col. Mustard", "orange", 0, 0), 5);
		panel.setGuess( "I have no guess!");
		panel.setGuessResult( "So you have nothing?");
	}
}
