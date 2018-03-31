package net.charno.codingchallenge.testclient;

public class StatusThread extends Thread {
    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(5000);
                System.out.printf("Sent %d IDs.%n", Client.i.get());
            } catch (InterruptedException iex) {
                iex.printStackTrace();
            }
        }
    }
}
