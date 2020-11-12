package clueGame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
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
	private Player player;
	private ArrayList<Player> players;
	
	public GameCardPanel(Player player, ArrayList<Player> players) { // Constructor (ArrayList object is only there for now so that tests will work)
		this.player = player;
		this.players = players;
		setSize(400, 700);
		lookAtCards(); // Cards loaded and sorted before panel is made
		setLayout(new GridLayout(1,0));
		JPanel panel = createCardPanel();
		panel.setBorder(new TitledBorder(new EtchedBorder(), "Known Cards"));
		add(panel);
	}
	
	private JPanel createCardPanel() { // Separates the panel into 3 rows corresponding to card type and then calls functions to add each row's panel
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(3,0));
		panel.add(createPeoplePanel());
		panel.add(createRoomsPanel());
		panel.add(createWeaponsPanel());
		return panel;
	}
	
	private JPanel createPeoplePanel() { // Panel showing info on person cards
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(sizePeoplePanel,1)); // Each panel corresponding to a type of card creates rows equal to the number of cards displayed by it
		panel.setBorder(new TitledBorder(new EtchedBorder(), "People"));
		JLabel handLabel = new JLabel("In Hand:");
		JLabel seenLabel = new JLabel("Seen:");
		panel.add(handLabel); // Each panel has a hand section and a seen section, separated by labels
		for (JTextField person : handPeople) { // Text fields from the array they are stored in via lookAtCards are added to their respective sections of their respective panels
			panel.add(person);
		}
		if (handPeople.size() == 0) { // If there are no cards to be displayed in this section, display "None"
			JTextField none = new JTextField("None	");
			none.setEditable(false);
			panel.add(none);
		}
		panel.add(seenLabel);
		for (JTextField person : seenPeople) {
			panel.add(person);
		}
		if (seenPeople.size() == 0) {
			JTextField none = new JTextField("None	");
			none.setEditable(false);
			panel.add(none);
		}
		return panel;
	}
	
	private JPanel createRoomsPanel() { // Panel showing info on room cards
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(sizeRoomsPanel,1));
		panel.setBorder(new TitledBorder(new EtchedBorder(), "Rooms"));
		JLabel handLabel = new JLabel("In Hand:");
		JLabel seenLabel = new JLabel("Seen:");
		panel.add(handLabel);
		for (JTextField room : handRooms) {
			panel.add(room);
		}
		if (handRooms.size() == 0) {
			JTextField none = new JTextField("None	");
			none.setEditable(false);
			panel.add(none);
		}
		panel.add(seenLabel);
		for (JTextField room : seenRooms) {
			panel.add(room);
		}
		if (seenRooms.size() == 0) {
			JTextField none = new JTextField("None	");
			none.setEditable(false);
			panel.add(none);
		}
		return panel;
	}

	private JPanel createWeaponsPanel() { // Panel showing info on weapon cards
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(sizeWeaponsPanel,1));
		panel.setBorder(new TitledBorder(new EtchedBorder(), "People"));
		JLabel handLabel = new JLabel("In Hand:");
		JLabel seenLabel = new JLabel("Seen:");
		panel.add(handLabel);
		for (JTextField weapon : handWeapons) {
			panel.add(weapon);
		}
		if (handWeapons.size() == 0) {
			JTextField none = new JTextField("None	");
			none.setEditable(false);
			panel.add(none);
		}
		panel.add(seenLabel);
		for (JTextField weapon : seenWeapons) {
			panel.add(weapon);
		}
		if (seenWeapons.size() == 0) {
			JTextField none = new JTextField("None	");
			none.setEditable(false);
			panel.add(none);
		}
		return panel;
	}
	
	public void lookAtCards() { // Looks at the player's hand, the player's seen cards, and all other players' hands to determine which and how many cards should be in each panel
		int numPeopleHand = 0;
		int numRoomsHand = 0;
		int numWeaponsHand = 0;
		int numPeopleSeen = 0;
		int numRoomsSeen = 0;
		int numWeaponsSeen = 0;
		sizePeoplePanel = 2; // Initialized to 2 to account for the 2 rows taken up by the hand and seen labels
		sizeRoomsPanel = 2;
		sizeWeaponsPanel = 2;
		JTextField text;
		handPeople = new ArrayList<JTextField>();
		seenPeople = new ArrayList<JTextField>();
		handRooms = new ArrayList<JTextField>();
		seenRooms = new ArrayList<JTextField>();
		handWeapons = new ArrayList<JTextField>();
		seenWeapons = new ArrayList<JTextField>();
		
		for (Card card : player.getHand()) { // Count how many of each type of card are in the player's hand create a text field with the card's name, adding it to its respective array of text fields
			text = new JTextField(card.getCardName());
			text.setEditable(false);
			text.setBackground(player.getColor()); // Cards in a player's hand are set to that player's color
			if (card.getType() == CardType.PERSON) {
				numPeopleHand++;
				handPeople.add(text);
			}
			if (card.getType() == CardType.ROOM) {
				numRoomsHand++;
				handRooms.add(text);
			}
			if (card.getType() == CardType.WEAPON) {
				numWeaponsHand++;
				handWeapons.add(text);
			}
		}
		
		for (Card card: player.getCardsSeen()) { // Similar procedure to previous loop, but only looks at seen cards not already in the player's hand
			if (!player.getHand().contains(card)) {
				Color ownerColor = Color.GRAY;
				for (Player player : players) { // Set the card's background color to match its owner's
					if (player.getHand().contains(card)) {
						ownerColor = player.getColor();
					}
				}
				text = new JTextField(card.getCardName());
				text.setEditable(false);
				text.setBackground(ownerColor);
				if (card.getType() == CardType.PERSON) {
					numPeopleSeen++;
					seenPeople.add(text);
				}
				if (card.getType() == CardType.ROOM) {
					numRoomsSeen++;
					seenRooms.add(text);
				}
				if (card.getType() == CardType.WEAPON) {
					numWeaponsSeen++;
					seenWeapons.add(text);
				}
			}
		}
		
		int numPeople[] = {numPeopleHand, numPeopleSeen}; // Combine cards in hand and cards seen to prepare to calculate the number of rows in that type's panel
		int numRooms[] = {numRoomsHand, numRoomsSeen};
		int numWeapons[] = {numWeaponsHand, numWeaponsSeen};
		
		for (int numCards : numPeople) { // Calculate number of rows in each panel
			if (numCards == 0) { // Adds a row for "None" if needed
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
	
	public static void main(String[] args) { // Test the card panel using some arbitrary players with cards in their hand and cards seen
		Card jordan = new Card("Jordan", CardType.PERSON);
		Card kathy = new Card("Kathy", CardType.PERSON);
		Card taser = new Card("Taser", CardType.WEAPON);
		Card gun = new Card("Gun", CardType.WEAPON);
		Card office = new Card("Office", CardType.ROOM);
		Card kitchen = new Card("Kitchen", CardType.ROOM);
		Card bat = new Card("Bat", CardType.WEAPON);
		Card philip = new Card("Philip", CardType.PERSON);
		Card knife = new Card("Knife", CardType.WEAPON);
		
		Player dummy = new Human (Color.CYAN);
		Player cpu1 = new Computer(Color.RED);
		Player cpu2 = new Computer(Color.GREEN);
		
		dummy.updateHand(jordan);
		dummy.updateHand(kathy);
		dummy.updateHand(office);
		dummy.updateSeen(taser);
		dummy.updateSeen(gun);
		dummy.updateSeen(kitchen);
		dummy.updateSeen(knife);
		dummy.updateSeen(bat);
		
		cpu1.updateHand(taser);
		cpu1.updateHand(knife);
		cpu1.updateHand(philip);

		
		cpu2.updateHand(gun);
		cpu2.updateHand(kitchen);
		cpu2.updateHand(bat);
		
		ArrayList<Player> players = new ArrayList<Player>();
		players.add(dummy);
		players.add(cpu1);
		players.add(cpu2);
		
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GameCardPanel cardDisplay = new GameCardPanel(dummy, players);
		frame.add(cardDisplay, BorderLayout.CENTER);
		frame.setSize(180, 700);
		frame.setVisible(true);
		
		
	}

}
