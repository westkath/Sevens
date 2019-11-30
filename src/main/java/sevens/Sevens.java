package sevens;

public class Sevens {

    private Game game;

    public Sevens(int numPlayers) {
        game = new Game(numPlayers);
    }

    public void run() {
        game.initialise();
        game.playGame();
    }

}
