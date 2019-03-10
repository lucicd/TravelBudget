package lucicd.travelbudget.model;

public class Helpers {
    public static String nullWhenEmpty(String input) {
        if (input == null || input.trim().length() == 0) {
            return null;
        } else {
            return input.trim();
        }
    }
}
