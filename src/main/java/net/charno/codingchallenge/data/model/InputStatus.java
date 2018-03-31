package net.charno.codingchallenge.data.model;

import java.util.concurrent.atomic.AtomicLong;

public class InputStatus {
    private AtomicLong uniques = new AtomicLong();
    private AtomicLong totalUniques = new AtomicLong();
    private AtomicLong duplicates = new AtomicLong();

    public void reset() {
        uniques.set(0);
        duplicates.set(0);
    }

    public void incrementUniques() {
        uniques.incrementAndGet();
        totalUniques.incrementAndGet();
    }

    public void incrementDuplicates() {
        duplicates.incrementAndGet();
    }

    public long getUniques() {
        return uniques.get();
    }

    public long getTotalUniques() {
        return totalUniques.get();
    }

    public long getDuplicates() {
        return duplicates.get();
    }
}
