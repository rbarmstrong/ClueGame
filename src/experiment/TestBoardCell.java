package experiment;

import java.util.*;

public class TestBoardCell {
	private int row, col;
	private boolean isRoom;
	private boolean occupied;
	private Set<TestBoardCell> adjList;
	
	public TestBoardCell(int row, int col) {
		adjList = new HashSet<TestBoardCell>();
		this.row = row;
		this.col = col;
	}
	
	public void addToAdjList(TestBoardCell cell) {
		adjList.add(cell);
	}
	
	public Set<TestBoardCell> getAdjList() {
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
	public String toString() {
		return "ROW:" + row + " COL:"+ col;
	}
	
	
	
}
