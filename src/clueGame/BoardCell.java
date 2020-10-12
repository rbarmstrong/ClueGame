package clueGame;
import java.util.*;

public class BoardCell {

	private int row, col;
	private char initial, secretPassage, roomChar;
	private DoorDirection doorDirection;
	private boolean roomLabel, roomCenter, isDoorway, occupied, isRoom;
	private Set<BoardCell> adjList;

	public BoardCell(int row, int col) {
		adjList = new HashSet<BoardCell>();
		this.row = row;
		this.col = col;
	}

	public void addToAdjList(BoardCell cell) {
		adjList.add(cell);
	}

	public Set<BoardCell> getAdjList() {
		return adjList;
	}

	public void setIsRoom(boolean isRoom) {
		this.isRoom = isRoom;
	}

	public boolean getIsRoom() {
		return isRoom;
	}

	public void setOccupied(boolean occupied) {
		this.occupied = occupied;
	}

	public boolean getOccupied() {
		return occupied;
	}

	@Override
	public String toString() { //used this for some debugging
		return "ROW:" + row + " COL:"+ col;
	}

	public DoorDirection getDoorDirection() {
		
		return doorDirection;
	}

	public boolean isDoorway() {
		
		return isDoorway;
	}

	public boolean isLabel() {
		
		return false;
	}

	public boolean isRoomCenter() {
		
		return false;
	}

	public char getSecretPassage() {
		
		return 0;
	}
	
	public void setRoomChar(char roomChar) {
		this.roomChar = roomChar;
	}
	
	public char getRoomChar() {
		return roomChar;
	}

}

