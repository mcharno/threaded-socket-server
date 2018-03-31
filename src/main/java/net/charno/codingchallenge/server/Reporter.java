package net.charno.codingchallenge.server;

import net.charno.codingchallenge.data.DataStore;

public class Reporter extends Thread {

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(10000);
                System.out.println(DataStore.repository.getStatus());
                DataStore.repository.resetStatus();
            } catch (InterruptedException iex) {
                iex.printStackTrace();
            }
        }
    }
}
