package seedu.address.model.person;

import java.time.LocalDate;
import java.time.Period;

/**
 * Represents the housekeeping details of a client.
 */
public class HousekeepingDetails implements Comparable<HousekeepingDetails> {
    public static final String MESSAGE_CONSTRAINTS =
            "Housekeeping details should be in the format: yyyy-mm-dd n (days|weeks|months|years) "
                    + "where n is an integer quantity of days, weeks, months or years.";
    public static final String MESSAGE_CONSTRAINTS_STORAGE =
            "Housekeeping details should be in the format: yyyy-mm-dd P?Y?M?W?D?"
                    + "where P is the period designator, Y is years, M is months, W is weeks D is days. "
                    + "YMWD must be in that order. All fields are optional.";
    public static final String NO_DETAILS_PROVIDED = "No housekeeping details provided";

    /** The last date the housekeeping was done. */
    private LocalDate lastHousekeepingDate;
    /** Client's preferred time between housekeeping services. */
    private Period preferredInterval;

    /**
     * Creates a HousekeepingDetails object.
     * @param lastHousekeepingDate
     * @param preferredInterval
     */
    public HousekeepingDetails(LocalDate lastHousekeepingDate, Period preferredInterval) {
        this.lastHousekeepingDate = lastHousekeepingDate;
        this.preferredInterval = preferredInterval;
    }

    /**
     * Creates a HousekeepingDetails object using a string representation of the housekeeping details used for storage.
     * @param details A string representation of the housekeeping details in the format: yyyy-mm-dd P?Y?M?D?T?H?M?S
     */
    public HousekeepingDetails(String details) {
        String[] s = details.split(" ");
        this.lastHousekeepingDate = LocalDate.parse(s[0]);
        this.preferredInterval = Period.parse(s[1]);
    }

    public LocalDate getNextHousekeepingDate() {
        return lastHousekeepingDate.plus(preferredInterval);
    }

    public static boolean isValidHousekeepingDetailsUser(String test) {
        return test.matches("\\d{4}-\\d{2}-\\d{2} \\d+ (days|weeks|months|years)");
    }

    public static boolean isValidHouseKppingDetailsStorage(String test) {
        return test.matches(
                "^\\d{4}-\\d{2}-\\d{2} P(?!$)(\\d+Y)?(\\d+M)?(\\d+W)?(\\d+D)?$");
    }

    public static String makeStoredDetailsFormatReadable(String details) {
        if (details == null) {
            return NO_DETAILS_PROVIDED;
        }
        else if (!isValidHouseKppingDetailsStorage(details)) {
            return "Invalid housekeeping details format";
        }
        String[] s = details.split(" ");
        String num = s[1].substring(1, s[1].length() - 1);
        String unit = s[1].substring(s[1].length() - 1);
        String unitString;
        switch (unit) {
        case "Y":
            unitString = "years";
            break;
        case "M":
            unitString = "months";
            break;
        case "W":
            unitString = "weeks";
            break;
        case "D":
            unitString = "days";
            break;
        default:
            unitString = "Invalid unit";
        }

        return String.format("Last housekeeping date: %s\nPreferred interval: %s %s", s[0], num, unitString);
    }

    @Override
    public int compareTo(HousekeepingDetails other) {
        return this.getNextHousekeepingDate().compareTo(other.getNextHousekeepingDate());
    }

    @Override
    public String toString() {
        return lastHousekeepingDate + " " + preferredInterval;
    }
}