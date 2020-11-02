package clueGame;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Board {
	private static int rows;
	private static int cols;
	private BoardCell[][] grid;
	private Set<BoardCell> targets;
	private Set<BoardCell> visited;
	private static HashMap<Character, Room> rooms;
	private static String layoutConfigFile;
	private static String setupConfigFile;
	private static ArrayList<Card> deck;
	private static ArrayList<Player> players;
	private Solution theAnswer;

	/*
	 * variable and methods used for singleton pattern
	 */
	private static Board theInstance = new Board();
	// constructor is private to ensure only one can be created
	private Board() {
		super() ;
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
		deck = new ArrayList<>();
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
				}else if(currLine.contains("Space")){
					tempChar = currLine.charAt(currLine.length() - 1);
					tempLabel = currLine.substring(7, currLine.lastIndexOf(","));
					rooms.put(tempChar, new Room(tempLabel,tempChar,false));
				}else if(currLine.contains("Weapon")){
					tempLabel = currLine.substring(8);
					Card tempCard = new Card();
					tempCard.setCardName(tempLabel);
					tempCard.setType(CardType.WEAPON);
					deck.add(tempCard);
				}else if(currLine.contains("Player")) {
					currLine = currLine.substring(8);
					if(currLine.contains("Human")) {
						Human tempPlayer;
						currLine = currLine.substring(7);
					}else if(currLine.contains("Computer")){
						Computer tempPlayer;
						currLine = currLine.substring(10);
					}else {
						throw new BadConfigFormatException();
					}
					tempLabel = currLine.substring(0, currLine.indexOf(","));
					currLine = currLine.substring(tempLabel.length() - 1, currLine.length() - 1);
					System.out.println(currLine); //TODO
					
					
					
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
				if (numSteps == 1 || adjCell.getIsRoom()) { //if no steps remaining or in a room
					targets.add(adjCell); //add cell to targets
				}
				else {
					findAllTargets(adjCell, numSteps-1); //else call recursive function
				}
				visited.remove(adjCell);
			}
		}
	}

	//calculates legal targets for a move from startCell of length pathlength 
	public void calcTargets(BoardCell startCell, int pathlength) {
		//first initialize visited list and targets list, add the cell the character is currently on to the visited list
		visited = new HashSet<BoardCell>();
		targets = new HashSet<BoardCell>();
		visited.add(startCell);
		findAllTargets(startCell, pathlength);
	}
	//TODO
	public void deal() {
		
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
	public ArrayList<Card> getDeck() {
		return deck;
	}
}