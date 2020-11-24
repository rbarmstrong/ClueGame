package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.awt.Color;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.Card;
import clueGame.CardType;
import clueGame.Human;
import clueGame.Player;
import clueGame.Solution;

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
			board.setTestMode();
		}
		
		@Test
		public void testCheckAccusation() { //Checks accusations;
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
		
		@Test
		public void testDisproveSuggestion() { //This test works because although there are random elements, the seed is constant
			Player player = new Human();
			Card bart = new Card("Bartholomew", CardType.PERSON);
			Card bat = new Card("Bat", CardType.WEAPON);
			Card diningRoom = new Card("Dining Room", CardType.ROOM);
			Card philip = new Card("Philip", CardType.PERSON);
			Card knife = new Card("Knife", CardType.WEAPON);
			Card sunRoom = new Card("Sun Room", CardType.ROOM);
			player.updateHand(philip);
			player.updateHand(knife);
			player.updateHand(sunRoom);
			
			Solution suggestion1 = new Solution(philip, diningRoom, bat);
			assertEquals(philip, player.disproveSuggestion(suggestion1)); 
			
			Solution suggestion2 = new Solution(bart, diningRoom, knife);
			assertEquals(knife, player.disproveSuggestion(suggestion2));
			
			Solution suggestion3 = new Solution(philip, sunRoom, bat);
			assertEquals(sunRoom, player.disproveSuggestion(suggestion3));
			
			Solution suggestion4 = new Solution(bart,diningRoom,bat);
			assertEquals(null, player.disproveSuggestion(suggestion4));

		}
		
		@Test
		public void testHandleSuggestion() {
			Card jordan = new Card("Jordan", CardType.PERSON);
			Card kathy = new Card("Kathy", CardType.PERSON);
			Card bart = new Card("Bartholomew", CardType.PERSON);
			Card philip = new Card("Philip", CardType.PERSON);
			Card taser = new Card("Taser", CardType.WEAPON);
			Card gun = new Card("Gun", CardType.WEAPON);
			Card bat = new Card("Bat", CardType.WEAPON);
			Card knife = new Card("Knife", CardType.WEAPON);
			Card office = new Card("Office", CardType.ROOM);
			Card kitchen = new Card("Kitchen", CardType.ROOM);
			Card diningRoom = new Card("Dining Room", CardType.ROOM);
			Card sunRoom = new Card("Sun Room", CardType.ROOM);
			
			board.setTestPlayers();
			
			board.getPlayers().get(0).updateHand(jordan);
			board.getPlayers().get(0).updateHand(kathy);
			board.getPlayers().get(0).updateHand(bat);
			
			board.getPlayers().get(1).updateHand(taser);
			board.getPlayers().get(1).updateHand(kitchen);
			board.getPlayers().get(1).updateHand(bart);
			
			board.getPlayers().get(2).updateHand(sunRoom);
			board.getPlayers().get(2).updateHand(knife);
			board.getPlayers().get(2).updateHand(diningRoom);
			
			Solution suggestion1 = new Solution(philip, knife, kitchen);
			assertEquals(kitchen, board.handleSuggestion(board.getPlayers().get(0), suggestion1));
			
			Solution suggestion2 = new Solution(philip, knife, office);
			assertEquals(knife, board.handleSuggestion(board.getPlayers().get(0), suggestion2));
			
			Solution suggestion3 = new Solution(jordan, bat, office);
			assertEquals(null, board.handleSuggestion(board.getPlayers().get(0), suggestion3));
			
			Solution suggestion4 = new Solution(jordan, gun, office);
			assertEquals(jordan, board.handleSuggestion(board.getPlayers().get(1), suggestion4));
			
			Solution suggestion5 = new Solution(philip, gun, office);
			assertEquals(null, board.handleSuggestion(board.getPlayers().get(2), suggestion5));
		}
}
