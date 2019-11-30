import java.util.*;

public class Game {

    private int numPlayers;
    private ArrayList<LinkedList<String>> playerHands = new ArrayList<>();
    private Map<String, LinkedList<String>> suitPiles = new HashMap<>();

    public Game(int numPlayers) {
        this.numPlayers = numPlayers;
        initialise();
    }

    public void initialise() {
        Deck deck = new Deck(numPlayers);
        deck.shuffle();
        deck.dealAllCards();

        for (int i=0; i<numPlayers; i++) {
            playerHands.add(deck.getPlayerHand(i));
        }
    }

    public void playGame() {
        // Prompt Player to Play the Game
        int currentPlayer = whoHasFirstSeven();

        String playedCard = runFirstTurn(currentPlayer, playerHands.get(currentPlayer)); // string formatting here
        System.out.println("Current Piles:");
        System.out.println(suitPiles.toString());

        playerHands.get(currentPlayer).remove(playedCard);

        currentPlayer = getNextPlayer(currentPlayer);

        // Run the Game for Each Turn
        while (!isGameOver()) {
            String card = runTurn(currentPlayer, playerHands.get(currentPlayer));

            if (card.equals("skip")) {
                System.out.println("Player " + (currentPlayer+1) + " has skipped a turn!");
            } else {
                playerHands.get(currentPlayer).remove(card);
            }

            currentPlayer = getNextPlayer(currentPlayer);

            System.out.println("Current Piles:");
            System.out.println(suitPiles.toString());
        }

        System.out.println("Game Over! Player " + (currentPlayer) + " has won the game!");
    }

    private int whoHasFirstSeven() {
        boolean sevenFound = false;
        int player = 0;
        while (!sevenFound) {
            if (playerHands.get(player).contains("C7") ||
                    playerHands.get(player).contains("H7") ||
                    playerHands.get(player).contains("D7") ||
                    playerHands.get(player).contains("S7")) {
                sevenFound = true;
            } else {
                player++;
            }
        }
        return player;
    }

    private boolean isGameOver() {
        boolean isOver = false;
        for (int i=0; i<numPlayers; i++) {
            if (playerHands.get(i).size() == 0) {
                isOver = true;
            }
        }
        return isOver;
    }

    private String runFirstTurn(int firstPlayer, LinkedList<String> firstHand) {
        Scanner input = new Scanner(System.in);
        boolean hasPlayed = false;

        System.out.println("Player " + (firstPlayer+1) + ": Play a Seven to Start the Game!");
        System.out.println("Current Hand: " + firstHand);
        String card = input.nextLine();

        while (!hasPlayed) {
            if (firstHand.contains(card) && (
                    card.equals("C7") || card.equals("D7") ||
                    card.equals("S7") || card.equals("H7"))) {
                System.out.println("Card Played: " + card);
                hasPlayed = true;
                firstHand.remove(card);
                addSevenToPile(card, getSuitByString(String.valueOf(card.charAt(0))));
            } else {
                System.out.println("Player " + (firstPlayer+1) + ": Play a Seven to Start the Game!");
                System.out.println("Current Hand: " + firstHand);
                card = input.nextLine();
            }
        }

        return card;
    }

    private String runTurn(int player, LinkedList<String> hand) {
        Scanner input = new Scanner(System.in);
        boolean hasPlayed = false;

        System.out.println("Player " + (player+1) + ": Play a Card to Play the Game! Or Say Skip to Miss a Turn!");
        System.out.println("Current Hand: " + hand);
        String card = input.nextLine();
        String suitChar = String.valueOf(card.charAt(0));
        String number = String.valueOf(card.charAt(1));

        while (!hasPlayed) {
            if (card.equalsIgnoreCase("skip")) {
                return card;
            } else if (hand.contains(card) && (isValidMove(card, hand))) {
                System.out.println("Card Played: " + card);
                hasPlayed = true;
                hand.remove(card);
                if (number.equals("7")) {
                    addSevenToPile(card, getSuitByString(suitChar));
                } else {
                    addCardToPile(card, getSuitByString(suitChar));
                }
            } else {
                System.out.println("Player " + (player+1) + ": Play a Card to Play the Game! Or Say Skip to Miss a Turn!");
                System.out.println("Current Hand: " + hand);
                card = input.nextLine();
            }
        }

        return card;
    }

    private boolean isValidMove(String card, LinkedList<String> hand) {
        boolean validMove = false;
        String suitChar = String.valueOf(card.charAt(0));
        String number = String.valueOf(card.charAt(1));

        String suit = getSuitByString(suitChar);

        if (number.equals("7")) {
            validMove = true;
        } else if (suitPiles.containsKey(suit)){
            validMove = checkAboveAndBelow(suit, stringValueToInt(number)); // changed here test X functionality before meaningful messages todo
        }

        return validMove;
    }

    private boolean checkAboveAndBelow(String suit, int number) {
        boolean isValid = false;

        LinkedList<String> tempPile = suitPiles.get(suit);
        int upper = stringValueToInt(String.valueOf(tempPile.getLast().charAt(1))) + 1;
        int lower = stringValueToInt(String.valueOf(tempPile.getFirst().charAt(1))) - 1;

        if (number == upper || number == lower) {
            isValid = true;
        }

        return isValid;
    }

    private void addSevenToPile(String card, String suit) {
        if (suitPiles.get(suit) != null) {
            suitPiles.get(suit).add(card);
        } else {
            LinkedList<String> startList = new LinkedList<>();
            startList.add(card);
            suitPiles.put(suit, startList);
        }
    }

    private void addCardToPile(String card, String suit) {
        String number = String.valueOf(card.charAt(1));
        int upper = stringValueToInt(String.valueOf(suitPiles.get(suit).getLast().charAt(1))) + 1;
        int entryCard = stringValueToInt(number);

        if (entryCard == upper) {
            suitPiles.get(suit).addLast(card);
        } else {
            suitPiles.get(suit).addFirst(card);
        }
    }

    private int getNextPlayer(int currentPlayer) {
        if (currentPlayer == numPlayers - 1) {
            currentPlayer = 0;
        } else {
            currentPlayer++;
        }
        return currentPlayer;
    }

    private String getSuitByString(String shortSuit) {
        String suit = "";

        switch (shortSuit) {
            case "C":
                suit = "Clubs";
                break;
            case "D":
                suit = "Diamonds";
                break;
            case "H":
                suit = "Hearts";
                break;
            case "S":
                suit = "Spades";
                break;
        }

        return suit;
    }

    private int stringValueToInt(String input) {
        switch(input) {
            case "A":
                return 1;
            case "X":
                return 10;
            case "J":
                return 11;
            case "Q":
                return 12;
            case "K":
                return 13;
            default:
                return Integer.parseInt(input);
        }
    }

}
