package net.charno.codingchallenge.data;

import net.charno.codingchallenge.data.model.InputStatus;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.BitSet;

public class InternalDataRepository implements Repository {
    private static final String OUTPUT_FILE = "numbers.log";
    private static BitSet idStore = new BitSet(); // super powered Set to just store binary values at each "id" location
    private static InputStatus status = new InputStatus();
    private static BufferedWriter writer;

    public void log(String input) {
        if (!idStore.get(Integer.valueOf(input))) {
        idStore.set(Integer.valueOf(input));
            try {
                // lazily check and create the writer here rather than in a constructor, and keep open indefinitely
                if (writer == null) writer = Files.newBufferedWriter(Paths.get(OUTPUT_FILE));
                writer.write(String.format("%s%n", input));
            } catch (IOException ioex) {
                ioex.printStackTrace();
            }
            status.incrementUniques();
        } else {
            status.incrementDuplicates();
        }
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
            // only close the writer at the very end to keep IO optimal, at the risk of not closing it formally
            writer.close();
        } catch (IOException ioex) {
            ioex.printStackTrace();
        }
    }
}
