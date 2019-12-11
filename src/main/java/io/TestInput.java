package io;

public class TestInput implements Input {

    private String testString;
    private int testInt;

    public void setTestString(String testString) {
        this.testString = testString;
    }

    public void setTestInt(int testInt) {
        this.testInt = testInt;
    }

    public String getInputString() {
        return testString;
    }

    public int getInputInt() {
        return testInt;
    }

}
