import junit.framework.TestCase;
import org.junit.Test;
import utils.Deck;
import utils.NotEnoughCardsException;

public class DeckTest extends TestCase {

    int numPlayers = 5;
    Deck deck;

    @Override
    protected void setUp() {
        System.out.println("Starting up utils.Deck Test Case!");
        deck = new Deck(numPlayers);
    }

    @Test
    public void testDeckSize() {
        assertEquals(52, deck.getDeckSize());
    }

    @Test
    public void testFourSuitsPresent() {
        String[] deckCards = {deck.getCard(0), deck.getCard(13), deck.getCard(26), deck.getCard(39)};
        String[] expectedCards = {"CA", "SA", "HA", "DA"};

        boolean matching = true;
        for (int i=0; i<4; i++) {
            if (!(expectedCards[i].equals(deckCards[i])))
                matching = false;
        }

        assertTrue(matching);
    }

    @Test
    public void testDeckIsShuffled() {
        String[] originalDeck = { deck.getCard(0), deck.getCard(9), deck.getCard(34), deck.getCard(47) };
        deck.shuffle();
        String[] shuffledDeck = { deck.getCard(0), deck.getCard(9), deck.getCard(34), deck.getCard(47) };
        boolean sameValues = false;
        for (int i=0; i<4; i++) {
            if (originalDeck[i].equals(shuffledDeck[i]))
                sameValues = true;
        }

        assertFalse(sameValues);
    }

    @Test
    public void testNumCardsPerPerson() {
        deck.dealAllCards();
        int expectedCardsPerPerson = 52 / numPlayers;
        int cardsPerPerson = deck.getPlayerHand(0).size();
        boolean equalCards = cardsPerPerson >= expectedCardsPerPerson;

        assertTrue(equalCards);
    }

    @Test
    public void testAllCardsDealt() {
        deck.dealAllCards();

        int totalCards = 0;
        for (int i=0; i<numPlayers; i++) {
            totalCards += deck.getPlayerHand(i).size();
        }

        assertEquals(52, totalCards);
    }

    @Test
    public void testSetNumberCardsDealt() throws NotEnoughCardsException {
        deck.dealSetNumberCards(5);

        int expectedTotal = 5 * numPlayers;
        int totalCards = 0;
        for (int i=0; i<numPlayers; i++) {
            totalCards += deck.getPlayerHand(i).size();
        }

        assertEquals(expectedTotal, totalCards);
    }

    @Test
    public void testNotEnoughCardsExceptionThrown() {
        String expectedMessage = "Not enough cards to deal " + 11 + " cards per person!";
        try {
            deck.dealSetNumberCards(11);
        } catch (NotEnoughCardsException exception) {
            assertEquals(expectedMessage, exception.getMessage());
        }
    }

    @Override
    protected void tearDown() {
        System.out.println("Shutting down utils.Deck Test Case!");
        deck = null;
    }

}
