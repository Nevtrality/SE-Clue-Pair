package clueGame;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class GameCardPanel extends JPanel{
	JPanel playerPanel;
	JPanel roomPanel;
	JPanel weaponPanel;
	
	List<Player> playerList;
	
	public GameCardPanel() {
		// set layout dimensions
		setLayout(new GridLayout(3,0));
		this.setSize(200, 750);
		// add border
		setBorder(new TitledBorder(new EtchedBorder(), "Known Cards"));
		// set layouts
		// update Players
				playerPanel = new JPanel();
				playerPanel.setLayout(new GridLayout(2,0));
					// add border
					playerPanel.setBorder(new TitledBorder(new EtchedBorder(), "People"));
					// update hand panel to have field for the Player's "people" card
					playerPanel.add(updateHandPanel(new ArrayList<Card>()));
					// update seen panel to have field for the seen "people"
					playerPanel.add(updateSeenPanel(new ArrayList<Card>()));
				
				add(playerPanel);
					
				// update rooms
				roomPanel = new JPanel();
				roomPanel.setLayout(new GridLayout(2,0));
					// add border
					roomPanel.setBorder(new TitledBorder(new EtchedBorder(), "Rooms"));
					// update hand panel to have field for the Player's "room" card
					roomPanel.add(updateHandPanel(new ArrayList<Card>()));
					// update seen panel to have field for the seen "rooms"
					roomPanel.add(updateSeenPanel(new ArrayList<Card>()));
				
				add(roomPanel);
				
				// update weapons
				weaponPanel = new JPanel();
				weaponPanel.setLayout(new GridLayout(2,0));
					// add border
					weaponPanel.setBorder(new TitledBorder(new EtchedBorder(), "Weapons"));
					// update hand panel to have field for the Player's "weapon" card
					weaponPanel.add(updateHandPanel(new ArrayList<Card>()));
					// update seen panel to have field for the seen "weapons"
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
		playerPanel.removeAll();
		playerPanel.setLayout(new GridLayout(2,0));
			// add border
			playerPanel.setBorder(new TitledBorder(new EtchedBorder(), "People"));
			// update hand panel to show value of the player's "people" card
			playerPanel.add(updateHandPanel(handPerson));
			// update seen panel to have value of the player's "people" card as a seen value
			playerPanel.add(updateSeenPanel(seenPerson));
		
		playerPanel.revalidate();
			
		// update rooms
		roomPanel.removeAll();
		roomPanel.setLayout(new GridLayout(2,0));
			// add border
			roomPanel.setBorder(new TitledBorder(new EtchedBorder(), "Rooms"));
			// update hand panel to show value of the player's "room" card
			roomPanel.add(updateHandPanel(handRoom));
			// update seen panel to have value of the player's "room" card as a seen value
			roomPanel.add(updateSeenPanel(seenRoom));
		
		roomPanel.revalidate();
		
		// update weapons
		weaponPanel.removeAll();
		weaponPanel.setLayout(new GridLayout(2,0));
			// add border
			weaponPanel.setBorder(new TitledBorder(new EtchedBorder(), "Weapons"));
			// update hand panel to show value of the player's "weapon" card
			weaponPanel.add(updateHandPanel(handWeapon));
			// update seen panel to have value of the player's "weapon" card as a seen value
			weaponPanel.add(updateSeenPanel(seenWeapon));
		
		weaponPanel.revalidate();
	}
	
	JPanel updateHandPanel(List<Card> hand) {
		JPanel handPanel = new JPanel();
		// formatting layout
		if(hand.size()<1) {
			handPanel.setLayout(new GridLayout(2+hand.size(),0));
		} else {
			handPanel.setLayout(new GridLayout(1+hand.size(),0));
		}
			// create label
			createJLabel(handPanel, "In Hand:");
			JTextField handCards = new JTextField();
			handCards.setEditable(false);
			// if the list is empty, return a single text field with "None"
			if(hand.size() > 0) {
				// loop through list of cards and add to panel
				for (Card card: hand) {
					handCards = new JTextField(card.getCardName());
					handCards.setEditable(false);
					Player playerWithCard = getPlayerHolding(card);
					Color color = playerWithCard.getColor();
					handCards.setBackground(color);
					handPanel.add(handCards);
				}
			} else {
				//if no new seen cards, set text field to say none
				handCards = new JTextField();
				handCards.setText("None");
				handCards.setEditable(false);
				handPanel.add(handCards);
			}
		return handPanel;
	}
	
	private JPanel updateSeenPanel(List<Card> seen) {
		JPanel seenPanel = new JPanel();
		// formatting layout
		if(seen.size()<1) {
			seenPanel.setLayout(new GridLayout(2+seen.size(),0));
		} else {
			seenPanel.setLayout(new GridLayout(1+seen.size(),0));
		}
			// create label
			createJLabel(seenPanel, "Seen:");
			JTextField seenCards;
			// if list is empty, return single text field with "None"
			if(seen.size() == 0) {
				seenCards = new JTextField();
				seenCards.setText("None");
				seenCards.setEditable(false);
				seenPanel.add(seenCards);
			} else {
				// loop through list of cards and add to panel
				for (Card card: seen) {
					seenCards = new JTextField(card.getCardName());
					seenCards.setEditable(false);
					Player playerWithCard = getPlayerHolding(card);
					Color color = playerWithCard.getColor();
					seenCards.setBackground(color);
					seenPanel.add(seenCards);
				}
			}
		return seenPanel;
	}
	
	private Player getPlayerHolding(Card card) {
		for(Player player : playerList) {
			for(Card playerCard : player.getHand()) {
				if(playerCard.equals(card)) {
					return player;
				}
			}
		}
		return null;
	}
	
	public void tempSetPlayerList(List<Player> players) {
		playerList = players;
	}
	
	public static void main(String[] args) {
		GameCardPanel panel = new GameCardPanel();  // create the panel
		JFrame frame = new JFrame();  // create the frame 
		frame.setContentPane(panel); // put the panel in the frame
		frame.setSize(180, 570);  // size the frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
		frame.setVisible(true); // make it visible
		
		// test filling in the data
			// make a list of players for testing (to be changed when incorporating board players)
		List<Player> players = new ArrayList<Player>();
		players.add(new ComputerPlayer("Colonel Mustard", "ORANGE", 0,0));
		players.add(new ComputerPlayer("Mrs. White", "WHITE", 0,0));
		players.add(new ComputerPlayer("Miss Scarlet", "RED", 0,0));
		players.add(new ComputerPlayer("Mrs Peacock", "BLUE", 0,0));
		players.add(new ComputerPlayer("Mr Green", "GREEN", 0,0));
		players.add(new ComputerPlayer("Professor Plum", "MAGENTA", 0,0));
		// give players in list 1 card each
		players.get(0).updateHand(new Card("Greenhouse", "Room"));
		players.get(1).updateHand(new Card("Kitchen", "Room"));
		players.get(2).updateHand(new Card("Mrs. White", "Person"));
		players.get(3).updateHand(new Card("Mr Green", "Person"));
		players.get(4).updateHand(new Card("Knife", "Weapon"));
		players.get(5).updateHand(new Card("Rope", "Weapon"));
		
		// put cards in testPlayer's seen list
		Player testPlayer = new HumanPlayer("Frank", "Green", 0,0);
		testPlayer.updateHand(new Card("Bathroom", "Room"));
		testPlayer.updateSeen(new Card("Greenhouse", "Room"));
		testPlayer.updateSeen(new Card("Kitchen", "Room"));
		testPlayer.updateSeen(new Card("Mrs. White", "Person"));
		testPlayer.updateSeen(new Card("Mr Green", "Person"));
		testPlayer.updateSeen(new Card("Knife", "Weapon"));
		testPlayer.updateSeen(new Card("Rope", "Weapon"));
		
		panel.tempSetPlayerList(players);
		panel.updatePanels(testPlayer);
	}
}
