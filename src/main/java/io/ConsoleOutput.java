package io;

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

    public String getOutputMessage() {
        return outputMessage;
    }

}
