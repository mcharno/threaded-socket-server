package net.charno.codingchallenge.server;

import net.charno.codingchallenge.data.DataStore;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class SocketServer extends ThreadedSocketServer {
    private static boolean isTerminated = false;

    public SocketServer(int port) {
        super(port);
    }

    @Override
    public void handleConnection(Socket socket) throws IOException {
        DataInputStream inputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
        String inputString;
        while(!isTerminated) {
            inputString = String.valueOf(inputStream.readUTF());
            if ("terminate".equals(inputString)) {
                isTerminated = true;
            } else if (DataStore.isInputValid(inputString)) {
                DataStore.repository.log(inputString);
            } else {
                inputStream.close();
                break;
            }
        }
        if (isTerminated) {
            // try and close as many connections as possible before a hard shutdown
            System.out.printf("Connection %d: Hasta la vista, baby...%n", connectionCounter--);
            closeAllConnections(socket, inputStream);
            System.exit(0);
        }
    }

    private void closeAllConnections(Socket socket, DataInputStream inputStream) throws IOException {
        inputStream.close();
        socket.close();
        DataStore.repository.close();
    }
}
