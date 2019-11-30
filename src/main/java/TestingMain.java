import sevens.Sevens;

import java.util.Scanner;

public class TestingMain {

    public static void main(String[] args) {
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
