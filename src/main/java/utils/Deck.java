package utils;

import java.util.*;

public class Deck {

    private String[] suits = {"C", "S", "H", "D"};
    private String[] numbers = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "X", "J", "Q", "K"};
    private ArrayList<LinkedList<String>> players = new ArrayList<>();
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

        for (int j=0; j<=numPlayers; j++) {
            LinkedList<String> initialLinkedList = new LinkedList<>();
            players.add(initialLinkedList);
        }
    }

    public void dealSetNumberCards(int numCards) throws NotEnoughCardsException {
        if (numCards * numPlayers > 52) {
            throw new NotEnoughCardsException("Not enough cards to deal " + numCards + " cards per person!");
        } else {
            List<String> tempDeck = new LinkedList<String>(Arrays.asList(deck));
            int currentPlayer = 0;
            int cardsPerPerson = numCards;
            while (tempDeck.size() > 0 && getNumCardsInHand(numPlayers - 1) < cardsPerPerson) {
                deal(tempDeck.get(0), currentPlayer);
                if (currentPlayer == numPlayers - 1) {
                    currentPlayer = 0;
                } else {
                    currentPlayer++;
                }
                tempDeck.remove(0);
            }
        }
    }

    public void dealAllCards() {
        List<String> tempDeck = new LinkedList<String>(Arrays.asList(deck));
        int currentPlayer = 0;
        while (tempDeck.size() > 0) {
            deal(tempDeck.get(0), currentPlayer);
            if (currentPlayer == numPlayers-1) {
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

    public LinkedList<String> getPlayerHand(int index) {
        LinkedList<String> hand = players.get(index);
        return players.get(index);
    }

    public int getNumCardsInHand(int player) {
        return players.get(player).size();
    }

}
