package experiment;

import java.util.*;

public class TestBoard {
	final static int ROWS = 4;
	final static int COLS = 4;
	private TestBoardCell[][] grid;
	private Set<TestBoardCell> targets;
	private Set<TestBoardCell> visited;

	//sets up the board
	public TestBoard() {
		grid = new TestBoardCell[ROWS][COLS]; //initialize grid to the provided dimensions
		//fill grid with empty cells
		for(int i = 0; i < ROWS; i++) {
			for(int j = 0; j < COLS; j++) {
				grid[i][j] = new TestBoardCell(i,j);
			}
		}
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
	
	private void findAllTargets(TestBoardCell thisCell, int numSteps) {
<<<<<<< HEAD
		for (TestBoardCell adjCell : thisCell.getAdjList()) { //loop through each adj cell
			if (!visited.contains(adjCell) && !adjCell.getOccupied()) {//as long as not visited or occupied
				visited.add(adjCell); //add cell to visited
				if (numSteps == 1 || adjCell.getIsRoom()) { //if no steps remaining or in a room
					targets.add(adjCell); //add cell to targets
				}
				else {
					findAllTargets(adjCell, numSteps-1); //else call recursive function
=======
		for (TestBoardCell adjCell : thisCell.getAdjList()) {
			if (!visited.contains(adjCell) && !adjCell.getOccupied()) {
				visited.add(adjCell);
				if (numSteps == 1 || adjCell.getIsRoom()) {
					targets.add(adjCell);
				}
				else {
					findAllTargets(adjCell, numSteps-1);
>>>>>>> 4d4222f4992aaa19202e58cfd72aa1625757d2e3
				}
				visited.remove(adjCell);
			}
		}
	}
	
<<<<<<< HEAD
	//calculates legal targets for a move from startCell of length pathlength 
	public void calcTargets(TestBoardCell startCell, int pathlength) {
		//first initialize visited list and targets list, add the cell the character is currently on to the visited list
=======
	
	public void calcTargets(TestBoardCell startCell, int pathlength) {
		//calculates legal targets for a move from startCell of length pathlength 
>>>>>>> 4d4222f4992aaa19202e58cfd72aa1625757d2e3
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
