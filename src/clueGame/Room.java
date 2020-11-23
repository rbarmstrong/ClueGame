package clueGame;

import java.util.ArrayList;

public class Room {
	private String name;
	private char letter;
	private boolean isRoom;
	private BoardCell centerCell;
	private BoardCell labelCell;
	private ArrayList<Player> playersInRoom;
	
	
	public Room() {
		super();
		playersInRoom = new ArrayList<>();
	}

	public Room(String name, char letter, boolean isRoom) {
		super();
		this.name = name;
		this.isRoom = isRoom;
		this.letter = letter;
		playersInRoom = new ArrayList<>();
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
	
	public void enterRoom(Player player) {
		playersInRoom.add(player);
	}
	
	public void leaveRoom(Player player) {
		playersInRoom.remove(player);
	}
	
	public ArrayList<Player> getPlayers(){
		return playersInRoom;
	}
	
	public boolean getIsRoom() {
		return isRoom;
	}
	
	public char getLetter() {
		return letter;
	}
	

}
