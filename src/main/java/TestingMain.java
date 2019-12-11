import io.ConsoleInput;
import io.ConsoleOutput;
import sevens.Sevens;
import utils.Log;

public class TestingMain {

    public static void main(String[] args) {

        Log log = Log.getInstance();
        log.writeToLog("TestingMain", "Starting Program...");
        ConsoleOutput output = new ConsoleOutput();
        ConsoleInput input = new ConsoleInput();

        output.output("What game would you like to play? ");
        String choice = input.getInputString();
        int numPlayers;

        if (choice.equalsIgnoreCase("Sevens")) {
            output.output("How many players will there be? ");
            numPlayers = input.getInputInt();

            Sevens sevens = new Sevens(numPlayers);
            sevens.run();
        }
    }

}
