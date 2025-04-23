package clueGame;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;

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
	private JButton makeAccusation;
	private JButton nextTurn;
	/**
	 * Constructor for the panel, it does 90% of the work
	 */
	public GameControlPanel(Board board)  {
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
					JPanel innerInnerPanel = new JPanel(); // only here so it looks nicer
					innerInnerPanel.setLayout(new GridLayout(0,4));
					innerInnerPanel.add(new JPanel()); // add empty space to make it prettier
					createJLabel(innerInnerPanel, "Roll:");
					rollIndicator = new JTextField();
					innerInnerPanel.add(rollIndicator); // add text field
					innerPanel.add(innerInnerPanel);
			panel.add(innerPanel);
				// contains make accusation button
				innerPanel = new JPanel();
				innerPanel.setLayout(new GridLayout(1,0));
					makeAccusation = new JButton("Make Accusation");
					
					// makeAccusation button listener
					innerPanel.add(makeAccusation);
			panel.add(innerPanel);
				// contains next turn button
				innerPanel = new JPanel();
				innerPanel.setLayout(new GridLayout(1,0));
					nextTurn = new JButton("NEXT!");
					
					// nextTurn button listener
					nextTurn.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent click) {
							handleNextButton(board);
						}
						
					});
					
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
		
		// start player's turn
		handleNextButton(board);
		
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
	
	public void setTurn(Player player, int roll) {
		turnIndicator.setText(player.getName()); 
		Color color = player.getColor();
		turnIndicator.setBackground(color);
		rollIndicator.setText(String.valueOf(roll));
	}
	
	public void handleNextButton(Board board) {
		try {
			// update player functions
			int roll = board.updatePlayer();
			Player currentPlayer = board.getCurrentPlayer();
			// update control panel
			setTurn(currentPlayer, roll);
			
			// check if player is human
			if(board.getCurrentPlayer().getType() == "Computer"){	// no
				// accusation?
				
				// move
				BoardCell playerTargetCell = currentPlayer.selectTarget(board, roll);
					// move player's sprite to that cell
				currentPlayer.updatePosition(playerTargetCell);

				
				// suggestion?
				if(playerTargetCell.isRoom()) {
					currentPlayer.createSuggestion(board);
				}
			}else {	// yes
				//display targets
				board.calcTargets(board.getCell(currentPlayer.row, currentPlayer.col), roll);
				Set<BoardCell> targets = board.getTargets();
					// loop through target list, change each cell/s color
				for(BoardCell cell : targets) {
					cell.updateColor(Color.lightGray);
				}
				//flag unfinished (what?)
			}
		// end
			board.repaint();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
	
	 //Main to test the panel
	public static void main(String[] args) {
		GameControlPanel panel = new GameControlPanel(Board.getInstance());  // create the panel
		JFrame frame = new JFrame();  // create the frame 
		frame.setContentPane(panel); // put the panel in the frame
		frame.setSize(750, 180);  // size the frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
		frame.setVisible(true); // make it visible
		
		// test filling in the data
		panel.setTurn(new ComputerPlayer( "Col. Mustard", "ORANGE", 0, 0), 5);
		panel.setGuess( "I have no guess!");
		panel.setGuessResult( "So you have nothing?");
	}
}
