package net.charno.codingchallenge.testclient;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

public class SimpleClient {
    public static void main(String[] args) throws Exception {
        new StatusThread().start();
        Executor tasks = Executors.newFixedThreadPool(6);
        IntStream.rangeClosed(1, 7).forEach(i -> tasks.execute(new Client("127.0.0.1", 4000)));
    }
}
