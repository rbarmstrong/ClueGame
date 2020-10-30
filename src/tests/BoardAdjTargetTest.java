package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.BoardCell;

public class BoardAdjTargetTest {
	// We make the Board static because we can load it one time and 
	// then do all the tests. 
	private static Board board;
	
	@BeforeAll
	public static void setUp() {
		// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("data/ClueLayout.csv", "data/ClueSetup.txt");		
		// Initialize will load config files 
		board.initialize();
	}

	// Ensure that player does not move around within room
	// These cells are LIGHT ORANGE on the planning spreadsheet
	@Test
	public void testAdjacenciesRooms()
	{
		// test center of room connected by secret passage
		Set<BoardCell> testList = board.getAdjList(2, 2);
		assertEquals(2, testList.size());
		assertTrue(testList.contains(board.getCell(4, 6)));
		assertTrue(testList.contains(board.getCell(21, 20)));
		
		// test room but not center of room
		testList = board.getAdjList(2, 20);
		assertEquals(0, testList.size());
	}

	
	// Ensure door locations include their rooms and also additional walkways
	// These cells are LIGHT ORANGE on the planning spreadsheet
	@Test
	public void testAdjacencyDoor()
	{
		// test doorway
		Set<BoardCell> testList = board.getAdjList(7, 12);
		assertEquals(4, testList.size());
		assertTrue(testList.contains(board.getCell(7, 11)));
		assertTrue(testList.contains(board.getCell(7, 13)));
		assertTrue(testList.contains(board.getCell(8, 12)));
		assertTrue(testList.contains(board.getCell(4, 11)));
	}
	
	// Test a variety of walkway scenarios
	// These tests are Dark Orange on the planning spreadsheet
	@Test
	public void testAdjacencyWalkways()
	{
		// test walkway touching left edge of board
		Set<BoardCell> testList = board.getAdjList(5, 0);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCell(5, 1)));
		
		// test walkway touching top edge of board
		testList = board.getAdjList(0, 7);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCell(1, 7)));
		
		// test walkway touching right edge of board
		testList = board.getAdjList(17, 25);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCell(17, 24)));
		
		// test walkway touching bottom edge of board
		testList = board.getAdjList(26, 16);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCell(25, 16)));
		
		// test walkway next to a room but not a doorway
		testList = board.getAdjList(16, 21);
		assertEquals(2, testList.size());
		assertTrue(testList.contains(board.getCell(16, 20)));
		assertTrue(testList.contains(board.getCell(17, 21)));
	}
	
	
	// tests targets from center of room without secret passage
	// These are LIGHT BLUE on the planning spreadsheet
	@Test
	public void testTargetsLeaveRoom() {
		// test a roll of 2
		board.calcTargets(board.getCell(12, 20), 2);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(8, targets.size());
		assertTrue(targets.contains(board.getCell(8, 16)));
		assertTrue(targets.contains(board.getCell(8, 18)));	
		assertTrue(targets.contains(board.getCell(7, 17)));	
		assertTrue(targets.contains(board.getCell(13, 15)));	
		assertTrue(targets.contains(board.getCell(12, 14)));	
		assertTrue(targets.contains(board.getCell(11, 15)));	
		assertTrue(targets.contains(board.getCell(17, 21)));	
		assertTrue(targets.contains(board.getCell(17, 23)));	
	}
	
	// tests targets from center of room with secret passage
	// These are LIGHT BLUE on the planning spreadsheet
	@Test
	public void testTargetsLeaveRoomPassage() {
		// test a roll of 2
		board.calcTargets(board.getCell(21, 20), 2);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(6, targets.size());
		assertTrue(targets.contains(board.getCell(2, 2)));
		assertTrue(targets.contains(board.getCell(17, 17)));
		assertTrue(targets.contains(board.getCell(17, 19)));
		assertTrue(targets.contains(board.getCell(16, 18)));
		assertTrue(targets.contains(board.getCell(17, 23)));
		assertTrue(targets.contains(board.getCell(17, 25)));
	}

	// tests targets from walkways from which one could enter a room
	// These are LIGHT BLUE on the planning spreadsheet
	@Test
	public void testTargetsEnterRoom() {
		// test a roll of 3
		board.calcTargets(board.getCell(21, 16), 3);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(4, targets.size());
		assertTrue(targets.contains(board.getCell(23, 15)));
		assertTrue(targets.contains(board.getCell(24, 16)));	
		assertTrue(targets.contains(board.getCell(18, 16)));	
		assertTrue(targets.contains(board.getCell(22, 11)));	
	}

	//tests targets on walkways with various rolls
	@Test
	public void testTargetsInWalkway() {
		// test a roll of 2
		board.calcTargets(board.getCell(6, 24), 2);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(4, targets.size());
		assertTrue(targets.contains(board.getCell(6, 22)));
		assertTrue(targets.contains(board.getCell(7, 23)));	
		assertTrue(targets.contains(board.getCell(8, 24)));	
		assertTrue(targets.contains(board.getCell(7, 25)));	
		
		// test a roll of 3
		board.calcTargets(board.getCell(6, 24), 3);
		targets= board.getTargets();
		assertEquals(5, targets.size());
		assertTrue(targets.contains(board.getCell(6, 23)));
		assertTrue(targets.contains(board.getCell(6, 21)));
		assertTrue(targets.contains(board.getCell(7, 22)));	
		assertTrue(targets.contains(board.getCell(7, 24)));	
		assertTrue(targets.contains(board.getCell(8, 23)));	
	}

	@Test
	// test to make sure occupied locations do not cause problems
	public void testTargetsOccupied() {
		// test a roll of 3 blocked 1 right
		board.getCell(17, 11).setOccupied(true);
		board.calcTargets(board.getCell(17, 10), 3);
		board.getCell(17, 11).setOccupied(false);
		Set<BoardCell> targets = board.getTargets();
		assertEquals(6, targets.size());
		assertTrue(targets.contains(board.getCell(16, 8)));
		assertTrue(targets.contains(board.getCell(17, 7)));
		assertTrue(targets.contains(board.getCell(18, 8)));	
		assertTrue(targets.contains(board.getCell(17, 9)));	
		assertTrue(targets.contains(board.getCell(18, 12)));	
		assertTrue(targets.contains(board.getCell(18, 10)));	
	}
}
