package dev.gregorius.library.json.reflect.util;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AssertionUtilTest {

    private static final String ONE_OBJECT =
        """
            {
              "object": {}
            }
            """;

    private static final String ANOTHER_OBJECT =
        """
            {
              "anotherObject": {}
            }
            """;

    private static final String TWO_OBJECTS =
        """
            {
              "object": {},
              "anotherObject": {}
            }
            """;

    private static final String NULL_OBJECT =
        """
            {
              "object": null
            }
            """;

    private static final String STRING_OBJECT =
        """
            {
              "object": "This is a String"
            }
            """;

    private static final String ANOTHER_STRING_OBJECT =
        """
            {
              "object": "This is another String"
            }
            """;

    private static final String TYPE_OBJECT_OBJECT =
        """
            {
              "object": "#object"
            }
            """;

    private static final String TYPE_STRING_OBJECT =
        """
            {
              "object": "#string"
            }
            """;

    private static final String OBJECT_WITH_NESTED_OBJECT =
        """
            {
              "object": {
                "nestedObject": {}
              }
            }
            """;

    private static final String OBJECT_WITH_ANOTHER_NESTED_OBJECT =
        """
            {
              "object": {
                "anotherNestedObject": {}
              }
            }
            """;

    private static final String OBJECT_WITH_ARRAY =
        """
            {
              "array": [
                1,
                2
              ]
            }
            """;

    private static final String OBJECT_WITH_ANOTHER_ARRAY =
        """
            {
              "array": [
                "one",
                "two"
              ]
            }
            """;

    private static Stream<Arguments> NOT_EQUAL_JSON_OBJECTS() {
        return Stream.of(
//            Arguments.of("One JSONObject is null", null, ONE_OBJECT, "Expected JSON objects '' to be equal.\nActual  : null\nExpected: " + ONE_OBJECT),
//            Arguments.of("Different objects size", ONE_OBJECT, TWO_OBJECTS, "Expected JSON objects '' to be equal.\nActual  : " + ONE_OBJECT + "Expected: " + TWO_OBJECTS),
//            Arguments.of("One traversed object does not exist", ONE_OBJECT, ANOTHER_OBJECT, "Expected JSON value 'anotherObject' to be present."),
            Arguments.of("One traversed object is null", NULL_OBJECT, ONE_OBJECT, "Expected JSON values 'object' to be equal.\nActual  : null\nExpected: {}\n")
//            Arguments.of("Not matching data type", STRING_OBJECT, TYPE_OBJECT_OBJECT, "Expected 'object' to be of type object.\nActual value: \"This is a String\"\n"),
//            Arguments.of("Different nested objects", OBJECT_WITH_NESTED_OBJECT, OBJECT_WITH_ANOTHER_NESTED_OBJECT, "Expected JSON value 'object.anotherNestedObject' to be present."),
//            Arguments.of("Different nested arrays", OBJECT_WITH_ARRAY, OBJECT_WITH_ANOTHER_ARRAY, "Expected JSON arrays 'array' to be equal.\nActual  : [\n  1,\n  2\n]\nExpected: [\n  \"one\",\n  \"two\"\n]\n"),
//            Arguments.of("Different values", STRING_OBJECT, ANOTHER_STRING_OBJECT, "Expected JSON values 'object' to be equal.\nActual  : \"This is a String\"\nExpected: \"This is another String\"\n")
        );
    }

    private static Stream<Arguments> EQUAL_JSON_OBJECTS() {
        return Stream.of(
            Arguments.of(STRING_OBJECT, STRING_OBJECT),
            Arguments.of(STRING_OBJECT, TYPE_STRING_OBJECT)
        );
    }

    private static final String TWO_ITEMS_ARRAY = "[1, 2]";
    private static final String ANOTHER_TWO_ITEMS_ARRAY = "[1, 3]";
    private static final String THREE_ITEMS_ARRAY = "[1, 2, 3]";
    private static final String ARRAY_CONTAINING_NULL = "[1, null]";
    private static final String ARRAY_CONTAINING_OBJECT =
        """
            [
              {
                "field": "value"
              }
            ]
            """;

    private static final String ARRAY_CONTAINING_ANOTHER_OBJECT =
        """
            [
              {
                "field": "anotherValue"
              }
            ]
            """;

    private static final String ARRAY_CONTAINING_ARRAY =
        """
            [
              [
                1,
                2
              ]
            ]
            """;

    private static final String ARRAY_CONTAINING_ANOTHER_ARRAY =
        """
            [
              [
                1,
                2,
                3
              ]
            ]
            """;

    private static Stream<Arguments> NOT_EQUAL_JSON_ARRAYS() {
        return Stream.of(
            Arguments.of("One JSONArray is null", null, TWO_ITEMS_ARRAY, "Expected JSON arrays 'arrayPath' to be equal.\nActual  : null\nExpected: [\n  1,\n  2\n]\n"),
            Arguments.of("Different arrays size", TWO_ITEMS_ARRAY, THREE_ITEMS_ARRAY, "Expected JSON arrays 'arrayPath' to be equal.\nActual  : [\n  1,\n  2\n]\nExpected: [\n  1,\n  2,\n  3\n]\n"),
            Arguments.of("Not matching value null", ARRAY_CONTAINING_NULL, TWO_ITEMS_ARRAY, "Expected JSON arrays 'arrayPath' to be equal.\nActual  : [\n  1,\n  null\n]\nExpected: [\n  1,\n  2\n]\n"),
            Arguments.of("Not matching object", ARRAY_CONTAINING_OBJECT, ARRAY_CONTAINING_ANOTHER_OBJECT, "Expected JSON arrays 'arrayPath' to be equal.\nActual  : " + ARRAY_CONTAINING_OBJECT + "Expected: " + ARRAY_CONTAINING_ANOTHER_OBJECT),
            Arguments.of("Not matching array", ARRAY_CONTAINING_ARRAY, ARRAY_CONTAINING_ANOTHER_ARRAY, "Expected JSON arrays 'arrayPath' to be equal.\nActual  : " + ARRAY_CONTAINING_ARRAY + "Expected: " + ARRAY_CONTAINING_ANOTHER_ARRAY),
            Arguments.of("Not matching value", TWO_ITEMS_ARRAY, ANOTHER_TWO_ITEMS_ARRAY, "Expected JSON arrays 'arrayPath' to be equal.\nActual  : [\n  1,\n  2\n]\nExpected: [\n  1,\n  3\n]\n")
        );
    }

    @Test
    void given_NotEqualObjects_when_assertEqual_then_AssertionErrorIsThrown() {
        final Throwable exception = assertThrows(AssertionError.class, () -> AssertionUtil.assertEqual("data", "FirstString", "SecondString"));
        assertThat(exception.getMessage()).isEqualTo("Expected 'data' to be equal.\nActual  : \"FirstString\"\nExpected: \"SecondString\"\n");
    }

    @Test
    void given_EqualObjects_when_assertEqual_then_NoAssertionErrorIsThrown() {
        assertDoesNotThrow(() -> AssertionUtil.assertEqual("", "EqualString", "EqualString"));
    }

    @ParameterizedTest
    @MethodSource("NOT_EQUAL_JSON_OBJECTS")
    void given_NotEqualJsonObjects_when_assertEqualJsonObjects_then_AssertionErrorIsThrown(final String description, final String object,
                                                                                           final String anotherObject, final String errorMessage) {
        final JsonObject jsonObject = object != null ? JsonParser.parseString(object).getAsJsonObject() : null;
        final JsonObject anotherJsonObject = anotherObject != null ? JsonParser.parseString(anotherObject).getAsJsonObject() : null;

        final Throwable exception = assertThrows(AssertionError.class, () -> AssertionUtil.assertEqualJsonObjects("", jsonObject, anotherJsonObject));
        assertThat(exception.getMessage()).isEqualTo(errorMessage);
    }

    @Test
    void given_SameJSONObjects_when_assertEqualJsonObjects_then_NoAssertionErrorIsThrown() {
        final JsonObject jsonObject = JsonParser.parseString(STRING_OBJECT).getAsJsonObject();

        assertDoesNotThrow(() -> AssertionUtil.assertEqualJsonObjects("", jsonObject, jsonObject));
    }

    @ParameterizedTest
    @MethodSource("EQUAL_JSON_OBJECTS")
    void given_EqualJSONObjects_when_assertEqualJsonObjects_then_NoAssertionErrorIsThrown() {
        final JsonObject jsonObject = JsonParser.parseString(STRING_OBJECT).getAsJsonObject();
        final JsonObject anotherJsonObject = JsonParser.parseString(STRING_OBJECT).getAsJsonObject();

        assertDoesNotThrow(() -> AssertionUtil.assertEqualJsonObjects("", jsonObject, anotherJsonObject));
    }

    @ParameterizedTest
    @MethodSource("NOT_EQUAL_JSON_ARRAYS")
    void given_NotEqualJsonArrays_when_assertEqualJsonArrays_then_AssertionErrorIsThrown(final String description, final String array,
                                                                                         final String anotherArray, final String errorMessage) {
        final JsonArray jsonArray = array != null ? JsonParser.parseString(array).getAsJsonArray() : null;
        final JsonArray anotherJsonArray = anotherArray != null ? JsonParser.parseString(anotherArray).getAsJsonArray() : null;

        final Throwable exception = assertThrows(AssertionError.class, () -> AssertionUtil.assertEqualJsonArrays("arrayPath", jsonArray, anotherJsonArray));
        assertThat(exception.getMessage()).isEqualTo(errorMessage);
    }

    @Test
    void given_SameJSONArrays_when_assertEqualJsonArrays_then_NoAssertionErrorIsThrown() {
        final JsonArray jsonArray = JsonParser.parseString(TWO_ITEMS_ARRAY).getAsJsonArray();

        assertDoesNotThrow(() -> AssertionUtil.assertEqualJsonArrays("", jsonArray, jsonArray));
    }

    @Test
    void given_EqualJSONArrays_when_assertEqualJsonArrays_then_NoAssertionErrorIsThrown() {
        final JsonArray jsonArray = JsonParser.parseString(TWO_ITEMS_ARRAY).getAsJsonArray();
        final JsonArray anotherJsonArray = JsonParser.parseString(TWO_ITEMS_ARRAY).getAsJsonArray();

        assertDoesNotThrow(() -> AssertionUtil.assertEqualJsonArrays("", jsonArray, anotherJsonArray));
    }
}
