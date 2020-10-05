package experiment;

import java.util.*;

public class TestBoardCell {
	private int row, col;
	private boolean isRoom;
	private boolean occupied;
	
	public TestBoardCell(int row, int col) {
		this.row = row;
		this.col = col;
	}
	
	public Set<TestBoardCell> getAdjList() {
		// returns adj list for the cell
		Set<TestBoardCell> emptySet = Collections.<TestBoardCell>emptySet();  
		return emptySet;
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
	
	public boolean getOccupied(boolean occupied) {
		return occupied;
	}
}
