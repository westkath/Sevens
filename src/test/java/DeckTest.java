import junit.framework.TestCase;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class DeckTest extends TestCase {

    int numPlayers = 5;
    Deck deck = new Deck(numPlayers);

    @Override
    protected void setUp() {
        System.out.println("Starting up Deck Test Case!");
        // create object instance
    }

    public void testDeckSize() {
        assertEquals(52, deck.getDeckSize());
    }

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

    public void testDeckIsShuffled() {
        String[] originalDeck = deck.getDeck();
        deck.shuffle();
        String[] shuffledDeck = deck.getDeck();

        assertNotEquals(shuffledDeck, originalDeck);
    }

    public void testNumCardsPerPerson() {
        deck.dealAllCards();
        int expectedCardsPerPerson = 52 / numPlayers;
        int cardsPerPerson = deck.getPlayerHand(0).size();
        boolean equalCards = cardsPerPerson >= expectedCardsPerPerson;

        assertTrue(equalCards);
    }

    public void testAllCardsDealt() {
        deck.dealAllCards();

        int totalCards = 0;
        for (int i=0; i<numPlayers; i++) {
            totalCards += deck.getPlayerHand(i).size();
        }

        assertEquals(52, totalCards);
    }

    @Override
    protected void tearDown() {
        System.out.println("Shutting down Deck Test Case!");
        // remove object instances
    }

}
