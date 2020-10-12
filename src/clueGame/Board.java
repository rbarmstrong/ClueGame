package clueGame;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Board {
	private static int ROWS;
	private static int COLS;
	private BoardCell[][] grid;
	private Set<BoardCell> targets;
	private Set<BoardCell> visited;
	private static HashMap<Character, Room> rooms;
	private static String layoutConfigFile;
	private static String setupConfigFile;
	//private static map<Character, Room>;

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
		loadConfigFiles();
		//create adjacency list for each cell in the grid
		for(int i = 0; i < ROWS; i++) {
			for(int j = 0; j < COLS; j++) {
				if(i - 1 >= 0) {
					grid[i][j].addToAdjList(this.getCell(i - 1,j));
				}
				if(j - 1 >= 0) {
					grid[i][j].addToAdjList(this.getCell(i,j - 1));
				}
				if(i + 1 < ROWS) {
					grid[i][j].addToAdjList(this.getCell(i + 1,j));
				}
				if(j + 1 < COLS) {
					grid[i][j].addToAdjList(this.getCell(i,j + 1));
				}
			}
		}
		
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
		}
		try {
			loadLayoutConfig();
		} catch (FileNotFoundException e) {
			System.out.println("Error. Can't find file: " + setupConfigFile);
		}
	}
	
	public void loadSetupConfig() throws FileNotFoundException {
		rooms = new HashMap<Character, Room>();
		Scanner scan;
		scan = new Scanner(new File(setupConfigFile));
		while(scan.hasNextLine()) {
			String currLine = scan.nextLine();
			if(!currLine.contains("//")) {
				if(currLine.contains("Room")){
					char tempChar = currLine.charAt(currLine.length() - 1);
					String tempLabel = currLine.substring(6, currLine.lastIndexOf(","));
					rooms.put(tempChar, new Room(tempLabel,tempChar,true));
				}else {
					char tempChar = currLine.charAt(currLine.length() - 1);
					String tempLabel = currLine.substring(7, currLine.lastIndexOf(","));
					rooms.put(tempChar, new Room(tempLabel,tempChar,false));
				}
			}
		}

	}
	
	public void loadLayoutConfig() throws FileNotFoundException {
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
			String temp = scan.next();
			if(temp != "/n") {
				numCols++;
			}else {
				break;
			}
		}
		ROWS = numLines;
		COLS = numCols;

		grid = new BoardCell[ROWS][COLS]; //initialize grid to the provided dimensions
		//fill grid with empty cells
		for(int i = 0; i < ROWS; i++) {
			for(int j = 0; j < COLS; j++) {
				grid[i][j] = new BoardCell(i,j);
			}
		}

		numLines = 0;
		numCols = 0;
		scan = new Scanner(new File(layoutConfigFile));
		scan.useDelimiter(",");
		while(scan.hasNext()) {
			String currVal = scan.next();

			if(currVal.contains("\n")) {
				char currChar = currVal.charAt(0);
				grid[numLines][numCols].setRoomChar(currChar); //sets the character in the created grid
				grid[numLines][numCols].setIsRoom(rooms.get(currChar).getIsRoom()); //sets whether each cell is a room
				if(currVal.charAt(1) != '\n' && currVal.charAt(1) != '\r') {
					grid[numLines][numCols].setIsSecretPassage(true);
					grid[numLines][numCols].setSecretPassage(currVal.charAt(1));
					currVal = currVal.substring(1);
				}
				currVal = currVal.replaceAll("\\r|\\n", "");
				numLines++;
				numCols = 0;
				currVal = currVal.substring(1);
				System.out.println(currVal); //DEBUG
				if(numLines == ROWS) {
					break;
				}
				currChar = currVal.charAt(0);
				grid[numLines][numCols].setRoomChar(currChar); //sets the character in the created grid
				grid[numLines][numCols].setIsRoom(rooms.get(currChar).getIsRoom()); //sets whether each cell is a room
				if(currVal.length() > 1) {
					grid[numLines][numCols].setIsSecretPassage(true);
					grid[numLines][numCols].setSecretPassage(currVal.charAt(1));
				}

			}else {
				System.out.println(currVal + " is in Row: " + numLines + " and Col: " + numCols);
				if(currVal.length() == 1) {
					char currChar = currVal.charAt(0);
					grid[numLines][numCols].setRoomChar(currChar); //sets the character in the created grid
					grid[numLines][numCols].setIsRoom(rooms.get(currChar).getIsRoom()); //sets whether each cell is a room
				}else {
					char currChar = currVal.charAt(0);
					grid[numLines][numCols].setRoomChar(currChar); //sets the character in the created grid
					grid[numLines][numCols].setIsRoom(rooms.get(currChar).getIsRoom()); //sets whether each cell is a room
					switch(currVal.charAt(1)) {
					case '*':
						grid[numLines][numCols].setIsRoomCenter(true);
						break;
					case '#':
						grid[numLines][numCols].setIsRoomLabel(true);
						break;
					case '^':
						grid[numLines][numCols].setDoorDirection(DoorDirection.UP);
						break;
					case '>':
						grid[numLines][numCols].setDoorDirection(DoorDirection.RIGHT);
						break;
					case '<':
						grid[numLines][numCols].setDoorDirection(DoorDirection.LEFT);
						break;
					case 'v':
						grid[numLines][numCols].setDoorDirection(DoorDirection.DOWN);
						break;
					default:
						grid[numLines][numCols].setIsSecretPassage(true);
						grid[numLines][numCols].setSecretPassage(currVal.charAt(1));
					}
				}
				numCols++;
			}
		}

	}
	private void populateGrid() {
		
	}
	
	private void findAllTargets(BoardCell thisCell, int numSteps) {
		for (BoardCell adjCell : thisCell.getAdjList()) { //loop through each adj cell
			if (!visited.contains(adjCell) && !adjCell.getOccupied()) {//as long as not visited or occupied
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
		return ROWS;
	}
	public int getNumColumns() {
		
		return COLS;
	}
	
}
