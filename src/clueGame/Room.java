package clueGame;

public class Room {
	private String name;
	private BoardCell centerCell;
	private BoardCell labelCell;
	
	
	
	public Room() {
		super();
	}

	public Room(String name, BoardCell centerCell, BoardCell labelCell) {
		super();
		this.name = name;
		this.centerCell = centerCell;
		this.labelCell = labelCell;
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

}
