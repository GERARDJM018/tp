package housekeeping.hub.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import housekeeping.hub.commons.exceptions.IllegalValueException;
import housekeeping.hub.model.person.HousekeepingDetails;

/**
 * Jackson-friendly version of {@link HousekeepingDetails}.
 */
public class JsonAdaptedDetails {

    private final String details;

    /**
     * Constructs a {@code JsonAdaptedDetails} with the given {@code details}.
     */
    @JsonCreator
    public JsonAdaptedDetails(String details) {
        this.details = details;
    }

    /**
     * Converts a given {@code HousekeepingDetails} into this class for Jackson use.
     */
    public JsonAdaptedDetails(HousekeepingDetails source) {
        this.details = source.toString();
    }

    @JsonValue
    public String getDetails() {
        return details;
    }

    /**
     * Converts this Jackson-friendly adapted details object into the model's {@code HousekeepingDetails} object.
     */
    public HousekeepingDetails toModelType() throws IllegalValueException {
        if (!HousekeepingDetails.isValidHousekeepingDetailsStorage(details)) {
            throw new IllegalValueException(HousekeepingDetails.MESSAGE_CONSTRAINTS);
        }
        return new HousekeepingDetails(details);
    }
}
