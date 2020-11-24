package clueGame;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import javax.swing.JOptionPane;
import javax.swing.JPanel;



public class Board extends JPanel{
	private static int rows;
	private static int cols;
	private BoardCell[][] grid;
	private Set<BoardCell> targets;
	private Set<BoardCell> visited;
	protected static HashMap<Character, Room> rooms;
	private static String layoutConfigFile;
	private static String setupConfigFile;
	protected static ArrayList<Card> deck;
	private static ArrayList<Player> players;
	private static ArrayList<Card> dealer;
	private Solution theAnswer;
	protected int turn = 0;
	private boolean test306;
	/*
	 * variable and methods used for singleton pattern
	 */
	private static Board theInstance = new Board();
	// constructor is private to ensure only one can be created
	private Board() {
		super() ;
		addMouseListener(new MouseWatch());


	}
	// this method returns the only Board
	public static Board getInstance() {
		return theInstance;
	}
	/*
	 * initialize the board (since we are using singleton pattern)
	 */
	public void initialize()
	{
		loadConfigFiles();
		//create adjacency list for each cell in the grid
		for(int i = 0; i < rows; i++) {
			for(int j = 0; j < cols; j++) {
				if(i - 1 >= 0) {
					if(grid[i - 1][j].getRoomChar() == 'W') {
						grid[i][j].addToAdjList(this.getCell(i - 1,j));
					}
				}
				if(j - 1 >= 0) {
					if(grid[i][j - 1].getRoomChar() == 'W') {
						grid[i][j].addToAdjList(this.getCell(i,j - 1));
					}
				}
				if(i + 1 < rows) {
					if(grid[i + 1][j].getRoomChar() == 'W') {
						grid[i][j].addToAdjList(this.getCell(i + 1,j));
					}
				}
				if(j + 1 < cols) {
					if(grid[i][j + 1].getRoomChar() == 'W') {
						grid[i][j].addToAdjList(this.getCell(i,j + 1));
					}
				}
				if(grid[i][j].isDoorway()) {
					grid[i][j].addToAdjList(doorCalc(this.getCell(i, j)));
					doorCalc(this.getCell(i, j)).addToAdjList(grid[i][j]);
				}
				if(grid[i][j].isSecretPassage()) {
					rooms.get(grid[i][j].getRoomChar()).getCenterCell().addToAdjList(secretPassageCalc(grid[i][j]));
				}
			}
		}
		deal();
	}
	private BoardCell secretPassageCalc(BoardCell cell) {
		return rooms.get(cell.getSecretPassage()).getCenterCell();
	}

	private BoardCell doorCalc(BoardCell cell) {
		DoorDirection direction = cell.getDoorDirection();
		int row = cell.getRow();
		int col = cell.getCol();
		if(direction == DoorDirection.UP) {
			row--;
		}if(direction == DoorDirection.DOWN) {
			row++;
		}if(direction == DoorDirection.LEFT) {
			col--;
		}if(direction == DoorDirection.RIGHT) {
			col++;
		}
		return(rooms.get(this.getCell(row, col).getRoomChar()).getCenterCell());
	}

	public void setConfigFiles(String csv, String txt) {
		layoutConfigFile = csv;
		setupConfigFile = txt;
	}

	public void loadConfigFiles() {
		try {
			loadSetupConfig();
		} catch (FileNotFoundException e) {
			System.out.println("Error. Can't find file: " + setupConfigFile);
		}catch (BadConfigFormatException f) {
			System.out.println("Error. BadConfigFormatException in: " + setupConfigFile);
		}
		try {
			loadLayoutConfig();
		} catch (FileNotFoundException e) {
			System.out.println("Error. Can't find file: " + layoutConfigFile);
		} catch (BadConfigFormatException f) {
			System.out.println("Error. BadConfigFormatException in: " + layoutConfigFile);
		}
	}

	public void loadSetupConfig() throws FileNotFoundException, BadConfigFormatException {
		rooms = new HashMap<Character, Room>();
		deck = new ArrayList<>();
		players = new ArrayList<>();
		Scanner scan;
		scan = new Scanner(new File(setupConfigFile));
		char tempChar;
		String tempLabel;
		while(scan.hasNextLine()) {
			String currLine = scan.nextLine();
			if(!currLine.contains("//")) {
				if(currLine.substring(0, currLine.indexOf(",")).contains("Room")){
					tempChar = currLine.charAt(currLine.length() - 1);
					tempLabel = currLine.substring(6, currLine.lastIndexOf(","));
					rooms.put(tempChar, new Room(tempLabel,tempChar,true));
					addToDeck(tempLabel, CardType.ROOM, tempChar);
				}else if(currLine.contains("Space")){
					tempChar = currLine.charAt(currLine.length() - 1);
					tempLabel = currLine.substring(7, currLine.lastIndexOf(","));
					rooms.put(tempChar, new Room(tempLabel,tempChar,false));
					test306 = true;
				}else if(currLine.contains("Weapon")){
					test306 = false;
					tempLabel = currLine.substring(8);
					addToDeck(tempLabel, CardType.WEAPON);
				}else if(currLine.contains("Player")) {
					Player tempPlayer;
					currLine = currLine.substring(8);
					if(currLine.contains("Human")) {
						tempPlayer = new Human();
						currLine = currLine.substring(7);
					}else if(currLine.contains("Computer")){
						tempPlayer = new Computer();
						currLine = currLine.substring(10);
					}else {
						throw new BadConfigFormatException();
					}
					tempLabel = currLine.substring(0, currLine.indexOf(","));
					currLine = currLine.substring(tempLabel.length() + 2, currLine.length());
					String colorString = currLine.substring(0, currLine.indexOf(","));
					currLine = currLine.substring(currLine.indexOf(",") + 2);
					tempPlayer.setColor(calcColor(colorString));
					tempPlayer.setName(tempLabel);
					tempPlayer.setLocation(Integer.parseInt(currLine.substring(0,currLine.indexOf(","))),Integer.parseInt(currLine.substring(currLine.indexOf(",") + 2)));
					addToDeck(tempLabel, CardType.PERSON);
					players.add(tempPlayer);

				}else {
					throw new BadConfigFormatException();
				}
			}
		}

	}
	public void loadLayoutConfig() throws FileNotFoundException, BadConfigFormatException {
		ArrayList<String> lines = new ArrayList<String>();
		Scanner scan;
		int numLines = 0;
		int numCols = 0;
		scan = new Scanner(new File(layoutConfigFile));
		while(scan.hasNextLine()) {
			scan.nextLine();
			numLines++;
		}
		scan = new Scanner(new File(layoutConfigFile));
		for(int i = 0; i < numLines - 1; i++) {
			scan.nextLine();
		}
		scan.useDelimiter(",");
		while(scan.hasNext()) {
			scan.next();
			numCols++;
		}
		rows = numLines;
		cols = numCols;
		grid = new BoardCell[rows][cols]; //initialize grid to the provided dimensions
		//fill grid with empty cells
		for(int i = 0; i < rows; i++) {
			for(int j = 0; j < cols; j++) {
				grid[i][j] = new BoardCell(i,j);
			}
		}
		scan = new Scanner(new File(layoutConfigFile));
		scan.useDelimiter("\n");
		while(scan.hasNext()) {
			lines.add(scan.next());
		}
		int column = 0;
		for(String i: lines) {
			readLine(i,column);
			column++;
		}

	}

	private void readLine(String currLine, int i) throws BadConfigFormatException {

		Scanner scan = new Scanner(currLine);
		scan.useDelimiter(",");
		int columnCount = 0;
		while(scan.hasNext()) {
			scan.next();
			columnCount++;
		}
		scan.close();
		if(columnCount != cols) {
			throw new BadConfigFormatException();
		}
		scan = new Scanner(currLine);
		scan.useDelimiter(",");
		for(int j = 0; j < cols; j++) {
			String currVal = scan.next();
			char currChar = currVal.charAt(0);
			if(!rooms.containsKey(currChar)) {
				scan.close();
				throw new BadConfigFormatException();
			}

			BoardCell tempCell = grid[i][j];
			tempCell.setRoomChar(currChar); //sets the character in the created grid
			tempCell.setIsRoom(rooms.get(currChar).getIsRoom()); //sets whether each cell is a room

			if(currVal.length() > 1) {
				switch(currVal.charAt(1)) {
				case '*':
					tempCell.setIsRoomCenter(true);
					rooms.get(currVal.charAt(0)).setCenterCell(tempCell);
					break;
				case '#':
					tempCell.setIsRoomLabel(true);
					rooms.get(currVal.charAt(0)).setLabelCell(tempCell);
					break;
				case '^':
					tempCell.setisDoorway(true);
					tempCell.setDoorDirection(DoorDirection.UP);
					break;
				case '>':
					tempCell.setisDoorway(true);
					tempCell.setDoorDirection(DoorDirection.RIGHT);
					break;
				case '<':
					tempCell.setisDoorway(true);
					tempCell.setDoorDirection(DoorDirection.LEFT);
					break;
				case 'v':
					tempCell.setisDoorway(true);
					tempCell.setDoorDirection(DoorDirection.DOWN);
					break;
				default:
					if(currVal.charAt(1) != '\n' && currVal.charAt(1) != '\r') {
						tempCell.setIsSecretPassage(true);
						tempCell.setSecretPassage(currVal.charAt(1));
					}
				}
			}
		}
		scan.close();
	}


	private void findAllTargets(BoardCell thisCell, int numSteps) {
		for (BoardCell adjCell : thisCell.getAdjList()) { //loop through each adj cell
			if (!visited.contains(adjCell) && (!adjCell.getOccupied() || adjCell.getIsRoom())) {//as long as not visited or occupied
				visited.add(adjCell); //add cell to visited
				if (adjCell.getIsRoom()) { //if in a room
					targets.add(adjCell); //add cell to targets
					adjCell.highlight = true;
				}else if(numSteps == 1) {//if no steps remaining
					boolean blocked = false;
					for(Player player : players) { //check to see if cell is occupied
						if(player.getLocationCell().getCol() == adjCell.getCol() && player.getLocationCell().getRow() == adjCell.getRow()) {
							blocked = true;
						}
					}
					if(!blocked) { //if not occupied
						targets.add(adjCell); //add cell to targets
						adjCell.highlight = true;
					}
				}
				else {
					findAllTargets(adjCell, numSteps-1); //else call recursive function
				}
				visited.remove(adjCell);
			}
		}
		repaint();
	}

	//calculates legal targets for a move from startCell of length pathlength 
	public void calcTargets(BoardCell startCell, int pathlength) {
		//first initialize visited list and targets list, add the cell the character is currently on to the visited list
		visited = new HashSet<BoardCell>();
		targets = new HashSet<BoardCell>();
		visited.add(startCell);
		findAllTargets(startCell, pathlength);
	}

	public void deal() { //deals cards not in solution to the players
		if(!test306) {
			generateSolution();
			Random rand = new Random();
			int playerCounter = 0;
			while(dealer.size() != 0) {
				Card currCard = dealer.get(rand.nextInt(dealer.size()));
				players.get(playerCounter).updateHand(currCard);
				dealer.remove(currCard);
				playerCounter++;
				if(playerCounter >= players.size()) {
					playerCounter = 0;
				}
			}
		}
	}

	public void generateSolution() { //copies the deck to be used for dealing, chose 3 random cards for the solution
		dealer = new ArrayList<Card>();
		for (Card card : deck) {
			dealer.add(card);
		}
		theAnswer = new Solution();
		Random rand = new Random();
		boolean needPerson = true;
		boolean needWeapon = true;
		boolean needRoom = true;
		Card tempCard;
		while(needPerson || needWeapon || needRoom) {
			tempCard = dealer.get(rand.nextInt(dealer.size()));
			if((tempCard.getType() == CardType.PERSON) && needPerson) {
				theAnswer.setPerson(tempCard);
				needPerson = false;
				dealer.remove(tempCard);
			}else if((tempCard.getType() == CardType.WEAPON) && needWeapon) {
				theAnswer.setWeapon(tempCard);
				needWeapon = false;
				dealer.remove(tempCard);
			}else if((tempCard.getType() == CardType.ROOM) && needRoom) {
				theAnswer.setRoom(tempCard);
				needRoom = false;
				dealer.remove(tempCard);
			}
		}
	}

	public Color calcColor(String input) throws BadConfigFormatException { //changes a color string to a color type
		switch(input) {
		case "Red":
			return Color.RED;
		case "Orange":
			return Color.ORANGE;
		case "Yellow":
			return Color.YELLOW;
		case "Green":
			return Color.GREEN;
		case "Blue":
			return Color.CYAN;
		case "Purple":
			return Color.MAGENTA;
		case "Magenta":
			return Color.MAGENTA;
		case "White":
			return Color.WHITE;
		default:
			throw new BadConfigFormatException();
		}
	}
	public void addToDeck(String name, CardType type) { //creates a new card and adds to deck.
		Card tempCard = new Card(name, type);
		//tempCard.setCardName(name);
		//tempCard.setType(type);
		deck.add(tempCard);
	}

	public void addToDeck(String name, CardType type, char roomChar) { //creates a room card and adds to deck.
		Card tempCard = new Card(name, type, roomChar);
		//tempCard.setCardName(name);
		//tempCard.setType(type);
		deck.add(tempCard);
	}

	public boolean checkAccusation(Card[] accusation) {
		boolean found = true;
		for(int i = 0; i < 3; i++) {
			if(!(accusation[i].equals(theAnswer.person) || accusation[i].equals(theAnswer.room) || accusation[i].equals(theAnswer.weapon))) {
				found = false;	
			}
		}
		return found;
	}

	public Card handleSuggestion(Player player, Solution suggestion) { //TODO
		for(Player person : players) { //Puts correct player in room
			if(person.getName().compareTo(suggestion.person.getCardName()) == 0) {
				BoardCell tempCell = getRoom(suggestion.room.getRoomChar()).getCenterCell();
				if(person.getInRoom()) {
					getRoom(person.getLocationCell()).leaveRoom(person);
				}
				person.setInRoom(true);
				getRoom(suggestion.room.getRoomChar()).enterRoom(person);
				person.setLocation(tempCell.getRow(), tempCell.getCol());
				break;
			}
		}
		GameControlPanel.setGuess(player.getName() + " suggested: " + suggestion.person.getCardName() + " with " + suggestion.weapon.getCardName() + " in " + suggestion.room.getCardName());		
		int playerIndex = players.indexOf(player) + 1;
		if (playerIndex >= players.size()) {
			playerIndex = 0;
		}
		while (playerIndex != players.indexOf(player)) {
			if(players.get(playerIndex).disproveSuggestion(suggestion) != null) {
				return players.get(playerIndex).disproveSuggestion(suggestion);
			}
			playerIndex++;
			if (playerIndex >= players.size()) {
				playerIndex = 0;
			}
		}
		return null;
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		int cellWidth = Board.getInstance().getHeight() / Board.getInstance().getNumRows();
		int cellHeight = Board.getInstance().getWidth() / Board.getInstance().getNumColumns();
		for(int i = 0; i < grid.length; i++) { //Draw Cells
			for(int j = 0; j < grid[i].length; j++) {
				grid[i][j].drawSelf( j * cellHeight, i * cellWidth, cellHeight, cellWidth, g);
			}
		}

		for(int i = 0; i < grid.length; i++) { //Draw Doorways
			for(int j = 0; j < grid[i].length; j++) {
				if(grid[i][j].isDoorway()) {
					grid[i][j].drawDoorway(j * cellHeight, i * cellWidth, cellHeight, cellWidth, g);
				}
			}
		}

		for(int i = 0; i < grid.length; i++) { //Draw Room Labels
			for(int j = 0; j < grid[i].length; j++) {
				if(grid[i][j].isLabel()) {
					grid[i][j].drawRoomName(j * cellHeight, i * cellWidth, g);
				}
			}
		}
		for(int i = 0; i < players.size(); i++) { //Draw Players
			int playerRoomCount = 0;
			if(players.get(i).getInRoom()) {
				for(Player roomPlayer : getRoom(players.get(i).getLocationCell()).getPlayers()) {
					if(roomPlayer.getName().compareTo(players.get(i).getName()) == 0) {
						break;
					}
					playerRoomCount++;
				}
			}
			players.get(i).drawSelf(cellHeight, cellWidth, g, playerRoomCount);
		}
	}


	public Set<BoardCell> getTargets() {
		//gets the targets last created by calcTargets()
		return targets;
	}

	public BoardCell getCell(int row, int col){
		//returns the cell from the board at row, col
		return grid[row][col];
	}

	public Room getRoom(BoardCell cell) {
		return rooms.get(cell.getRoomChar());
	}
	public Room getRoom(char letter) {
		return rooms.get(letter);
	}

	public int getNumRows() {
		return rows;
	}
	public int getNumColumns() {
		return cols;
	}
	public Set<BoardCell> getAdjList(int i, int j) {
		return grid[i][j].getAdjList();
	}
	public ArrayList<Player> getPlayers() {
		return players;
	}
	public String getFirstTurnName() { // Get name of the human to display initial message
		turn = 0;
		return players.get(turn).getName();
	}

	public void firstTurn(){ // Set up the first turn of the game
		Random rand = new Random();
		int roll = rand.nextInt(6) + 1;
		GameControlPanel.setTurn(players.get(turn), roll);
		players.get(turn).movedThisTurn = false;
		players.get(turn).finishedTurn = false;
		calcTargets(players.get(turn).getLocationCell(),roll);
	}

	public void nextTurn() { // Called when the next button is pressed, either sets up the next turn or displays an error
		Random rand = new Random();
		if(players.get(turn).finishedTurn) {
			int roll = rand.nextInt(6) + 1;
			GameControlPanel.setTurn(getNextPlayerTurn(),roll);
			calcTargets(players.get(turn).getLocationCell(),roll);
			players.get(turn).movedThisTurn = false;
			if (players.get(turn).getClass().getName() == "clueGame.Computer") { // Computer players move on their own
				Computer compPlayer = (Computer) players.get(turn);
				BoardCell selection = compPlayer.selectTargets();
				compPlayer.setLocation(selection.getRow(), selection.getCol());
				if(compPlayer.inRoom) {
					Solution newSuggestion = compPlayer.createSuggestion();
					players.get(turn).updateHand(handleSuggestion(compPlayer, newSuggestion));
				}
				for(BoardCell cell: targets) {
					cell.highlight = false;
				}
			}
		}else { // Cannot go to next turn until human player has moved
			Object[] options = {"OK"};
			JOptionPane.showOptionDialog(null, "Error: You must complete your turn before pressing NEXT", "ERROR", JOptionPane.PLAIN_MESSAGE, JOptionPane.ERROR_MESSAGE, null, options, options[0]);
		}
	}

	public Player getNextPlayerTurn() { // Update turn status
		turn++;
		if(turn >= players.size()) {
			turn = 0;
		}
		if(players.get(turn).getClass().getName() == "clueGame.Human") {
			players.get(turn).finishedTurn = false;
		}else {
			players.get(turn).finishedTurn = true;
		}
		return players.get(turn);
	}

	public ArrayList<Card> getDeck() {
		return deck;
	}
	public Solution getSolution() {
		return theAnswer;
	}
	public void setSolution(String person, String weapon, String room) {
		Card personCard = new Card(person, CardType.PERSON);
		Card weaponCard = new Card(weapon, CardType.WEAPON);
		Card roomCard = new Card(room, CardType.ROOM);
		theAnswer.setPerson(personCard);
		theAnswer.setWeapon(weaponCard);
		theAnswer.setRoom(roomCard);
	}
	public ArrayList<Card> getDealer() {
		return dealer;
	}
	
	public Player getCurrPlayer() {
		return players.get(turn);
	}
	public void setTestPlayers() {
		players = new ArrayList<Player>();
		Player humanPlayer = new Human(Color.CYAN);
		Player compPlayer1 = new Computer(Color.RED);
		Player compPlayer2 = new Computer(Color.GREEN);
		players.add(humanPlayer);
		players.add(compPlayer1);
		players.add(compPlayer2);
	}
	public int getTurn() {
		return turn;
	}
	private class MouseWatch implements MouseListener {
		//  Empty definitions for unused event methods.
		public void mousePressed (MouseEvent event) {}  
		public void mouseReleased (MouseEvent event) {}  
		public void mouseEntered (MouseEvent event) {}  
		public void mouseExited (MouseEvent event) {}  
		public void mouseClicked (MouseEvent event) {  
			if (players.get(turn).getClass().getName() == "clueGame.Computer") {
				Object[] options = {"OK"};
				JOptionPane.showOptionDialog(null, "Error: It is not your turn", "ERROR", JOptionPane.PLAIN_MESSAGE, JOptionPane.ERROR_MESSAGE, null, options, options[0]);
			}
			else {
				int cellWidth = Board.getInstance().getHeight() / Board.getInstance().getNumRows();
				int cellHeight = Board.getInstance().getWidth() / Board.getInstance().getNumColumns();
				boolean inTargets = false;
				for(BoardCell cell : targets) {
					if(cell.getCol() == event.getX() / cellHeight && cell.getRow() == event.getY() / cellWidth) {
						inTargets = true;
					}
				}
				if(players.get(turn).movedThisTurn) {
					Object[] options = {"OK"};
					JOptionPane.showOptionDialog(null, "Error: You have already moved this turn", "ERROR", JOptionPane.PLAIN_MESSAGE, JOptionPane.ERROR_MESSAGE, null, options, options[0]);
				}else {
					if(inTargets) {
						if(players.get(turn).getInRoom()) {
							players.get(turn).setInRoom(false);
							getRoom(players.get(turn).getLocationCell()).leaveRoom(players.get(turn));
						}
						players.get(turn).setLocation(event.getY() / cellWidth, event.getX() / cellHeight);
						for(BoardCell cell: targets) {
							cell.highlight = false;
						}
		
						if(getCell(event.getY() / cellWidth, event.getX() / cellHeight).isRoomCenter()) {
							getRoom(getCell(event.getY() / cellWidth, event.getX() / cellHeight).getRoomChar()).enterRoom(players.get(turn));
							players.get(turn).setInRoom(true);
						}
						repaint();
					}else {
						Object[] options = {"OK"};
						JOptionPane.showOptionDialog(null, "Error: This is not a valid space", "ERROR", JOptionPane.PLAIN_MESSAGE, JOptionPane.ERROR_MESSAGE, null, options, options[0]);
					}
				}
			}
		}
	}
}
