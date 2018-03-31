package net.charno.codingchallenge.testclient;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicInteger;

public class Client implements Runnable {

    private final String host;
    private final int port;
    private Socket socket;
    private DataOutputStream output;
    public static AtomicInteger i = new AtomicInteger(1);

    public Client(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void run() {
        try {
            socket = new Socket(host, port);
            output = new DataOutputStream(socket.getOutputStream());

            while (true) {
                long randomNumber = (long) Math.floor(Math.random() * 900000000L) + 100000000L;
                output.writeUTF(String.valueOf(randomNumber));

                if (i.incrementAndGet() % 20000000 == 0) {
                    System.out.println("TERMINATING!!! ");
                    output.writeUTF("terminate");
                } else if (i.incrementAndGet() % 5000000 == 0) {
                    System.out.println("INVALID ID!!!");
                    output.writeUTF("testing");
                } else {
                    i.incrementAndGet();
                }
            }
        } catch (IOException ioex) {
            closeConnections();
        }
    }

    private void closeConnections() {
        try {
            output.close();
            socket.close();
        } catch (IOException ioex) {
            ioex.printStackTrace();
        }
    }
}
