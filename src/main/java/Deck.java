import java.util.*;

public class Deck {

    private String[] suits = {"C", "S", "H", "D"};
    private String[] numbers = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};
    private static ArrayList<ArrayList<String>> players = new ArrayList<>();
    private String[] deck = new String[52];
    private int numPlayers;

    public Deck(int numPlayers) {
        this.numPlayers = numPlayers;
        int i = 0;
        for (String suit : suits) {
            for (String number : numbers) {
                deck[i] = suit + number;
                i++;
            }
        }
        ArrayList<String> initialArrayList = new ArrayList<>();
        for (int j=0; j<numPlayers+1; j++) {
            players.add(initialArrayList);
        }
    }

//    public void deal() {
//        int currentPlayer = 0;
//        for (String card : deck) {
//            players.get(currentPlayer).add("test");
//            if (currentPlayer == numPlayers) {
//                currentPlayer = 0;
//            } else {
//                currentPlayer++;
//            }
//
//        }
//        System.out.println(players.get(0));
//        System.out.println(players.get(numPlayers-1));
//    }

    public void dealToPlayers() {
        // List<String> tempDeck = Arrays.asList(deck);
        List<String> tempDeck = new LinkedList<String>(Arrays.asList(deck));
        int currentPlayer = 0;
        while (tempDeck.size() > 0) {
            deal(tempDeck.get(0), currentPlayer);
            if (currentPlayer == numPlayers) {
                currentPlayer = 0;
            } else {
                currentPlayer++;
            }
            tempDeck.remove(0);
        }
    }

    public void deal(String card, int player) {
        players.get(player).add(card);
    }

    public void shuffle() {
        List<String> tempList = Arrays.asList(deck);
        Collections.shuffle(tempList);
        deck = (String[]) tempList.toArray();
    }

    public String[] getDeck() {
        return deck;
    }

    public int getDeckSize() {
        return deck.length;
    }

    public String getCard(int index) {
        return deck[index];
    }

    public int getCardsInHand(int index) {
        return players.get(index).size();
    }

    public static void main(String[] args) {
        Deck deck = new Deck(3);
        deck.shuffle();
        deck.dealToPlayers();
        System.out.println(players.get(0));
        System.out.println(players.get(1));
        System.out.println(players.get(2));
    }

}
