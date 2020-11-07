package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.Card;
import clueGame.CardType;
import clueGame.Computer;
import clueGame.Player;
import clueGame.Solution;

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
			
			Card jordan = new Card("Jordan", CardType.PERSON);
			Card office = new Card("Office", CardType.ROOM, 'O');
			Card kitchen = new Card("Kitchen", CardType.ROOM, 'K');
			Card diningRoom = new Card("Dining Room", CardType.ROOM, 'D');
			Card sunRoom = new Card("Sun Room", CardType.ROOM, 'S');
			
			board.setTestPlayers();

			
			
			Computer compPlayer = (Computer) board.getPlayers().get(1);
			
			compPlayer.updateHand(kitchen);
			compPlayer.updateHand(diningRoom);
			compPlayer.updateHand(jordan);
			
			//if room in list and not in hand
			board.calcTargets(board.getCell(6,  20), 2);
			assertEquals(board.getCell(3, 20), compPlayer.selectTargets());
			
			//if no rooms in list
			Set<BoardCell> visitedCells = new HashSet<BoardCell>();
			for (int i = 0; i < 100; i++) {
				board.calcTargets(board.getCell(7, 21), 2);
				visitedCells.add(compPlayer.selectTargets());
			}
			assertTrue(visitedCells.contains(board.getCell(6, 20)));
			assertTrue(visitedCells.contains(board.getCell(7, 19)));
			assertTrue(visitedCells.contains(board.getCell(8, 20)));
			assertTrue(visitedCells.contains(board.getCell(8, 22)));
			assertTrue(visitedCells.contains(board.getCell(6, 22)));
			assertTrue(visitedCells.contains(board.getCell(7, 23)));

			//if only room in list is room in hand
			Set<BoardCell> visitedCellsSequel = new HashSet<BoardCell>();
			for (int i = 0; i < 100; i++) {
				board.calcTargets(board.getCell(8, 18), 2);
				visitedCellsSequel.add(compPlayer.selectTargets());
			}
			assertTrue(visitedCellsSequel.contains(board.getCell(12, 20)));
			assertTrue(visitedCellsSequel.contains(board.getCell(6, 18)));
			assertTrue(visitedCellsSequel.contains(board.getCell(7, 17)));
			assertTrue(visitedCellsSequel.contains(board.getCell(7, 19)));
			assertTrue(visitedCellsSequel.contains(board.getCell(8, 16)));
			assertTrue(visitedCellsSequel.contains(board.getCell(8, 20)));
		}
		@Test
		public void computerPlayerCreateSuggestion() {
			
			Card office = new Card("Office", CardType.ROOM, 'O');
			
			Card you = new Card("You", CardType.PERSON);
			Card jordan = new Card("Jordan", CardType.PERSON);
			Card henry = new Card("Henry", CardType.PERSON);
			Card kathy = new Card("Kathy", CardType.PERSON);
			Card philip = new Card("Philip", CardType.PERSON);
			Card bart = new Card("Bartholomew", CardType.PERSON);

			Card gun = new Card("Gun", CardType.WEAPON);
			Card knife = new Card("Knife", CardType.WEAPON);
			Card bat = new Card("Bat", CardType.WEAPON);
			Card sword = new Card("Sword", CardType.WEAPON);
			Card taser = new Card("Taser", CardType.WEAPON);
			Card hammer = new Card("Hammer", CardType.WEAPON);
			
			board.setTestPlayers();
			Computer compPlayer = (Computer) board.getPlayers().get(1);
			compPlayer.setLocation(3, 20);
			
			Set<String> chosenWeapons = new HashSet<String>();
			Set<String> chosenPersons = new HashSet<String>();
			for (int i = 0; i < 100; i++) {
				Solution suggestion = compPlayer.createSuggestion();
				chosenWeapons.add(suggestion.getWeapon().getCardName());
				chosenPersons.add(suggestion.getPerson().getCardName());
			}
			
			assertTrue(chosenWeapons.contains("Gun"));
			assertTrue(chosenWeapons.contains("Knife"));
			assertTrue(chosenWeapons.contains("Bat"));
			assertTrue(chosenWeapons.contains("Sword"));
			assertTrue(chosenWeapons.contains("Taser"));
			assertTrue(chosenWeapons.contains("Hammer"));
			assertTrue(chosenPersons.contains("You"));
			assertTrue(chosenPersons.contains("Jordan"));
			assertTrue(chosenPersons.contains("Henry"));
			assertTrue(chosenPersons.contains("Kathy"));
			assertTrue(chosenPersons.contains("Philip"));
			assertTrue(chosenPersons.contains("Bartholomew"));
			
			for(int i = 0; i < board.getDeck().size(); i++) {
				if(!(board.getDeck().get(i).getCardName().equals("Hammer")) && !(board.getDeck().get(i).getCardName().equals("Henry"))) {
					
					compPlayer.updateHand(board.getDeck().get(i));
				}
			}
			Solution test1 = compPlayer.createSuggestion();
			board.setSolution("Henry","Office","Hammer");
			Card[] solutionArray = new Card[] {test1.person, test1.room, test1.weapon};
			assertTrue(test1.person.getCardName().equals("Henry"));
			assertTrue(test1.room.getCardName().equals("Office"));
			assertTrue(test1.weapon.getCardName().equals("Hammer"));

		}

}
