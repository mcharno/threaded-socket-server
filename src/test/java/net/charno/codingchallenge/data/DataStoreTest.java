package net.charno.codingchallenge.data;

import org.junit.Assert;
import org.junit.Test;

public class DataStoreTest {

    @Test
    public void isValidInput_validId_returnTrue() {
        Assert.assertTrue(DataStore.isInputValid("123456789"));
        Assert.assertTrue(DataStore.isInputValid("000123456"));
    }

    @Test
    public void isValidInput_invalidIds_returnFalse() {
        Assert.assertFalse(DataStore.isInputValid("123"));
        Assert.assertFalse(DataStore.isInputValid("12345678X"));
        Assert.assertFalse(DataStore.isInputValid("!@#$%^&*()"));
        Assert.assertFalse(DataStore.isInputValid(""));
    }
}
