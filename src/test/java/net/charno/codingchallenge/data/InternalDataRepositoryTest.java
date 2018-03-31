package net.charno.codingchallenge.data;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class InternalDataRepositoryTest {

    InternalDataRepository internalDataRepository;

    @Before
    public void setup() {
        internalDataRepository = new InternalDataRepository();
    }

    @Test
    public void log_newId_idGetsLogged() {
        String input = "123456789";
        Assert.assertTrue(internalDataRepository.log(input));
    }

    @Test
    public void log_duplicateIds_idDoesNotGetLogged() {
        String input = "987654321";
        Assert.assertTrue(internalDataRepository.log(input));
        Assert.assertFalse(internalDataRepository.log(input));
    }

    @Test
    public void getStatus_initialStatus_defaultStats() {
        String initialMessage = "Received 0 unique numbers, 0 duplicates. Unique total: 0";
        Assert.assertEquals(initialMessage, internalDataRepository.getStatus());
    }
}
