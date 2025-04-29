package clueGame;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JOptionPane;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.*;




public class ClueGame extends JFrame{
	private static Board board;
	private JPanel controlPanel;
	private JPanel cardPanel;
	public ClueGame() {
		board = Board.getInstance();
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		board.initialize();
		// set layout to allow center south and east bindings
		setLayout(new BorderLayout());
			// draw board grid
		add(drawBoard(), BorderLayout.CENTER);
			// draw control panel
		controlPanel = drawControlPanel();
		add(controlPanel, BorderLayout.SOUTH);
			// draw card panel
		cardPanel = drawCardPanel();
		cardPanel.setPreferredSize(new Dimension(120,0));
		add(cardPanel, BorderLayout.EAST);
		board.setGame(this);
	}
	
	private JPanel drawBoard() {
		board = Board.getInstance();
		return board;
	}
	
	public static void playSong() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
	    File song = new File("data/HouseTheme.wav");
	    AudioInputStream audioInput = AudioSystem.getAudioInputStream(song);
	    
	    Clip songClip = AudioSystem.getClip();
	    songClip.open(audioInput);
	    songClip.start();
	    
	}
	
	public static void jumpscare() {
		ImageIcon icon = new ImageIcon("data/housejumpscare.jpeg");
        JOptionPane.showMessageDialog(
                null,
                new JLabel("Where's my Vicodin", icon, JLabel.LEFT));
	}
	
	private JPanel drawControlPanel() {
		return new GameControlPanel(board);
	}
	
	private JPanel drawCardPanel() {
		GameCardPanel cardPanel = new GameCardPanel();
		cardPanel.tempSetPlayerList(board.getPlayers());
		cardPanel.updatePanels(board.getPlayers().get(0));
		return cardPanel;
	}
	
	public static void main(String[] args) {
		// initialize game's frame
		ClueGame gameFrame = new ClueGame();
		JOptionPane.showMessageDialog(null, "<html><center>You are House<center><html>\n"+"<html><center>Figure out how the patient<center><html>\n"+"<html><center>died before your stupid collegues<center><html>", "Welcome to House",JOptionPane.INFORMATION_MESSAGE);
		jumpscare();
		// set default game frame size
		gameFrame.setSize(900,900);
		gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gameFrame.setVisible(true);
		gameFrame.setTitle("Clue Game");
		// ideal board size(multiple of 28)x(multiple of 30)
		
		try {
			playSong();
		} catch(Exception e) {}
		
		gameFrame.revalidate();
		gameFrame.repaint();
		
	}
	
}
