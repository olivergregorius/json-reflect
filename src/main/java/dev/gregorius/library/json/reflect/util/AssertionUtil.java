package dev.gregorius.library.json.reflect.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dev.gregorius.library.json.reflect.util.fuzzy.FuzzyMatcher;
import dev.gregorius.library.json.reflect.util.fuzzy.FuzzyMatchingUtil;
import dev.gregorius.library.json.reflect.util.fuzzy.NullMatcher;
import org.hamcrest.Matcher;
import org.springframework.util.StringUtils;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.hamcrest.Matchers.equalTo;

public class AssertionUtil {

    private AssertionUtil() {
    }

    private static final NullMatcher NULL_MATCHER = new NullMatcher();
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

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
            final String errorMessage = String.format("Expected '%s' to be equal.%nActual  : %s%nExpected: %s%n", fieldPath, GSON.toJson(actualValue), GSON.toJson(expectedValue));
            throw new AssertionError(errorMessage);
        }
    }

    private static <T> void assertIsOfType(final String fieldPath, final T actualValue, final FuzzyMatcher fuzzyMatcher) {
        if (!fuzzyMatcher.matches(actualValue)) {
            final String errorMessage = String.format("Expected '%s' to be of type %s.%nActual value: %s%n", fieldPath,
                StringUtils.trimLeadingCharacter(fuzzyMatcher.getFuzzyTag(), '#'), GSON.toJson(actualValue));
            throw new AssertionError(errorMessage);
        }
    }

    private static boolean eitherValueIsNull(final JsonElement actualValue, final JsonElement expectedValue) {
        return (NULL_MATCHER.matches(actualValue) && !NULL_MATCHER.matches(expectedValue)) || (!NULL_MATCHER.matches(actualValue) && NULL_MATCHER.matches(expectedValue));
    }

    /**
     * Checks two JsonObject instances to be equal.
     * <p>
     * This method traverses all keys in the given objects to do a recursive check for equality. If the objects differ an {@link AssertionError} is thrown, if
     * the objects are equal the method returns nothing.
     *
     * @param basePath       the path of the objects in the checked Json document, returned to the user as part of the error message
     * @param actualObject   actual {@link JsonObject} instance to be checked
     * @param expectedObject expected {@link JsonObject} instance
     * @throws AssertionError if the objects are not equal
     */
    public static void assertEqualJsonObjects(final String basePath, final JsonObject actualObject, final JsonObject expectedObject) throws AssertionError {
        String errorMessage = String.format("Expected JSON objects '%s' to be equal.%nActual  : %s%nExpected: %s%n", basePath, GSON.toJson(actualObject), GSON.toJson(expectedObject));

        // Check if objects are equal (also true if both are null)
        if (Objects.equals(actualObject, expectedObject)) {
            return;
        }

        // Check if either of the objects is null
        if (eitherValueIsNull(actualObject, expectedObject)) {
            throw new AssertionError(errorMessage);
        }

        // Check if object sizes differ
        if (actualObject.size() != expectedObject.size()) {
            throw new AssertionError(errorMessage);
        }

        // Traverse objects key by key. We do not need to check further if the values differ for which an AssertionError is thrown.
        // If no AssertionError is thrown during iteration the objects are considered equal.
        for (final String expectedKey : expectedObject.keySet()) {
            final String expectedKeyPath = StringUtils.trimLeadingCharacter(String.format("%s.%s", basePath, expectedKey), '.');
            if (!actualObject.has(expectedKey)) {
                errorMessage = String.format("Expected JSON value '%s' to be present.", expectedKeyPath);
                throw new AssertionError(errorMessage);
            }

            final JsonElement expectedValue = expectedObject.get(expectedKey);
            final JsonElement actualValue = actualObject.get(expectedKey);
            errorMessage = String.format("Expected JSON values '%s' to be equal.%nActual  : %s%nExpected: %s%n", expectedKeyPath, GSON.toJson(actualValue), GSON.toJson(expectedValue));

            // Check if either of the values is null
            if (eitherValueIsNull(actualValue, expectedValue)) {
                throw new AssertionError(errorMessage);
            }

            // As both values could be null we need to do a null check here prior to further checks
            if (actualValue != null) {
                final Optional<FuzzyMatcher> fuzzyMatcherOptional = FuzzyMatchingUtil.getFuzzyMatcher(expectedValue);
                if (fuzzyMatcherOptional.isPresent()) {
                    // We do not check the value itself but if it represents the data type expected
                    assertIsOfType(expectedKeyPath, actualValue, fuzzyMatcherOptional.get());
                } else if (actualValue.isJsonObject() && expectedValue.isJsonObject()) {
                    // If both values are Json objects we check those recursively
                    assertEqualJsonObjects(expectedKeyPath, actualValue.getAsJsonObject(), expectedValue.getAsJsonObject());
                } else if (actualValue.isJsonArray() && expectedValue.isJsonArray()) {
                    // If both values are Json arrays we check those recursively
                    assertEqualJsonArrays(expectedKeyPath, actualValue.getAsJsonArray(), expectedValue.getAsJsonArray());
                } else {
                    // We check if the values are equal
                    if (!Objects.equals(expectedValue, actualValue)) {
                        throw new AssertionError(errorMessage);
                    }
                }
            }
        }
    }

    /**
     * Checks two JsonArray instances to be equal.
     * <p>
     * This method traverses all keys in the given arrays to do a recursive check for equality. If the arrays differ an {@link AssertionError} is thrown, if
     * the arrays are equal the method returns nothing.
     *
     * @param basePath      the path of the arrays in the checked Json document, returned to the user as part of the error message
     * @param actualArray   actual {@link JsonArray} instance to be checked
     * @param expectedArray expected {@link JsonArray} instance
     * @throws AssertionError if the arrays are not equal
     */
    public static void assertEqualJsonArrays(final String basePath, final JsonArray actualArray, final JsonArray expectedArray) throws AssertionError {
        final String errorMessage = String.format("Expected JSON arrays '%s' to be equal.%nActual  : %s%nExpected: %s%n", basePath, GSON.toJson(actualArray), GSON.toJson(expectedArray));

        // Check if arrays are equal (also true if both are null)
        if (Objects.equals(actualArray, expectedArray)) {
            return;
        }

        // Check if either of the arrays is null
        if (eitherValueIsNull(actualArray, expectedArray)) {
            throw new AssertionError(errorMessage);
        }

        // Check if array sizes differ
        if (actualArray.size() != expectedArray.size()) {
            throw new AssertionError(errorMessage);
        }

        // Traverse items. We do not need to check further if the values differ for which an AssertionError is thrown.
        // Order is not important in a Json array, so we only check if one matching actual item exists for the expected one (noneMatch -> false).
        // If no AssertionError is thrown during iteration the objects are considered equal.
        for (final JsonElement expectedValue : expectedArray) {
            if (StreamSupport.stream(actualArray.spliterator(), true).noneMatch(actualValue -> {
                // Check if either of the values is null
                if (eitherValueIsNull(actualValue, expectedValue)) {
                    return false;
                }

                // As both values could be null we need to do a null check here prior to further checks
                if (actualValue != null) {
                    try {
                        if (actualValue.isJsonObject() && expectedValue.isJsonObject()) {
                            // If both values are Json objects we check those recursively
                            assertEqualJsonObjects(basePath, actualValue.getAsJsonObject(), expectedValue.getAsJsonObject());
                        } else if (actualValue.isJsonArray() && expectedValue.isJsonArray()) {
                            // If both values are Json arrays we check those recursively
                            assertEqualJsonArrays(basePath, actualValue.getAsJsonArray(), expectedValue.getAsJsonArray());
                        } else {
                            // We check if the values are equal
                            return Objects.equals(expectedValue, actualValue);
                        }
                    } catch (final AssertionError e) {
                        return false;
                    }
                }

                return true;
            })) {
                throw new AssertionError(errorMessage);
            }
        }
    }
}
