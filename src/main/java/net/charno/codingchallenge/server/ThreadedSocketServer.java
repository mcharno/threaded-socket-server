package net.charno.codingchallenge.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public abstract class ThreadedSocketServer {
    // variable to control concurrent connections and threads
    private static final int CONCURRENT_CONNECTIONS = 5;
    protected static int connectionCounter = 0;
    private int port;

    public ThreadedSocketServer(int port) {
        this.port = port;
    }

    public void listen() {
        // set up thread pool & queue to limit connections/threads
        ExecutorService tasks = Executors.newFixedThreadPool(CONCURRENT_CONNECTIONS);
        BlockingQueue queue = new ArrayBlockingQueue(CONCURRENT_CONNECTIONS);
        try (ServerSocket listener = new ServerSocket(port)) {
            Socket socket;
            while (true) {
                if (queue.size() < CONCURRENT_CONNECTIONS) {
                    // accept connections, update queue and output
                    socket = listener.accept();
                    queue.add(++connectionCounter); // increment the counter before adding to the queue
                    tasks.execute(new ConnectionHandler(queue, socket));
                    outputCurrentConnections(queue);
                }
            }
        } catch (IOException ioex) {
            System.err.printf("IOException: %s%n", ioex);
            ioex.printStackTrace();
        }
    }

    private void outputCurrentConnections(BlockingQueue queue) {
        System.out.printf("%d current connection%s%n", queue.size(), queue.size() != 1 ? "s" : "");
    }

    // abstract method to be implemented with desired logic and behaviors for each connection
    protected abstract void handleConnection(Socket connection) throws IOException;

    private class ConnectionHandler implements Runnable {

        private BlockingQueue queue;
        private Socket socket;

        public ConnectionHandler(BlockingQueue queue, Socket socket) {
            this.queue = queue;
            this.socket = socket;
        }

        public void run() {
            try (Socket connection = this.socket) {
                handleConnection(connection);
                queue.remove(connectionCounter--); // decrement the counter after adding to the queue
                outputCurrentConnections(queue);
            } catch (IOException ioex) {
                System.err.printf("IOException: %s%n", ioex);
                ioex.printStackTrace();
            }
        }
    }
}
