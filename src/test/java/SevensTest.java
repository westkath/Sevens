import io.TestInput;
import io.TestOutput;
import junit.framework.TestCase;
import org.junit.Test;
import sevens.Game;

import java.util.ArrayList;
import java.util.LinkedList;

public class SevensTest extends TestCase {

    Game game;
    int numPlayers = 5;
    LinkedList<String> testHand;
    LinkedList<String> testHand2;

    TestInput testInput = new TestInput();
    TestOutput testOutput = new TestOutput();

    @Override
    protected void setUp() {
        System.out.println("Starting up Game Test Case!");
        game = new Game(numPlayers);

        testHand = new LinkedList<>();
        testHand.add("C7");
        testHand.add("D7");
        testHand.add("H7");
        testHand.add("S7");

        testHand2 = new LinkedList<>();
    }

    private ArrayList<String> setupTest() {
        testOutput.clear();
        game.setUserOutput(testOutput);

        return testOutput.getTestOutputs();
    }

    @Test
    public void testIllegalArgumentExceptionThrown() {
        String expectedMessage = 53 + " is an invalid number of players to have! Exiting Game";
        try {
            game = new Game(53);
        } catch (IllegalArgumentException exception) {
            assertEquals(expectedMessage, exception.getMessage());
        }
    }

    @Test
    public void testGameNoStart() {
        Game validGame = new Game(52);
        assertNotNull(validGame);
    }

    @Test
    public void testInitialise() {
        game.initialise();
        int totalCards = 0;
        for (int i=0; i<numPlayers; i++) {
            totalCards += game.getPlayerHand(i).size();
        }
        assertEquals(52, totalCards);
    }

    @Test
    public void testStringValueToInt() {
        int aceValue = game.stringValueToInt("A");
        int tenValue = game.stringValueToInt("X");
        int jackValue = game.stringValueToInt("J");
        int queenValue = game.stringValueToInt("Q");
        int kingValue = game.stringValueToInt("K");

        assertEquals(1, aceValue);
        assertEquals(10, tenValue);
        assertEquals(11, jackValue);
        assertEquals(12, queenValue);
        assertEquals(13, kingValue);
    }

    @Test
    public void testGetSuitByString() {
        String heartsValue = game.getSuitByString("H");
        String diamondsValue = game.getSuitByString("D");
        String clubsValue = game.getSuitByString("C");
        String spadesValue = game.getSuitByString("S");

        assertEquals("Hearts", heartsValue);
        assertEquals("Diamonds", diamondsValue);
        assertEquals("Clubs", clubsValue);
        assertEquals("Spades", spadesValue);
    }

    @Test
    public void testGetNextPlayer() {
        int firstPlayer = game.getNextPlayer(numPlayers - 1);
        int finalPlayer = game.getNextPlayer(numPlayers - 2);

        assertEquals(0, firstPlayer);
        assertEquals(numPlayers-1, finalPlayer);
    }

    @Test
    public void testAddSevenToPile() {
        game.addSevenToPile("S7", "Spades");
        String storedCard = game.getSuitPile("Spades").getFirst();

        assertEquals("S7", storedCard);
    }

    @Test
    public void testAddCardToPile() {
        game.addSevenToPile("H7", "Hearts");
        game.addCardToPile("H8", "Hearts");
        String storedCard = game.getSuitPile("Hearts").getLast();

        assertEquals("H8", storedCard);
    }

    @Test
    public void testCheckAboveAndBelow() {
        game.addSevenToPile("H7", "Hearts");
        boolean validMove = game.checkAboveAndBelow("Hearts", 8);
        boolean invalidMove = game.checkAboveAndBelow("Hearts", 9);

        assertTrue(validMove);
        assertFalse(invalidMove);
    }

    @Test
    public void testIsCardValid() {
        boolean validCard = game.isCardValid("S7");
        boolean invalidSuitCard = game.isCardValid("X8");
        boolean invalidNumberCard = game.isCardValid("HE");
        boolean invalidExtraCard = game.isCardValid("D44");

        assertTrue(validCard);
        assertFalse(invalidSuitCard);
        assertFalse(invalidNumberCard);
        assertFalse(invalidExtraCard);
    }

    @Test
    public void whoHasFirstSeven() {
        game.initialise();
        game.setPlayerHand(0, testHand);
        int player = game.whoHasFirstSeven();

        assertEquals(0, player);
    }

    @Test
    public void testPlayCard() {
        game.initialise();
        game.setPlayerHand(0, testHand);

        game.playCard("C7", 0);
        int numCards = game.getPlayerHand(0).size();

        game.playCard("", 0);

        assertEquals(3, numCards);
    }

    @Test
    public void testOutputPiles() {
        game.initialise();
        game.setPlayerHand(0, testHand);
        game.playCard("H7", 0);
        game.outputPiles();
        String output = game.getOutput().getOutputMessage();

        assertEquals("\n\u001B[1;95mCurrent Piles:\u001B[0m", output);
    }

    @Test
    public void testGameOver() {
        game.initialise();
        game.setPlayerHand(0, testHand2);
        boolean isOver = game.isGameOver();

        assertTrue(isOver);
    }

    @Test
    public void testOutputPiles() {

    }

    @Override
    protected void tearDown() {
        System.out.println("Shutting down Game Test Case!");
        game = null;
    }

}
