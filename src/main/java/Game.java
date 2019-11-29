import java.util.ArrayList;
import java.util.LinkedList;

public class Game {

    private int numPlayers;
    private ArrayList<LinkedList<String>> playerHands = new ArrayList<>();

    public Game(int numPlayers) {
        this.numPlayers = numPlayers;
        initialise();
    }

    public void initialise() {
        Deck deck = new Deck(numPlayers);
        deck.shuffle();
        deck.dealAllCards();

        for (int i=0; i<numPlayers; i++) {
            playerHands.add(deck.getPlayerHand(0));
        }
    }

    public void start() {

    }

}
