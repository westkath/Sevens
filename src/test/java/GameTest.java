import junit.framework.TestCase;
import org.junit.Test;

public class GameTest extends TestCase {

    Game game;
    int numPlayers = 5;

    @Override
    protected void setUp() {
        System.out.println("Starting up Game Test Case!");
        game = new Game(numPlayers);
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

    @Override
    protected void tearDown() {
        System.out.println("Shutting down Game Test Case!");
        game = null;
    }

}
