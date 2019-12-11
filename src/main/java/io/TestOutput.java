package io;

import java.util.ArrayList;

public class TestOutput implements Output {

    private ArrayList<String> testOutputs;
    private String outputMessage;

    public void setTestOutputs(ArrayList<String> testOutputs) {
        this.testOutputs = testOutputs;
    }

    public void output(String message) {
        outputMessage = message;
        testOutputs.add(message);
    }

    public void output() {
        outputMessage = "";
        System.out.println();
    }

    public void output(ArrayList<String> displayOutput){
        for (String display : displayOutput){
            testOutputs.add(display);
        }
    }

    public void clear() {
        testOutputs.clear();
    }

    public ArrayList<String> getTestOutputs () {
        return this.testOutputs;
    }

    public String getOutputMessage() {
        return outputMessage;
    }

    public String getTestOutput(int index) {
        return testOutputs.get(index);
    }

}
