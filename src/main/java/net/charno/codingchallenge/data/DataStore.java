package net.charno.codingchallenge.data;

public class DataStore {
    public static Repository repository = new InternalDataRepository();

    public static boolean isInputValid(String inputString) {
        return (inputString.length() == 9 && inputString.chars().allMatch(Character::isDigit)) ? true : false;
    }
}
