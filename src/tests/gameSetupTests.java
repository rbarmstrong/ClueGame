package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.awt.Color;
import java.util.HashSet;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.Card;
import clueGame.CardType;
import clueGame.Player;

public class gameSetupTests {

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
		public void testPlayerLoad() {
			//Test Num Players
			assertEquals(6, board.getPlayers().size());
			//Test Player Type
			assertEquals("clueGame.Human", board.getPlayers().get(0).getClass().getName());
			assertEquals("clueGame.Computer", board.getPlayers().get(1).getClass().getName());
			assertEquals("clueGame.Computer", board.getPlayers().get(2).getClass().getName());
			assertEquals("clueGame.Computer", board.getPlayers().get(3).getClass().getName());
			assertEquals("clueGame.Computer", board.getPlayers().get(4).getClass().getName());
			assertEquals("clueGame.Computer", board.getPlayers().get(5).getClass().getName());
			//Test Names
			assertEquals("You", board.getPlayers().get(0).getName());
			assertEquals("Kathy", board.getPlayers().get(2).getName());
			assertEquals("Philip", board.getPlayers().get(5).getName());
			//Test Colors
			assertEquals(Color.ORANGE, board.getPlayers().get(5).getColor());
			assertEquals(Color.BLUE, board.getPlayers().get(1).getColor());
			assertEquals(Color.RED, board.getPlayers().get(0).getColor());
			//Test Location
			assertEquals(1, board.getPlayers().get(0).getLocation()[0]);
			assertEquals(7, board.getPlayers().get(0).getLocation()[1]);
			assertEquals(18, board.getPlayers().get(5).getLocation()[0]);
			assertEquals(13, board.getPlayers().get(5).getLocation()[1]);

		}
		
		@Test
		public void testCardLoad() {
			//Test Deck Size
			assertEquals(21, board.getDeck().size());
			//Test number of cards per type
			int numRooms = 0, numPeople = 0, numWeapons = 0;
			for (Card card : board.getDeck()) {
				if (card.getType().equals(CardType.ROOM)) {
					numRooms++;
				}
				if (card.getType().equals(CardType.PERSON)) {
					numPeople++;
				}
				if (card.getType().equals(CardType.WEAPON)) {
					numWeapons++;
				}
			}
			assertEquals(9, numRooms);
			assertEquals(6, numPeople);
			assertEquals(6, numWeapons);
			//Test name of a person card
			assertEquals("Bartholomew", board.getDeck().get(19).getCardName());
			//Test name of a weapon card
			assertEquals("Gun", board.getDeck().get(9).getCardName());
			//Test the name of a room card
			assertEquals("TV Room", board.getDeck().get(0).getCardName());
		}
		
		@Test
		public void testSolutionDealt() {
			//Test that the solution exists and each card is an appropriate type
			assertEquals(CardType.PERSON, board.getSolution().getPerson().getType());
			assertEquals(CardType.ROOM, board.getSolution().getRoom().getType());
			assertEquals(CardType.WEAPON, board.getSolution().getWeapon().getType());

			//Test that no solution cards are in any players' hands
			for (Player player : board.getPlayers()) {
				for (Card card : player.getHand()) {
					assertNotEquals(card.getCardName(), board.getSolution().getPerson().getCardName());
					assertNotEquals(card.getCardName(), board.getSolution().getRoom().getCardName());
					assertNotEquals(card.getCardName(), board.getSolution().getWeapon().getCardName());
				}
			}
		}
		
		@Test
		public void testCardsDealt() {
			//Test that all cards have been dealt from the dealer's deck
			assertEquals(0, board.getDealer().size());
			//Test that each player has an appropriate number of cards in their hand
			int handSize = (board.getDeck().size() - 3) / board.getPlayers().size();
			for (Player player : board.getPlayers()) {
				assertTrue(player.getHand().size() >= handSize);
			}
			//Test that no two players share the same card and no more than 1 of a card exists in each players hand
			HashSet<Card> uniqueCards = new HashSet<Card>();
			for (Player player : board.getPlayers()) {
				for (Card card : player.getHand()) {
					uniqueCards.add(card);
				}
			}
			assertEquals(board.getDeck().size() - 3, uniqueCards.size());
		}
}
