package clueGame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;	

public class GameCardPanel extends JPanel {
	
	private ArrayList<JTextField> handPeople;
	private ArrayList<JTextField> seenPeople;
	private ArrayList<JTextField> handRooms;
	private ArrayList<JTextField> seenRooms;
	private ArrayList<JTextField> handWeapons;
	private ArrayList<JTextField> seenWeapons;
	private int sizePeoplePanel;
	private int sizeRoomsPanel;
	private int sizeWeaponsPanel;
	
	public GameCardPanel() {
		setLayout(new GridLayout(1,0));
		JPanel panel = createCardPanel();
		panel.setBorder(new TitledBorder(new EtchedBorder(), "Known Cards"));
		add(panel);
	}
	
	private JPanel createCardPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(3,0));
		panel.add(createPeoplePanel());
		panel.add(createRoomsPanel());
		panel.add(createWeaponsPanel());
		return panel;
	}
	
	private JPanel createPeoplePanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(sizePeoplePanel,0));
		panel.setBorder(new TitledBorder(new EtchedBorder(), "People"));
		JLabel handLabel = new JLabel("In Hand:");
		JLabel seenLabel = new JLabel("Seen:");
		panel.add(handLabel);
		for (JTextField person : handPeople) {
			panel.add(person);
		}
		panel.add(seenLabel);
		for (JTextField person : seenPeople) {
			panel.add(person);
		}
		return panel;
	}
	
	private JPanel createRoomsPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(sizeRoomsPanel,0));
		panel.setBorder(new TitledBorder(new EtchedBorder(), "Rooms"));
		JLabel handLabel = new JLabel("In Hand:");
		JLabel seenLabel = new JLabel("Seen:");
		panel.add(handLabel);
		for (JTextField room : handRooms) {
			panel.add(room);
		}
		panel.add(seenLabel);
		for (JTextField room : seenRooms) {
			panel.add(room);
		}
		return panel;
	}
	
	private JPanel createWeaponsPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(sizeWeaponsPanel,0));
		panel.setBorder(new TitledBorder(new EtchedBorder(), "People"));
		JLabel handLabel = new JLabel("In Hand:");
		JLabel seenLabel = new JLabel("Seen:");
		panel.add(handLabel);
		for (JTextField weapon : handWeapons) {
			panel.add(weapon);
		}
		panel.add(seenLabel);
		for (JTextField weapon : seenWeapons) {
			panel.add(weapon);
		}
		return panel;
	}
	
	public void countCards(Player player) {
		int numPeopleHand = 0;
		int numRoomsHand = 0;
		int numWeaponsHand = 0;
		int numPeopleSeen = 0;
		int numRoomsSeen = 0;
		int numWeaponsSeen = 0;
		sizePeoplePanel = 2;
		sizeRoomsPanel = 2;
		sizeWeaponsPanel = 2;
		
		for (Card card : player.getHand()) {
			if (card.getType() == CardType.PERSON) {
				numPeopleHand++;
			}
			if (card.getType() == CardType.ROOM) {
				numRoomsHand++;
			}
			if (card.getType() == CardType.WEAPON) {
				numWeaponsHand++;
			}
		}
		
		for (Card card: player.getCardsSeen()) {
			if (card.getType() == CardType.PERSON) {
				numPeopleSeen++;
			}
			if (card.getType() == CardType.ROOM) {
				numRoomsSeen++;
			}
			if (card.getType() == CardType.WEAPON) {
				numWeaponsSeen++;
			}
		}
		
		int numPeople[] = {numPeopleHand, numPeopleSeen};
		int numRooms[] = {numPeopleHand, numPeopleSeen};
		int numWeapons[] = {numPeopleHand, numPeopleSeen};
		
		for (int numCards : numPeople) {
			if (numCards == 0) {
				sizePeoplePanel++;
			}
			else {
				sizePeoplePanel += numCards;
			}
		}
		
		for (int numCards : numRooms) {
			if (numCards == 0) {
				sizeRoomsPanel++;
			}
			else {
				sizeRoomsPanel += numCards;
			}
		}
		
		for (int numCards : numWeapons) {
			if (numCards == 0) {
				sizeWeaponsPanel++;
			}
			else {
				sizeWeaponsPanel += numCards;
			}
		}
	}
	
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GameCardPanel cardDisplay = new GameCardPanel();
		frame.add(cardDisplay, BorderLayout.CENTER);
		frame.setSize(180, 700);
		frame.setVisible(true);
	}

}
