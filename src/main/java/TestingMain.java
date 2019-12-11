import sevens.Sevens;
import utils.Log;

import java.util.Scanner;

public class TestingMain {

    public static void main(String[] args) {

        Log log = Log.getInstance();
        log.writeToLog("TestingMain", "Starting Program...");

        Scanner in = new Scanner(System.in);

        System.out.println("What game would you like to play? ");
        String choice = in.nextLine();
        int numPlayers;

        if (choice.equalsIgnoreCase("Sevens")) {
            System.out.println("How many players will there be? ");
            numPlayers = in.nextInt();

            Sevens sevens = new Sevens(numPlayers);
            sevens.run();
        }
    }

}
