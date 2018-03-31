package net.charno.codingchallenge.data;

public class DataStore {
    // generic Repository which can be swapped out for a proper store later
    public static Repository repository = new InternalDataRepository();

    // data validation check specific to the data store
    public static boolean isInputValid(String inputString) {
        return (inputString.length() == 9 && inputString.chars().allMatch(Character::isDigit)) ? true : false;
    }
}
