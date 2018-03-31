package net.charno.codingchallenge.data;

public interface Repository {
    void log(String input);
    String getStatus();
    void resetStatus();
    void close();
}
