package dev.gregorius.library.json.reflect.util.fuzzy;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

public abstract class DateTimeFuzzyMatcher implements FuzzyMatcher {

	protected abstract List<DateTimeFormatter> getDateTimeFormatters();

	protected final boolean dateTimeFormattersMatch(final Object value) {
		if (!new StringMatcher().matches(value)) {
			return false;
		}

		// If at least one of the formats works, it returns false -> allMatch is false -> the method returns true
		// If none of the formats work, all return true -> allMatch is true -> the method returns false
        return !getDateTimeFormatters().stream().allMatch(dateTimeFormatter -> {
            try {
                dateTimeFormatter.parse((String) value);
            } catch (final DateTimeParseException e) {
                return true;
            }

            return false;
        });
    }
}
