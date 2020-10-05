package tests;

import java.util.*;

import org.junit.Assert;
import org.junit.jupiter.api.*;

import experiment.*;

public class BoardTestsExp {
	TestBoard board;
	
	@BeforeEach
	public void setUp() {
		//builds a 4x4 board with cells, each with their own adjacency list
		board = new TestBoard();
	}
	
	@Test
	public void testAdjacencyTopLeft() {
		//tests that cells in cell 0,0's adjacency list are accurate
		TestBoardCell cell = board.getCell(0,  0);
		Set<TestBoardCell> testList = cell.getAdjList();
		Assert.assertTrue(testList.contains(board.getCell(1, 0)));
		Assert.assertTrue(testList.contains(board.getCell(0, 1)));
		Assert.assertEquals(2,  testList.size());
	}
	
	@Test
	public void testAdjacencyBotRight() {
		//tests that cells in cell 3,3's adjacency list are accurate
		TestBoardCell cell = board.getCell(3,  3);
		Set<TestBoardCell> testList = cell.getAdjList();
		Assert.assertTrue(testList.contains(board.getCell(2, 3)));
		Assert.assertTrue(testList.contains(board.getCell(3, 2)));
		Assert.assertEquals(2,  testList.size());
	}
	
	@Test
	public void testAdjacencyRightEdge() {
		//tests that cells in cell 1,3's adjacency list are accurate
		TestBoardCell cell = board.getCell(1,  3);
		Set<TestBoardCell> testList = cell.getAdjList();
		Assert.assertTrue(testList.contains(board.getCell(1, 2)));
		Assert.assertTrue(testList.contains(board.getCell(0, 3)));
		Assert.assertTrue(testList.contains(board.getCell(2, 3)));
		Assert.assertEquals(3,  testList.size());
	}
	
	@Test
	public void testAdjacencyLeftEdge() {
		//tests that cells in cell 2,0's adjacency list are accurate
		TestBoardCell cell = board.getCell(2,  0);
		Set<TestBoardCell> testList = cell.getAdjList();
		Assert.assertTrue(testList.contains(board.getCell(2, 1)));
		Assert.assertTrue(testList.contains(board.getCell(1, 0)));
		Assert.assertTrue(testList.contains(board.getCell(3, 0)));
		Assert.assertEquals(3,  testList.size());
	}
	
	@Test
	public void testTargetsNormal3() {
		//tests target cells for when player starts at 0,0 and rolls a 3
		TestBoardCell cell = board.getCell(0, 0);
		board.calcTargets(cell, 3);
		Set<TestBoardCell> targets = board.getTargets();
		Assert.assertEquals(6, targets.size());
		Assert.assertTrue(targets.contains(board.getCell(3, 0)));
		Assert.assertTrue(targets.contains(board.getCell(2, 1)));
		Assert.assertTrue(targets.contains(board.getCell(0, 1)));
		Assert.assertTrue(targets.contains(board.getCell(1, 2)));
		Assert.assertTrue(targets.contains(board.getCell(0, 3)));
		Assert.assertTrue(targets.contains(board.getCell(1, 0)));
	}
	
	@Test
	public void testTargetsNormal4() {
		//tests target cells for when player starts at 1,1 and rolls a 4
		TestBoardCell cell = board.getCell(1, 1);
		board.calcTargets(cell, 4);
		Set<TestBoardCell> targets = board.getTargets();
		Assert.assertEquals(7, targets.size());
		Assert.assertTrue(targets.contains(board.getCell(0, 0)));
		Assert.assertTrue(targets.contains(board.getCell(0, 2)));
		Assert.assertTrue(targets.contains(board.getCell(2, 0)));
		Assert.assertTrue(targets.contains(board.getCell(1, 3)));
		Assert.assertTrue(targets.contains(board.getCell(2, 2)));
		Assert.assertTrue(targets.contains(board.getCell(3, 1)));
		Assert.assertTrue(targets.contains(board.getCell(3, 3)));
	}
	
	@Test
	public void testTargetsOccupied() {
		//tests target cells for when player starts at 2,0 and rolls a 3 when cell 3,0 is occupied
		board.getCell(3, 0).setOccupied(true);
		TestBoardCell cell = board.getCell(2, 0);
		board.calcTargets(cell, 3);
		Set<TestBoardCell> targets = board.getTargets();
		Assert.assertTrue(targets.contains(board.getCell(0, 1)));
		Assert.assertTrue(targets.contains(board.getCell(1, 0)));
		Assert.assertTrue(targets.contains(board.getCell(1, 2)));
		Assert.assertTrue(targets.contains(board.getCell(2, 1)));
		Assert.assertTrue(targets.contains(board.getCell(2, 3)));
		Assert.assertTrue(targets.contains(board.getCell(3, 2)));
		Assert.assertFalse(targets.contains(board.getCell(3, 0)));
	}
	
	@Test
	public void testTargetsRoom() {
		//tests target cells for when player starts at 3,0 and rolls a 3 when cell 3,2 is in a room
		board.getCell(3, 2).setIsRoom(true);
		TestBoardCell cell = board.getCell(3, 0);
		board.calcTargets(cell, 3);
		Set<TestBoardCell> targets = board.getTargets();
		Assert.assertTrue(targets.contains(board.getCell(2, 0)));
		Assert.assertTrue(targets.contains(board.getCell(2, 2)));
		Assert.assertTrue(targets.contains(board.getCell(3, 1)));
		Assert.assertTrue(targets.contains(board.getCell(1, 1)));
		Assert.assertTrue(targets.contains(board.getCell(0, 0)));
		Assert.assertFalse(targets.contains(board.getCell(3, 3)));
	}
	
	@Test
	public void testTargetsMixed() {
		//tests target cells for when player starts at 0,3 and rolls a 3 when cell 0,2 is occupied and cell 1,2 is in a room
		board.getCell(0, 2).setOccupied(true);
		board.getCell(1, 2).setIsRoom(true);
		TestBoardCell cell = board.getCell(0, 3);
		board.calcTargets(cell, 3);
		Set<TestBoardCell> targets = board.getTargets();
		Assert.assertTrue(targets.contains(board.getCell(1, 2)));
		Assert.assertTrue(targets.contains(board.getCell(2, 2)));
		Assert.assertTrue(targets.contains(board.getCell(3, 3)));
		Assert.assertFalse(targets.contains(board.getCell(0, 0)));
		Assert.assertFalse(targets.contains(board.getCell(1, 1)));
		Assert.assertFalse(targets.contains(board.getCell(0, 2)));
		Assert.assertFalse(targets.contains(board.getCell(1, 3)));
	}
}
