package io;

import java.util.ArrayList;

public interface Output {
    public void output(String outputMessage);
    public void output();
    public void output(ArrayList<String> value);
    public String getOutputMessage();
}
