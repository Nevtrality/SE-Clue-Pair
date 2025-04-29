package clueGame;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PromptDialog extends JDialog{
	Board board;
	JButton submit;
	JButton cancel;
	Solution solution;
	JComboBox<String> rDropdown;
	JComboBox<String> pDropdown;
	JComboBox<String> wDropdown;
	Card room;
	
	public PromptDialog(Room currentRoom, String type) {
		board = Board.getInstance();
		setModal(true);
		if(type.equals("Suggestion")) {
			setTitle("Make a Suggestion");
		} else {
			setTitle("Make an Accusation");
		}
		room = board.getCard(currentRoom.getName());
		
		setLayout(new GridLayout(4,0));
		// first row of dialog box (room)
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0,2));
			JLabel label = new JLabel("Current room");
			panel.add(label);
			if(type.equals("Suggestion")) {
				label = new JLabel(currentRoom.getName()); // current room's name
				panel.add(label);
			} else {
				rDropdown = new JComboBox<String>();
				setupDropdown(rDropdown,"Room");
				panel.add(rDropdown);
			}
			
		add(panel);
		// second row of dialog box (person)
		panel = new JPanel();
		panel.setLayout(new GridLayout(0,2));
			label = new JLabel("Person");
			panel.add(label);
			pDropdown = new JComboBox<String>();
			setupDropdown(pDropdown,"Person");
			panel.add(pDropdown);
		add(panel);
		// third row of dialog box (weapon)
		panel = new JPanel();
		panel.setLayout(new GridLayout(0,2));
			label = new JLabel("Weapon");
			panel.add(label);
			wDropdown = new JComboBox<String>();
			setupDropdown(wDropdown,"Weapon");
			panel.add(wDropdown);
		add(panel);
		// fourth row of dialog box (buttons)
		panel = new JPanel();
		panel.setLayout(new GridLayout(0,2));
			submit = new JButton("Submit");
			
			// submit action handler
			submit.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent click) {
					handleButton("","");
				}

			});
			
			panel.add(submit);
			cancel = new JButton("Cancel");
			
			// cancel action handler
			cancel.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent click) {
					handleButton("close",type);
				}
				
			});
			
			panel.add(cancel);
		add(panel);
		
		// dimensions and visibility
		setSize(260,200);
		setVisible(true);
	}
	
	private void setupDropdown(JComboBox<String> dropdown, String type) {
		if(type.equals("Person")) {
			for(Card card : board.getDeck()) {
				if(card.getCardType()==CardType.PERSON) {
					dropdown.addItem(card.getCardName());
				}
			}
		}else if (type.equals("Weapon")) {
			for(Card card : board.getDeck()) {
				if(card.getCardType()==CardType.WEAPON) {
					dropdown.addItem(card.getCardName());
				}
			}
		}else if(type.equals("Room")) {
			for(Card card : board.getDeck()) {
				if(card.getCardType()==CardType.ROOM) {
					dropdown.addItem(card.getCardName());
				}
			}
		}
	}
	
	public void handleButton(String type, String type2) {
		if(type.equals("close")) { // close when cancelling
			solution = new Solution(null,null,null);
			dispose();
		} else {
			// update solution list to hold selected items in dropdown menu
			if(type2.equals("Suggestion")) {
				solution = new Solution(room, board.getCard(pDropdown.getSelectedItem().toString()), board.getCard(wDropdown.getSelectedItem().toString()));
			}else {
				solution = new Solution(board.getCard(rDropdown.getSelectedItem().toString()), board.getCard(pDropdown.getSelectedItem().toString()), board.getCard(wDropdown.getSelectedItem().toString()));
			}
			dispose(); // close dialog box
		}
	}
	
	public Solution getDialogSolution() {
		return solution;
	}
	
	public static void main(String args[]) {
		Room room = new Room();
		room.setName("bathroom");
		PromptDialog dialog = new PromptDialog(room, "give up");
	}
}
