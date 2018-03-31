package net.charno.codingchallenge.data.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class InputStatusTest {

    InputStatus inputStatus;

    @Before
    public void setup() {
        inputStatus = new InputStatus();
    }

    @Test
    public void getMethods_initialValues_returnZeros() {
        Assert.assertEquals(0, inputStatus.getUniques());
        Assert.assertEquals(0, inputStatus.getDuplicates());
        Assert.assertEquals(0, inputStatus.getTotalUniques());
    }

    @Test
    public void incrementUniques_incrementValue_bothUniquesAndTotalUniquesGetIncremented() {
        inputStatus.incrementUniques();
        Assert.assertEquals(1, inputStatus.getUniques());
        Assert.assertEquals(1, inputStatus.getTotalUniques());
    }

    @Test
    public void incrementDuplicates_incrementValue_returnIncrementedValue() {
        inputStatus.incrementDuplicates();
        Assert.assertEquals(1, inputStatus.getDuplicates());
    }

    @Test
    public void reset_callReset_nonTotalValuesReset() {
        inputStatus.incrementUniques();
        inputStatus.incrementDuplicates();
        Assert.assertEquals(1, inputStatus.getUniques());
        Assert.assertEquals(1, inputStatus.getDuplicates());

        inputStatus.reset();
        Assert.assertEquals(0, inputStatus.getUniques());
        Assert.assertEquals(0, inputStatus.getDuplicates());
        Assert.assertEquals(1, inputStatus.getTotalUniques());
    }
}
