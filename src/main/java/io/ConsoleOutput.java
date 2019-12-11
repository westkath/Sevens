package io;

import java.util.ArrayList;

public class ConsoleOutput implements Output {

    private String outputMessage;

    public void output(String outputMessage) {
        this.outputMessage = outputMessage;
        System.out.println(outputMessage);
    }

    public void output() {
        this.outputMessage = "";
        System.out.println();
    }

    public void output(ArrayList<String> displayOutput) {
        for (String display : displayOutput){
            System.out.println(display);
        }
    }

    public String getOutputMessage() {
        return outputMessage;
    }

}
