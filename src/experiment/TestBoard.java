package experiment;

import java.util.*;

public class TestBoard {
	
	
	public TestBoard() {
		//sets up the board
	}
	
	public void calcTargets(TestBoardCell startCell, int pathlength) {
		//calculates legal targets for a move from startCell of length pathlength
	}
	
	public Set<TestBoardCell> getTargets() {
		//gets the targets last created by calcTargets()
		Set<TestBoardCell> emptySet = Collections.<TestBoardCell>emptySet();  
		return emptySet;
	}
	
	public TestBoardCell getCell(int row, int col) {
		//returns the cell from the board at row, col
		TestBoardCell returnCell = new TestBoardCell(row, col);
		return returnCell;
	}
}
