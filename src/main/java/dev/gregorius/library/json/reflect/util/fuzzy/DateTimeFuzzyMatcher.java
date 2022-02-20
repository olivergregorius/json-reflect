package dev.gregorius.library.json.reflect.util.fuzzy;

import com.google.gson.JsonElement;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

public abstract class DateTimeFuzzyMatcher implements FuzzyMatcher {

	protected abstract List<DateTimeFormatter> getDateTimeFormatters();

    protected final boolean dateTimeFormattersMatch(final JsonElement value) {
        if (!new StringMatcher().matches(value)) {
            return false;
        }

        // If at least one of the formats works, it returns true -> anyMatch is true -> the method returns true
        // If none of the formats work, all return false -> anyMatch is false -> the method returns false
        return getDateTimeFormatters().stream().anyMatch(dateTimeFormatter -> {
            try {
                dateTimeFormatter.parse(value.getAsString());
            } catch (final DateTimeParseException e) {
                return false;
            }

            return true;
        });
    }
}
