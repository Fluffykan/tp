package seedu.address.model.volunteer;

import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import seedu.address.model.exceptions.DuplicateAssignException;
import seedu.address.model.exceptions.VolunteerDeleteMissingDateException;
import seedu.address.model.exceptions.VolunteerDuplicateDateException;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
public class VolunteerDatesTest {
    private VolunteerDates volunteerDates;
    private LocalDate defaultDate = LocalDate.parse("2022-01-15");

    private String generateStringProperty(String... dates) {
        if (dates.length == 0) {
            return "StringProperty [value: ]";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("StringProperty [value: ");
        for (String date : dates) {
            sb.append(date);
            sb.append(", ");
        }
        sb.replace(sb.length() - 2, sb.length(), "]");
        return sb.toString();
    }

    @BeforeEach
    public void setUp() {
        volunteerDates = new VolunteerDates("2022-01-15");
    }

    @Test
    public void createNewVolunteerDateWithInvalidDates() {
        String withDuplicateDates = "2022-01-15, 2022-01-15";
        String withInvalidDate = "2022-01-15, 2022-29-16";
        String withDuplicateThenInvalid = "2022-01-15, 2022-01-15, 2022-29-16";
        String withInvalidThenDuplicate = "2022-01-15, 2022-29-16, 2022-01-15";

        assertThrows(VolunteerDuplicateDateException.class, () -> new VolunteerDates(withDuplicateDates));
        assertThrows(IllegalArgumentException.class, () -> new VolunteerDates(withInvalidDate));
        assertThrows(VolunteerDuplicateDateException.class, () -> new VolunteerDates(withDuplicateThenInvalid));
        assertThrows(IllegalArgumentException.class, () -> new VolunteerDates(withInvalidThenDuplicate));

    }

    @Test
    public void createNewVolunteerDate_checkForDefaultDate() {
        StringProperty dateList = volunteerDates.getDatesListAsObservableString();
        assertTrue(dateList.toString().contains(defaultDate.toString()));
    }

    @Test
    public void addDateToVolunteerDates_validDate_checkForNewDate() {
        String newDate = "2022-01-16";
        volunteerDates.addStringOfDatesToAvailList(newDate);
        StringProperty dateList = volunteerDates.getDatesListAsObservableString();
        assertTrue(dateList.toString().equals(generateStringProperty(defaultDate.toString(), newDate)));
    }

    @Test
    public void addDateToVolunteerDates_invalidDate_checkForExceptionThrown() {
        String newDate = "2022-29-16";
        assertThrows(Exception.class, () -> volunteerDates.addStringOfDatesToAvailList(newDate));
    }

    @Test
    public void RemoveDateFromVolunteerDates_validDate_checkForRemovedDate() {
        String newDate = "2022-01-16";
        volunteerDates.addStringOfDatesToAvailList(newDate);
        volunteerDates.removeStringOfDatesFromAvailList(defaultDate.toString());

        StringProperty dateList = volunteerDates.getDatesListAsObservableString();
        System.out.println(dateList.toString());
        assertEquals(dateList.toString(),generateStringProperty(newDate));
    }

    @Test
    public void removeAllDatesFromVolunteerDates() {
        volunteerDates.removeStringOfDatesFromAvailList(defaultDate.toString());
        StringProperty dateList = volunteerDates.getDatesListAsObservableString();
        assertEquals(dateList.toString(), generateStringProperty());
    }

    @Test
    public void RemoveDateFromVolunteerDates_invalidDateOrDateNotPresent_checkForNoChange() {
        String notPresentDate = "2022-01-17";
        assertThrows(VolunteerDeleteMissingDateException.class, () -> volunteerDates.removeStringOfDatesFromAvailList(notPresentDate));

        String invalidDate = "2022-29-16";
        assertThrows(IllegalArgumentException.class, () -> volunteerDates.removeStringOfDatesFromAvailList(invalidDate));
        String invalidDate2 = "2022-01-55";
        assertThrows(IllegalArgumentException.class, () -> volunteerDates.removeStringOfDatesFromAvailList(invalidDate2));

        StringProperty dateList = volunteerDates.getDatesListAsObservableString();
        assertEquals(dateList.toString(),generateStringProperty(defaultDate.toString()));
    }
}
