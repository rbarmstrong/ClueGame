package experiment;

import java.util.*;

public class TestBoard {
	final static int ROWS = 4;
	final static int COLS = 4;
	private TestBoardCell[][] grid;
	private Set<TestBoardCell> targets ;
	private Set<TestBoardCell> visited;

	
	public TestBoard() {
		//sets up the board
		grid = new TestBoardCell[ROWS][COLS];
		
		for(int i = 0; i < ROWS; i++) {
			for(int j = 0; j < COLS; j++) {
				grid[i][j] = new TestBoardCell(i,j);
			}
		}
		
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
	
	private void findAllTargets(TestBoardCell thisCell, int numSteps) {
		for (TestBoardCell adjCell : thisCell.getAdjList()) {
			if (!visited.contains(adjCell) && !adjCell.getOccupied()) {
				visited.add(adjCell);
				if (numSteps == 1 || adjCell.getIsRoom()) {
					targets.add(adjCell);
				}
				else {
					findAllTargets(adjCell, numSteps-1);
				}
				visited.remove(adjCell);
			}
		}
	}
	
	
	public void calcTargets(TestBoardCell startCell, int pathlength) {
		//calculates legal targets for a move from startCell of length pathlength 
		visited = new HashSet<TestBoardCell>();
		targets = new HashSet<TestBoardCell>();
		visited.add(startCell);
		findAllTargets(startCell, pathlength);
	}
	
	public Set<TestBoardCell> getTargets() {
		//gets the targets last created by calcTargets()
		return targets;
	}
	
	public TestBoardCell getCell(int row, int col){
		//returns the cell from the board at row, col
		return grid[row][col];
	}
}
