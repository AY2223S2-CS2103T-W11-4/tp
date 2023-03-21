package seedu.address.model.person;
@SuppressWarnings("checkstyle:AbbreviationAsWordInName")
public class NRIC {

    public static final String MESSAGE_CONSTRAINTS =
            "Person's age should be in this format SXXXXXXXA";
    public final String number;
    public NRIC(String number) {
        this.number = number;
    }
    public static boolean isValidNumber(String number) {
        if (number.length() == 9 && ( number.indexOf(0) == 'S' ||number.indexOf(0) == 'T')) {
            return true;
        }
        return false;
    }
    public String getNumber() {
        return this.number;
    }
    @Override
    public String toString() {
        return "NRIC: " + number;
    }
}
