package net.charno.codingchallenge.data;

import net.charno.codingchallenge.data.model.InputStatus;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

public class InternalDataRepository implements Repository {
    private static Set<String> idStore = new HashSet<>();
    private static InputStatus status = new InputStatus();
    private static BufferedWriter writer;

    public boolean log(String input) {
        boolean logged = idStore.add(input);
        if (logged) {
            try {
                if (writer == null) writer = Files.newBufferedWriter(Paths.get("numbers.log"));
                writer.write(String.format("%s%n", input));
            } catch (IOException ioex) {
                ioex.printStackTrace();
            }
            status.incrementUniques();
        } else {
            status.incrementDuplicates();
        }
        return logged;
    }

    public String getStatus() {
        return String.format("Received %d unique numbers, %d duplicates. Unique total: %d",
                status.getUniques(), status.getDuplicates(), status.getTotalUniques());
    }

    public void resetStatus() {
        status.reset();
    }

    public void close() {
        try {
            writer.close();
        } catch (IOException ioex) {
            ioex.printStackTrace();
        }
    }
}
