package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * To record the patient MedicalCondition
 */
public class MedicalCondition {
    public static final String MESSAGE_CONSTRAINTS =
            "Person's medical condition should be in String";
    private String value;
    /**
     * Represents a Patient's medical condition in the address book.
     */
    public MedicalCondition(String value) {
        requireNonNull(value);
        checkArgument(isValidCondition(value), MESSAGE_CONSTRAINTS);
        this.value = value;
    }

    /**
     * @param cond the medical condition of the patient
     * @return a boolean type, true if is a String obj and non-null, false otherwise
     */
    public static boolean isValidCondition(String cond) {
        if (cond instanceof String) {
            return true;
        }
        requireNonNull(cond);
        return false;
    }
    @Override
    public String toString() {
        return value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MedicalCondition // instanceof handles nulls
                && value.equals(((MedicalCondition) other).value)); // state check
    }

}
