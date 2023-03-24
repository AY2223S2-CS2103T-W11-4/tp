package seedu.address.model.person;


import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;
public class MedicalConditionTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new MedicalCondition(null));
    }

    @Test
    public void constructor_invalidMedicalCondition_throwsIllegalArgumentException() {
        String invalidCond = "invalid medical condition";
        assertThrows(IllegalArgumentException.class, () -> new MedicalCondition(invalidCond));
    }

    @Test
    public void isValidMedicalCond() {
        // null age
        assertThrows(NullPointerException.class, () -> MedicalCondition.isValidCondition(null));

        // invalid ages
        assertFalse(MedicalCondition.isValidCondition(" ")); // spaces only
        assertFalse(MedicalCondition.isValidCondition("Asthma23455")); //Condition with random number behind

        // valid ages
        assertTrue(MedicalCondition.isValidCondition("Asthma")); //Any String
        assertTrue(MedicalCondition.isValidCondition("")); // empty string
    }
    
}
