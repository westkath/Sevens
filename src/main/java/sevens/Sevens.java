package sevens;

import utils.Log;

public class Sevens {

    private Game game;

    public Sevens(int numPlayers) {
        game = new Game(numPlayers);
    }

    public void run() {
        game.initialise();
        game.playGame();

        Log log = Log.getInstance();
        log.writeToLog("Sevens", "Running Game...");
    }

}
