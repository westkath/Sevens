package sevens;

import io.ConsoleInput;
import io.ConsoleOutput;
import io.Input;
import io.Output;
import utils.ConsoleColors;
import utils.Deck;
import utils.Log;

import java.util.*;

public class Game {

    private Input userInput;
    private Input testInput;
    private Output output;
    private Log log;

    private int numPlayers;
    private ArrayList<LinkedList<String>> playerHands = new ArrayList<>();
    private Map<String, LinkedList<String>> suitPiles = new HashMap<>();
    private String separator = ConsoleColors.BLUE_BOLD + "=============================== " + ConsoleColors.BLUE_BOLD_BRIGHT
            + "[Sevens]" + ConsoleColors.BLUE_BOLD + " ===============================" + ConsoleColors.RESET;

    public Game(int numPlayers) {

        userInput = new ConsoleInput();
        output = new ConsoleOutput();

        if (numPlayers <= 52 && numPlayers >= 1)
            this.numPlayers = numPlayers;
        else {
            output.output(ConsoleColors.RED_BOLD_BRIGHT + numPlayers + ConsoleColors.RESET +
                    " is an invalid number of players to have! Exiting Game");
            throw new IllegalArgumentException(numPlayers + " is an invalid number of players to have! Exiting Game");
        }

        this.log = Log.getInstance();
        log.writeToLog("Game", "Starting Game...");
    }

    public void initialise() {
        Deck deck = new Deck(numPlayers);
        deck.shuffle();
        deck.dealAllCards();

        for (int i=0; i<numPlayers; i++) {
            playerHands.add(deck.getPlayerHand(i));
        }

        log.writeToLog("Game", "Initialising Game...");
    }

    public void playGame() {
        log.writeToLog("Game", "Starting the Game...");

        int currentPlayer = whoHasFirstSeven();

        showSeparator();
        output.output("Welcome to " + ConsoleColors.PURPLE_BOLD_BRIGHT + "Sevens!" + ConsoleColors.RESET);
        showSeparator();
        output.output();

        showSeparator();
        String playedCard = runFirstTurn(currentPlayer, playerHands.get(currentPlayer));
        showSeparator();
        output.output();

        removePlayerCard(playedCard, currentPlayer);
        currentPlayer = getNextPlayer(currentPlayer);

        while (!isGameOver()) {
            output.output(separator);
            String card = runTurn(currentPlayer, playerHands.get(currentPlayer));

            if (card.equals("skip")) {
                output.output(ConsoleColors.PURPLE_BOLD_BRIGHT + "Player " + (currentPlayer+1) +
                        ConsoleColors.RESET + " has skipped a turn!");
            } else {
                playerHands.get(currentPlayer).remove(card);
            }

            currentPlayer = getNextPlayer(currentPlayer);
            outputPiles();
            showSeparator();
            output.output();
        }

        showSeparator();
        output.output("Game Over! Player " + ConsoleColors.PURPLE_BOLD_BRIGHT + (currentPlayer) +
                ConsoleColors.RESET + " has won the game!");
        showSeparator();

        log.writeToLog("Game", "Game Ended...");
    }

    private String runFirstTurn(int firstPlayer, LinkedList<String> firstHand) {
        boolean hasPlayed = false;

        output.output(ConsoleColors.PURPLE_BOLD_BRIGHT + "Player " + (firstPlayer+1) + ": " + ConsoleColors.RESET
                + "Play a Seven to Start the Game!");
        output.output(ConsoleColors.PURPLE_BOLD_BRIGHT + "Current Hand: " + ConsoleColors.RESET + firstHand);

        String card = userInput.getInputString().toUpperCase();
        String suit = String.valueOf(card.charAt(0)).toUpperCase();

        while (!hasPlayed) {
            if (firstHand.contains(card) && (card.equals("C7") || card.equals("D7") || card.equals("S7") || card.equals("H7"))) {
                hasPlayed = true;
                playCard(card, firstPlayer);
                addSevenToPile(card, getSuitByString(suit));
            } else {
                output.output(ConsoleColors.PURPLE_BOLD_BRIGHT + "Player " + (firstPlayer+1) + ": " + ConsoleColors.RESET +
                        "Play a Seven to " + "Start the Game - " + ConsoleColors.RED_BOLD_BRIGHT + card + ConsoleColors.RESET
                        + " is not a seven...");
                output.output(ConsoleColors.PURPLE_BOLD_BRIGHT + "Current Hand: " + ConsoleColors.RESET + firstHand);
                card = userInput.getInputString();
            }
        }

        outputPiles();
        return card;
    }

    private String runTurn(int player, LinkedList<String> hand) {
        Scanner input = new Scanner(System.in);
        boolean hasPlayed = false;

        output.output(ConsoleColors.PURPLE_BOLD_BRIGHT + "Player " + (player+1) + ": " + ConsoleColors.RESET +
                "Play a Card to Play the Game! Or Say Skip to Miss a Turn!");
        output.output("Current Hand: " + hand);
        String card = userInput.getInputString();

        while (!isCardValid(card)) {
            output.output(ConsoleColors.PURPLE_BOLD_BRIGHT + "Player " + (player+1) + ": " + ConsoleColors.RESET +
                    ConsoleColors.RED_BOLD_BRIGHT + card + ConsoleColors.RESET + " is not a valid card. Enter one!");
            output.output("Current Hand: " + hand);
            card = userInput.getInputString();
        }

        String suitChar = String.valueOf(card.charAt(0)).toUpperCase();
        String number = String.valueOf(card.charAt(1));

        while (!hasPlayed) {
            if (card.equalsIgnoreCase("skip")) {
                return card;
            } else if (!isCardValid(card)) {
                output.output(ConsoleColors.PURPLE_BOLD_BRIGHT + "Player " + (player+1) + ": " + ConsoleColors.RESET +
                        ConsoleColors.RED_BOLD_BRIGHT + card + ConsoleColors.RESET + " is not a valid card. Enter one!");
                output.output("Current Hand: " + hand);
                card = input.nextLine();
            } else if (hand.contains(card) && (isValidMove(card, hand))) {
                hasPlayed = true;
                playCard(card, player);
                if (number.equals("7")) {
                    addSevenToPile(card, getSuitByString(suitChar));
                } else {
                    addCardToPile(card, getSuitByString(suitChar));
                }
            } else {
                output.output(ConsoleColors.PURPLE_BOLD_BRIGHT + "Player " + (player+1) + ": " +
                        ConsoleColors.RED_BOLD_BRIGHT + card + ConsoleColors.RESET + " cannot be played. " +
                        "Play a Card to Play the Game! Or Say Skip to Miss a Turn!");
                output.output("Current Hand: " + hand);
                card = userInput.getInputString().toUpperCase();
            }
        }

        return card;
    }

    public int whoHasFirstSeven() {
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

    private boolean isValidMove(String card, LinkedList<String> hand) {
        boolean validMove = false;
        String suitChar = String.valueOf(card.charAt(0)).toUpperCase();
        String number = String.valueOf(card.charAt(1));

        String suit = getSuitByString(suitChar.toUpperCase());

        if (number.equals("7")) {
            validMove = true;
        } else if (suitPiles.containsKey(suit)){
            validMove = checkAboveAndBelow(suit, stringValueToInt(number));
        }

        return validMove;
    }

    public boolean checkAboveAndBelow(String suit, int number) {
        boolean isValid = false;

        LinkedList<String> tempPile = suitPiles.get(suit);
        int upper = stringValueToInt(String.valueOf(tempPile.getLast().charAt(1))) + 1;
        int lower = stringValueToInt(String.valueOf(tempPile.getFirst().charAt(1))) - 1;

        if (number == upper || number == lower) {
            isValid = true;
        }

        return isValid;
    }

    public void addSevenToPile(String card, String suit) {
        if (suitPiles.get(suit) != null) {
            suitPiles.get(suit).add(card);
        } else {
            LinkedList<String> startList = new LinkedList<>();
            startList.add(card);
            suitPiles.put(suit, startList);
        }
    }

    public void addCardToPile(String card, String suit) {
        String number = String.valueOf(card.charAt(1));
        int upper = stringValueToInt(String.valueOf(suitPiles.get(suit).getLast().charAt(1))) + 1;
        int entryCard = stringValueToInt(number);

        if (entryCard == upper) {
            suitPiles.get(suit).addLast(card);
        } else {
            suitPiles.get(suit).addFirst(card);
        }
    }

    public int getNextPlayer(int currentPlayer) {
        if (currentPlayer == numPlayers - 1) {
            currentPlayer = 0;
        } else {
            currentPlayer++;
        }
        return currentPlayer;
    }

    public String getSuitByString(String shortSuit) {
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

    public int stringValueToInt(String input) {
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

    public boolean isGameOver() {
        boolean isOver = false;
        for (int i=0; i<numPlayers; i++) {
            if (playerHands.get(i).size() == 0) {
                isOver = true;
            }
        }
        return isOver;
    }

    public void outputPiles() {
        output.output("\n" + ConsoleColors.PURPLE_BOLD_BRIGHT + "Current Piles:" + ConsoleColors.RESET);

        if (suitPiles.get("Hearts") != null) {
            output.output(ConsoleColors.RED_BOLD_BRIGHT + "Hearts: " + ConsoleColors.RESET + suitPiles.get("Hearts"));
        }
        if (suitPiles.get("Clubs") != null) {
            output.output(ConsoleColors.GREEN_BOLD_BRIGHT + "Clubs: " + ConsoleColors.RESET + suitPiles.get("Clubs"));
        }
        if (suitPiles.get("Diamonds") != null) {
            output.output(ConsoleColors.CYAN_BOLD_BRIGHT + "Diamonds: " + ConsoleColors.RESET + suitPiles.get("Diamonds"));
        }
        if (suitPiles.get("Spades") != null) {
            output.output(ConsoleColors.YELLOW_BOLD_BRIGHT + "Spades: " + ConsoleColors.RESET + suitPiles.get("Spades"));
        }
    }

    public LinkedList<String> getPlayerHand(int player) {
        return playerHands.get(player);
    }

    public void playCard(String card, int player) {
        output.output(ConsoleColors.PURPLE_BOLD_BRIGHT + "Card Played: " + ConsoleColors.RESET + card);
        removePlayerCard(card, player);
        output.output(ConsoleColors.PURPLE_BOLD_BRIGHT + "New Hand: " + ConsoleColors.RESET + getPlayerHand(player).toString());
    }

    public void removePlayerCard(String card, int player) {
        playerHands.get(player).remove(card);
    }

    public LinkedList<String> getSuitPile(String suit) {
        return suitPiles.get(suit);
    }

    public void showSeparator() {
        output.output(separator);
    }

    public boolean isCardValid(String card) {
        boolean isValid = true;

        if (card.length() < 2) {
            isValid = false;
        } else if (card.equalsIgnoreCase("skip")) {

        } else {
            String suit = String.valueOf(card.charAt(0)).toUpperCase();
            String number = String.valueOf(card.charAt(1));
            String extra = card.substring(2);

            if (!(suit.equals("C") || suit.equals("D") || suit.equals("H") || suit.equals("S")))
                isValid = false;
            else if (!(number.equals("A") || number.equals("2") || number.equals("3") || number.equals("4") ||
                    number.equals("5") || number.equals("6") || number.equals("7") || number.equals("8") ||
                    number.equals("9") || number.equals("X") || number.equals("Q") || number.equals("K")))
                isValid = false;
            else if (!extra.equals(""))
                isValid = false;
        }

        return isValid;
    }

    public void setPlayerHand(int player, LinkedList<String> hand) {
        playerHands.set(player, hand);
    }

    public void setUserInput(Input inputType){
        userInput = inputType;
    }

    public void setUserOutput(Output outputType){
        output = outputType;
    }

    public void setComputerInput(Input inputType){
        testInput = inputType;
    }

    public Output getOutput() {
        return output;
    }

}
