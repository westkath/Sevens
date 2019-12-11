package io;

import java.util.Scanner;

public class ConsoleInput implements Input {

    Scanner input = new Scanner(System.in);

    public String getInputString() {
        String result = input.nextLine();
        return result;
    }

    public int getInputInt() {
        int result = -1;
        do {
            try {
                result = input.nextInt();
            } catch (NumberFormatException exception) {
                System.out.println("Please enter a integer.");
            }
        } while (result < 0);
        return result;
    }

}
