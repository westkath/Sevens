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



    @Override
    protected void tearDown() {
        System.out.println("Shutting down Game Test Case!");
        game = null;
    }

}
