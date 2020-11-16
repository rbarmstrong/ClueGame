package clueGame;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.*;

public class BoardCell{

	private int row, col;
	private char secretPassage, roomChar;
	private DoorDirection doorDirection;
	private boolean roomLabel, roomCenter, isDoorway, occupied, isRoom, isSecretPassage;
	private Set<BoardCell> adjList;
	boolean highlight;

	public BoardCell(int row, int col) {
		adjList = new HashSet<BoardCell>();
		this.row = row;
		this.col = col;
		roomLabel = false;
		roomCenter = false;
		isDoorway = false;
		occupied = false;
		isRoom = false;
		doorDirection = DoorDirection.NONE;
		isSecretPassage = false;
		highlight = false;
	}
	
	public void drawSelf(int x, int y, int width, int height, Graphics g) {
		if(isRoom) {
			g.setColor(Color.GRAY);
			g.fillRect(x, y, width, height);
		}else if(roomChar == 'X') {
			g.setColor(Color.BLACK);
			g.fillRect(x, y, width, height);
		}else {
			if(highlight) {
				g.setColor(Color.ORANGE);
			}else {
				g.setColor(Color.YELLOW);
			}
			g.fillRect(x, y, width, height);
			g.setColor(Color.BLACK);
			g.drawRect(x, y, width, height);
		}
	}
	
	public void drawDoorway(int x, int y, int width, int height, Graphics g) {
		g.setColor(Color.BLUE);
		int doorDivide = 6;
		switch(doorDirection) {
		case DOWN:
			g.fillRect(x, y+height, width, height/doorDivide);
			break;
		case UP:
			g.fillRect(x, y - (height/doorDivide), width, height/doorDivide);
			break;
		case LEFT:
			g.fillRect(x - (width / doorDivide), y, width/doorDivide, height);
			break;
		case RIGHT:
			g.fillRect(x + width, y, width/doorDivide, height);
			break;
		default:
			break;
		}
	}
	
	public void drawRoomName(int x, int y, Graphics g) {
		g.setColor(Color.BLUE);
		Font font = new Font("font", Font.BOLD, 13);
		g.setFont(font);
		g.drawString(Board.getInstance().getRoom(roomChar).getName(), x, y);
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
		return roomLabel;
	}

	public boolean isRoomCenter() {
		
		return roomCenter;
	}

	public char getSecretPassage() {
		return secretPassage;
	}
	
	public void setRoomChar(char roomChar) {
		this.roomChar = roomChar;
	}
	
	public char getRoomChar() {
		return roomChar;
	}

	public void setIsRoomCenter(boolean isRoomCenter) {
		this.roomCenter = isRoomCenter;
		
	}

	public void setIsRoomLabel(boolean isRoomLabel) {
		this.roomLabel = isRoomLabel;
		
	}

	public void setDoorDirection(DoorDirection direction) {
		this.doorDirection = direction;
		
	}

	public void setIsSecretPassage(boolean isSecretPassage) {
		this.isSecretPassage = isSecretPassage;
		
	}

	public void setSecretPassage(char secretPassage) {
		this.secretPassage = secretPassage;
		
	}
	public void setisDoorway(boolean isDoorway) {
		this.isDoorway = isDoorway;
		
	}
	
	public int getRow() {
		return row;
	}
	public int getCol() {
		return col;
	}

	public boolean isSecretPassage() {
		return isSecretPassage;
	}

}

