package clueGame;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class GameCardPanel extends JPanel{
	JPanel playerPanel;
	JPanel roomPanel;
	JPanel weaponPanel;
	
	public GameCardPanel() {
		// set layout
		setLayout(new GridLayout(3,0));
		// add border
		setBorder(new TitledBorder(new EtchedBorder(), "Known Cards"));
		// set layouts
		// update Players
				playerPanel = new JPanel();
				playerPanel.setLayout(new GridLayout(2,0));
					// add border
					playerPanel.setBorder(new TitledBorder(new EtchedBorder(), "People"));
					// update hand panel
					playerPanel.add(updateHandPanel(new ArrayList<Card>()));
					// update seen panel
					playerPanel.add(updateSeenPanel(new ArrayList<Card>()));
				
				add(playerPanel);
					
				// update rooms
				roomPanel = new JPanel();
				roomPanel.setLayout(new GridLayout(2,0));
					// add border
					roomPanel.setBorder(new TitledBorder(new EtchedBorder(), "Rooms"));
					// update hand panel
					roomPanel.add(updateHandPanel(new ArrayList<Card>()));
					// update seen panel
					roomPanel.add(updateSeenPanel(new ArrayList<Card>()));
				
				add(roomPanel);
				
				// update weapons
				weaponPanel = new JPanel();
				weaponPanel.setLayout(new GridLayout(2,0));
					// add border
					weaponPanel.setBorder(new TitledBorder(new EtchedBorder(), "Weapons"));
					// update hand panel
					weaponPanel.add(updateHandPanel(new ArrayList<Card>()));
					// update seen panel
					weaponPanel.add(updateSeenPanel(new ArrayList<Card>()));
				
				add(weaponPanel);
	}
	
	private void createJLabel(JPanel currentPanel, String labelText) {
		JLabel label = new JLabel(labelText, SwingConstants.CENTER);
		currentPanel.add(label); // add label field
	}
	
	public void updatePanels(Player player) {
		// import player's hand and seen cards into lists
		List<Card> hand = player.getHand();
		Set<Card> seen = player.getSeen();

		// loop through hand to separate all cards based on type
		List<Card> handPerson = new ArrayList<Card>();
		List<Card> handRoom = new ArrayList<Card>();
		List<Card> handWeapon = new ArrayList<Card>();
		for(Card card : hand) {
			if(card.getCardType() == CardType.PERSON) {
				handPerson.add(card);
			} else if (card.getCardType() == CardType.ROOM) {
				handRoom.add(card);
			} else {
				handWeapon.add(card);
			}
		}

		// loop through seen cards to separate all cards based on type
		List<Card> seenPerson = new ArrayList<Card>();
		List<Card> seenRoom = new ArrayList<Card>();
		List<Card> seenWeapon = new ArrayList<Card>();
		for(Card card : seen) {
			if(card.getCardType() == CardType.PERSON) {
				seenPerson.add(card);
			} else if (card.getCardType() == CardType.ROOM) {
				seenRoom.add(card);
			} else {
				seenWeapon.add(card);
			}
		}
		
		// update Players
		playerPanel = new JPanel();
		playerPanel.setLayout(new GridLayout(2,0));
			// add border
			playerPanel.setBorder(new TitledBorder(new EtchedBorder(), "People"));
			// update hand panel
			playerPanel.add(updateHandPanel(handPerson));
			// update seen panel
			playerPanel.add(updateSeenPanel(seenPerson));
		
		add(playerPanel);
			
		// update rooms
		roomPanel = new JPanel();
		roomPanel.setLayout(new GridLayout(2,0));
			// add border
			roomPanel.setBorder(new TitledBorder(new EtchedBorder(), "Rooms"));
			// update hand panel
			roomPanel.add(updateHandPanel(handRoom));
			// update seen panel
			roomPanel.add(updateSeenPanel(seenRoom));
		
		add(roomPanel);
		
		// update weapons
		weaponPanel = new JPanel();
		weaponPanel.setLayout(new GridLayout(2,0));
			// add border
			weaponPanel.setBorder(new TitledBorder(new EtchedBorder(), "Weapons"));
			// update hand panel
			weaponPanel.add(updateHandPanel(handWeapon));
			// update seen panel
			weaponPanel.add(updateSeenPanel(seenWeapon));
		
		add(weaponPanel);
	}
	
	private JPanel updateHandPanel(List<Card> hand) {
		JPanel handPanel = new JPanel();
		handPanel.setLayout(new GridLayout(2+hand.size(),0));
			// create label
			createJLabel(handPanel, "In Hand:");
			JTextField handCards;
			// if the list is empty, return a single text field with "None"
			if(hand.size() == 0) {
				handCards = new JTextField();
				handCards.setText("None");
				handPanel.add(handCards);
			} else {
				// loop through list of cards and add to panel
				for (Card card: hand) {
					handCards = new JTextField(card.getCardName());
					handPanel.add(handCards);
				}
			}
		return handPanel;
	}
	
	private JPanel updateSeenPanel(List<Card> seen) {
		JPanel seenPanel = new JPanel();
		seenPanel.setLayout(new GridLayout(2+seen.size(),0));
			// create label
			createJLabel(seenPanel, "Seen:");
			JTextField seenCards;
			// if list is empty, return single text field with "None"
			if(seen.size() == 0) {
				seenCards = new JTextField();
				seenCards.setText("None");
				seenPanel.add(seenCards);
			} else {
				// loop through list of cards and add to panel
				for (Card card: seen) {
					seenCards = new JTextField(card.getCardName());
					seenPanel.add(seenCards);
				}
			}
		return seenPanel;
	}
	
	public static void main(String[] args) {
		GameCardPanel panel = new GameCardPanel();  // create the panel
		JFrame frame = new JFrame();  // create the frame 
		frame.setContentPane(panel); // put the panel in the frame
		frame.setSize(180, 570);  // size the frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
		frame.setVisible(true); // make it visible
		
		// test filling in the data
		//Player testPlayer = new HumanPlayer("Frank", "Green", 0,0);
		//testPlayer.updateHand(new Card("Bathroom", "Room"));
		//testPlayer.updateSeen(new Card("Bart", "Person"));
		//panel.updatePanels(testPlayer);
	}
}
