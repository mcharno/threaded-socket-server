package net.charno.codingchallenge.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public abstract class ThreadedSocketServer {
    private int port;

    public ThreadedSocketServer(int port) {
        this.port = port;
    }

    public void listen() {
        ExecutorService tasks = Executors.newFixedThreadPool(5);
        try (ServerSocket listener = new ServerSocket(port)) {
            Socket socket;
            int connection = 0;
            while (true) {
                socket = listener.accept();
                tasks.execute(new ConnectionHandler(socket, ++connection));
                System.out.printf("%d socket connection%s%n", connection, connection > 1 ? "s" : "");
            }
        } catch (IOException ioex) {
            System.err.printf("IOException: %s%n", ioex);
            ioex.printStackTrace();
        }
    }

    protected abstract void handleConnection(Socket connection, int node) throws IOException;

    private class ConnectionHandler implements Runnable {

        private Socket socket;
        private int node;

        public ConnectionHandler(Socket socket, int node) {
            this.socket = socket;
            this.node = node;
        }

        public void run() {
            try (Socket connection = this.socket) {
                handleConnection(connection, node);
            } catch (IOException ioex) {
                System.err.printf("IOException: %s%n", ioex);
                ioex.printStackTrace();
            }
        }
    }
}
