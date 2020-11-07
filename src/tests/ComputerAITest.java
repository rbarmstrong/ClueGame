package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.Card;
import clueGame.CardType;
import clueGame.Player;

public class ComputerAITest {

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
		public void testSelectTargets() {
			Card office = new Card("Office", CardType.ROOM, 'O');
			Card kitchen = new Card("Kitchen", CardType.ROOM, 'K');
			Card diningRoom = new Card("Dining Room", CardType.ROOM, 'D');
			Card sunRoom = new Card("Sun Room", CardType.ROOM, 'S');
		}
}
