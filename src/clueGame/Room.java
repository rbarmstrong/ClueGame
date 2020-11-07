package clueGame;

public class Room {
	private String name;
	private char letter;
	private boolean isRoom;
	private BoardCell centerCell;
	private BoardCell labelCell;
	
	
	
	public Room() {
		super();
	}

	public Room(String name, char letter, boolean isRoom) {
		super();
		this.name = name;
		this.isRoom = isRoom;
		this.letter = letter;
	}
	
	public void setCenterCell(BoardCell cell) {
		this.centerCell = cell;
	}
	
	public void setLabelCell(BoardCell cell) {
		this.labelCell = cell;
	}

	public String getName() {
		return name;
	}

	public BoardCell getLabelCell() {
		return labelCell;
	}

	public BoardCell getCenterCell() {
		return centerCell;
	}

	public boolean getIsRoom() {
		return isRoom;
	}
	
	public char getLetter() {
		return letter;
	}
	

}
