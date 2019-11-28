import java.util.Random;
import java.util.Scanner;

class RockPaperScissor {

    static String generateRequest(String[] weapons){
        String display = "Please select";
        /*for (int i=0;i<weapons.length;i++){*/
        int i=0;
        for (String weapon: weapons){
            i++;
            display += " " + i + " " + weapon;
        };
        return display;
    }

    static int requestPlay(String[] weapons)
    {
        String request;
        Scanner userInput = new Scanner(System.in);
        request = generateRequest(weapons);
        System.out.println(request);

        int userWeapon = userInput.nextInt()-1;

        return userWeapon;
    }

    public static void main(String[ ] args) {
        RockPaperScissor rockPaperScissor = new RockPaperScissor();
        rockPaperScissor.run();
    }

    public static String determineWinner(String[] weaponList, int userWeapon, int computerWeapon)
    {
        String winner;
        if (userWeapon == computerWeapon)
        {
            winner = "Draw both selected " + weaponList[computerWeapon];
        }
        else if ((userWeapon + 1) % 3 == computerWeapon)
        {
            winner = "You win and beat the computer's " + weaponList[computerWeapon];
        }
        else if ((computerWeapon + 1) % 3 == userWeapon)
        {
            winner = "Computer wins with " + weaponList[computerWeapon];
        }
        else
        {
            winner = "Please select 1. Rock, 2. Scissors or 3. Paper";
        }

        return winner;

    }

    private static void displayWinner(String winner)
    {
        System.out.println(winner);
    }

    public void run(){
        int userWeapon;
		    /*BufferedReader userInput =
		    new BufferedReader( new InputStreamReader(System.in));*/


        Random rand = new Random();
        int computerWeapon;
        computerWeapon = rand.nextInt(3);
        String winner;
        //Final declares
        final String[] weaponList = {"Rock","Scissors","Paper"};
        do{
            userWeapon = requestPlay(weaponList);

            winner = determineWinner(weaponList, userWeapon, computerWeapon);
            displayWinner(winner);
        } while (userWeapon< 3);
    }
}