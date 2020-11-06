package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.Card;
import clueGame.CardType;
import clueGame.Player;

public class GameSolutionTest {

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
		
		@Test
		public void testCheckAccusation() {
			board.setSolution("Jordan", "Taser", "Office");
			
			Card jordan = new Card("Jordan", CardType.PERSON);
			Card kathy = new Card("Kathy", CardType.PERSON);
			Card taser = new Card("Taser", CardType.WEAPON);
			Card gun = new Card("Gun", CardType.WEAPON);
			Card office = new Card("Office", CardType.ROOM);
			Card kitchen = new Card("Kitchen", CardType.ROOM);
			
			Card[] trueCase = {jordan, taser, office};
			Card[] falseCasePerson = {kathy, taser, office};
			Card[] falseCaseWeapon = {jordan, gun, office};
			Card[] falseCaseRoom = {jordan, taser, kitchen};
			
			assertTrue(board.checkAccusation(trueCase));
			assertFalse(board.checkAccusation(falseCasePerson));
			assertFalse(board.checkAccusation(falseCaseWeapon));
			assertFalse(board.checkAccusation(falseCaseRoom));
			
		}
}
