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
		this.letter = letter;
		this.isRoom = isRoom;
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
		
		return null;
	}

	public BoardCell getCenterCell() {
		
		return null;
	}

	public boolean getIsRoom() {
		return isRoom;
	}

}
