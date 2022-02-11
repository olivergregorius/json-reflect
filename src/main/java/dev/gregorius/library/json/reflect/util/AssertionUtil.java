package dev.gregorius.library.json.reflect.util;

import org.hamcrest.Matcher;

import java.util.function.Predicate;

import static org.hamcrest.Matchers.equalTo;

public class AssertionUtil {

    private static final Predicate<Object> IS_STRING = String.class::isInstance;

    private AssertionUtil() {
    }

    /**
     * Asserts the actual value to equal the expected value.
     *
     * @param fieldPath     the path of the field to be included in the error message as hint for the user
     * @param actualValue   the actual value of the field
     * @param expectedValue the expected value
     * @param <T>           the type of the value
     * @throws AssertionError if the values are not equal
     */
    public static <T> void assertEqual(final String fieldPath, final T actualValue, final T expectedValue) throws AssertionError {
        final Matcher<T> matcher = equalTo(expectedValue);
        if (!matcher.matches(actualValue)) {
            final String errorMessage = String.format("Expected '%s' to be equal.%nActual  : %s%nExpected: %s%n", fieldPath, getFormattedValue(actualValue), getFormattedValue(expectedValue));
            throw new AssertionError(errorMessage);
        }
    }

    private static <T> String getFormattedValue(final T value) {
        return IS_STRING.test(value) ? String.format("\"%s\"", value) : value.toString();
    }
}
