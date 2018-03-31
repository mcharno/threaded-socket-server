package net.charno.codingchallenge.data;

public interface Repository {
    boolean log(String input);
    String getStatus();
    void resetStatus();
    void close();
}
