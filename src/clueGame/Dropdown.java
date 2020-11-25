package clueGame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class Dropdown extends JPanel{
	String room;
	String person;
	String weapon;
	
	public Dropdown(boolean accuse) {
		add(createLeft(accuse), BorderLayout.WEST);
		add(createRight(accuse), BorderLayout.EAST);
		setSize(300,150);
		setVisible(true);
		
	}


	private JPanel createLeft(boolean accuse) {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(3,0));
		JLabel roomLabel; 
		if(accuse) {
			roomLabel = new JLabel("Room");
		}else {
			roomLabel = new JLabel("Current Room");
		}
		panel.add(roomLabel);
		JLabel personLabel = new JLabel("Person");
		JLabel weaponLabel = new JLabel("Weapon");
		panel.add(personLabel);
		panel.add(weaponLabel);
		return panel;
	}

	private JPanel createRight(boolean accuse) {
		JPanel panel = new JPanel();
		
		panel.setLayout(new GridLayout(3,0));
		if(!accuse) {
			JTextField roomField = new JTextField(10);
			roomField.setEditable(false);
			roomField.setText(Board.getInstance().getPlayers().get(Board.getInstance().turn).getRoom().getName()); //Get room name and display
			room = Board.getInstance().getPlayers().get(Board.getInstance().turn).getRoom().getName();
			panel.add(roomField);
		}else {
			panel.add(createDropdown(CardType.ROOM));
		}
		panel.add(createDropdown(CardType.PERSON));
		panel.add(createDropdown(CardType.WEAPON));
		return panel;
	}


	private JComboBox<String> createDropdown(CardType type){
		
		ArrayList<Card> tempDeck = Board.getInstance().getDeck();
		int count = 0;
		for(Card card : tempDeck) {
			if(card.getType() == type) {
				count++;
			}
		}
		String[] dropDownCards = new String[count];
		count = 0;
		for(Card card : tempDeck) {
			if(card.getType() == type) {
				dropDownCards[count] = card.getCardName();
				count++;
			}
		}
		switch(type) {
        case ROOM:
        	room = dropDownCards[0];
        	break;
        case PERSON:
        	person = dropDownCards[0];
        	break;
        case WEAPON:
        	weapon = dropDownCards[0];
        	break;
        }
        Board.getInstance().setSuggestion(room, person, weapon);
        
		JComboBox<String> newDropdown = new JComboBox<String>(dropDownCards);
		newDropdown.addActionListener(new ActionListener() {
	         @Override
	         public void actionPerformed(ActionEvent e) {
	            switch(type) {
	            case ROOM:
	            	room = newDropdown.getSelectedItem().toString();
	            	break;
	            case PERSON:
	            	person = newDropdown.getSelectedItem().toString();
	            	break;
	            case WEAPON:
	            	weapon = newDropdown.getSelectedItem().toString();
	            	break;
	            }
	            Board.getInstance().setSuggestion(room, person, weapon);
	         }
	      });
		return newDropdown;
	}
}
