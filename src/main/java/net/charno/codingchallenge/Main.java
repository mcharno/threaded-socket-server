package net.charno.codingchallenge;

import net.charno.codingchallenge.server.Reporter;
import net.charno.codingchallenge.server.SocketServer;

public class Main {

    public static void main(String[] args) {
        int port = 4000;
        try {
            port = Integer.parseInt(args[0]);
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException ex) {
            System.out.printf("No valid port provided so using %d.%n" +
                    "\tIf you want to use a different port add a command%n" +
                    "\tline argument that is an integer above 1023.%n%n", port);
        }

        System.out.println("Starting up reporter ....");
        new Reporter().start();
        System.out.println("Starting up server ....");
        new SocketServer(port).listen();
    }
}