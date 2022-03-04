package dev.gregorius.library.json.reflect.util;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
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

    private static final String OBJECT_CONTAINING_OBJECT =
        """
            {
              "object": {}
            }
            """;

    private static final String OBJECT_CONTAINING_ANOTHER_OBJECT =
        """
            {
              "anotherObject": {}
            }
            """;

    private static final String OBJECT_CONTAINING_TWO_OBJECTS =
        """
            {
              "object": {},
              "anotherObject": {}
            }
            """;

    private static final String OBJECT_CONTAINING_NULL_OBJECT =
        """
            {
              "object": null
            }
            """;

    private static final String OBJECT_CONTAINING_STRING_OBJECT =
        """
            {
              "object": "This is a String"
            }
            """;

    private static final String OBJECT_CONTAINING_ANOTHER_STRING_OBJECT =
        """
            {
              "object": "This is another String"
            }
            """;

    private static final String OBJECT_CONTAINING_OBJECT_TYPE_OBJECT =
        """
            {
              "object": "#object"
            }
            """;

    private static final String OBJECT_CONTAINING_STRING_TYPE_OBJECT =
        """
            {
              "object": "#string"
            }
            """;

    private static final String OBJECT_CONTAINING_OBJECT_WITH_NESTED_OBJECT =
        """
            {
              "object": {
                "nestedObject": {}
              }
            }
            """;

    private static final String OBJECT_CONTAINING_OBJECT_WITH_NESTED_OBJECT_TYPE_OBJECT =
        """
            {
              "object": {
                "nestedObject": "#object"
              }
            }
            """;

    private static final String OBJECT_CONTAINING_OBJECT_WITH_ANOTHER_NESTED_OBJECT =
        """
            {
              "object": {
                "anotherNestedObject": {}
              }
            }
            """;

    private static final String OBJECT_CONTAINING_ARRAY =
        """
            {
              "array": [
                1,
                2
              ]
            }
            """;

    private static final String OBJECT_CONTAINING_INTERCHANGED_ARRAY =
        """
            {
              "array": [
                2,
                1
              ]
            }
            """;

    private static final String OBJECT_CONTAINING_ANOTHER_ARRAY =
        """
            {
              "array": [
                "one",
                "two"
              ]
            }
            """;

    private static final String ARRAY_CONTAINING_TWO_ITEMS = "[1, 2]";
    private static final String ARRAY_CONTAINING_TWO_OTHER_ITEMS = "[1, 3]";
    private static final String ARRAY_CONTAINING_THREE_ITEMS = "[1, 2, 3]";
    private static final String ARRAY_CONTAINING_NULL_ITEM = "[1, null]";
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
    private static final String ARRAY_CONTAINING_TWO_OBJECTS =
        """
            [
              {
                "field": "value"
              },
              {
                "field2": "value2"
              }
            ]
            """;

    private static final String INTERCHANGED_ARRAY_CONTAINING_TWO_OBJECTS =
        """
            [
              {
                "field2": "value2"
              },
              {
                "field": "value"
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

    private static final String ARRAY_CONTAINING_INTERCHANGED_ARRAY =
        """
            [
              [
                2,
                1
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

    private static Stream<Arguments> notEqualJsonElements() {
        return Stream.of(
            Arguments.of("One JsonElement is null", null, OBJECT_CONTAINING_OBJECT, "Expected JSON documents to be equal.\nActual  : null\nExpected: " + OBJECT_CONTAINING_OBJECT),
            Arguments.of("Mixed object and array", OBJECT_CONTAINING_OBJECT, ARRAY_CONTAINING_TWO_ITEMS, "Expected JSON documents to be equal.\nActual  : " + OBJECT_CONTAINING_OBJECT + "Expected: [\n  1,\n  2\n]\n"),
            Arguments.of("Mixed JsonPrimitive and object", "String", OBJECT_CONTAINING_OBJECT, "Expected JSON documents to be equal.\nActual  : \"String\"\nExpected: " + OBJECT_CONTAINING_OBJECT),
            Arguments.of("Mixed JsonPrimitive and array", "String", ARRAY_CONTAINING_TWO_ITEMS, "Expected JSON documents to be equal.\nActual  : \"String\"\nExpected: [\n  1,\n  2\n]\n"),
            Arguments.of("Different JsonPrimitives", "String", "AnotherString", "Expected JSON documents to be equal.\nActual  : \"String\"\nExpected: \"AnotherString\"\n")
        );
    }

    private static Stream<Arguments> equalJsonElements() {
        return Stream.of(
            Arguments.of(null, JsonNull.INSTANCE.toString()),
            Arguments.of("String", "String"),
            Arguments.of(OBJECT_CONTAINING_STRING_OBJECT, OBJECT_CONTAINING_STRING_OBJECT),
            Arguments.of(OBJECT_CONTAINING_STRING_OBJECT, OBJECT_CONTAINING_STRING_TYPE_OBJECT),
            Arguments.of(ARRAY_CONTAINING_ARRAY, ARRAY_CONTAINING_INTERCHANGED_ARRAY)
        );
    }

    private static Stream<Arguments> notEqualJsonObjects() {
        return Stream.of(
            Arguments.of("Different objects size", OBJECT_CONTAINING_OBJECT, OBJECT_CONTAINING_TWO_OBJECTS, "Expected JSON objects '' to be equal.\nActual  : " + OBJECT_CONTAINING_OBJECT + "Expected: " + OBJECT_CONTAINING_TWO_OBJECTS),
            Arguments.of("One traversed object does not exist", OBJECT_CONTAINING_OBJECT, OBJECT_CONTAINING_ANOTHER_OBJECT, "Expected JSON value 'anotherObject' to be present."),
            Arguments.of("One traversed object is null", OBJECT_CONTAINING_NULL_OBJECT, OBJECT_CONTAINING_OBJECT, "Expected JSON values 'object' to be equal.\nActual  : null\nExpected: {}\n"),
            Arguments.of("Not matching data type", OBJECT_CONTAINING_STRING_OBJECT, OBJECT_CONTAINING_OBJECT_TYPE_OBJECT, "Expected 'object' to be of type object.\nActual value: \"This is a String\"\n"),
            Arguments.of("Different nested objects", OBJECT_CONTAINING_OBJECT_WITH_NESTED_OBJECT, OBJECT_CONTAINING_OBJECT_WITH_ANOTHER_NESTED_OBJECT, "Expected JSON value 'object.anotherNestedObject' to be present."),
            Arguments.of("Different nested arrays", OBJECT_CONTAINING_ARRAY, OBJECT_CONTAINING_ANOTHER_ARRAY, "Expected JSON arrays 'array' to be equal.\nActual  : [\n  1,\n  2\n]\nExpected: [\n  \"one\",\n  \"two\"\n]\n"),
            Arguments.of("Different values", OBJECT_CONTAINING_STRING_OBJECT, OBJECT_CONTAINING_ANOTHER_STRING_OBJECT, "Expected JSON values 'object' to be equal.\nActual  : \"This is a String\"\nExpected: \"This is another String\"\n")
        );
    }

    private static Stream<Arguments> equalJsonObjects() {
        return Stream.of(
            Arguments.of(OBJECT_CONTAINING_STRING_OBJECT, OBJECT_CONTAINING_STRING_OBJECT),
            Arguments.of(OBJECT_CONTAINING_STRING_OBJECT, OBJECT_CONTAINING_STRING_TYPE_OBJECT),
            Arguments.of(OBJECT_CONTAINING_OBJECT_WITH_NESTED_OBJECT, OBJECT_CONTAINING_OBJECT_WITH_NESTED_OBJECT_TYPE_OBJECT),
            Arguments.of(OBJECT_CONTAINING_ARRAY, OBJECT_CONTAINING_INTERCHANGED_ARRAY)
        );
    }

    private static Stream<Arguments> notEqualJsonArrays() {
        return Stream.of(
            Arguments.of("Different arrays size", ARRAY_CONTAINING_TWO_ITEMS, ARRAY_CONTAINING_THREE_ITEMS, "Expected JSON arrays 'arrayPath' to be equal.\nActual  : [\n  1,\n  2\n]\nExpected: [\n  1,\n  2,\n  3\n]\n"),
            Arguments.of("Not matching value null", ARRAY_CONTAINING_NULL_ITEM, ARRAY_CONTAINING_TWO_ITEMS, "Expected JSON arrays 'arrayPath' to be equal.\nActual  : [\n  1,\n  null\n]\nExpected: [\n  1,\n  2\n]\n"),
            Arguments.of("Not matching object", ARRAY_CONTAINING_OBJECT, ARRAY_CONTAINING_ANOTHER_OBJECT, "Expected JSON arrays 'arrayPath' to be equal.\nActual  : " + ARRAY_CONTAINING_OBJECT + "Expected: " + ARRAY_CONTAINING_ANOTHER_OBJECT),
            Arguments.of("Not matching array", ARRAY_CONTAINING_ARRAY, ARRAY_CONTAINING_ANOTHER_ARRAY, "Expected JSON arrays 'arrayPath' to be equal.\nActual  : " + ARRAY_CONTAINING_ARRAY + "Expected: " + ARRAY_CONTAINING_ANOTHER_ARRAY),
            Arguments.of("Not matching value", ARRAY_CONTAINING_TWO_ITEMS, ARRAY_CONTAINING_TWO_OTHER_ITEMS, "Expected JSON arrays 'arrayPath' to be equal.\nActual  : [\n  1,\n  2\n]\nExpected: [\n  1,\n  3\n]\n")
        );
    }

    private static Stream<Arguments> equalJsonArrays() {
        return Stream.of(
            Arguments.of(ARRAY_CONTAINING_TWO_ITEMS, ARRAY_CONTAINING_TWO_ITEMS),
            Arguments.of(ARRAY_CONTAINING_TWO_OBJECTS, INTERCHANGED_ARRAY_CONTAINING_TWO_OBJECTS),
            Arguments.of(ARRAY_CONTAINING_ARRAY, ARRAY_CONTAINING_INTERCHANGED_ARRAY)
        );
    }

    @Test
    void given_notEqualObjects_when_assertEqual_then_AssertionErrorIsThrown() {
        final Throwable exception = assertThrows(AssertionError.class, () -> AssertionUtil.assertEqual("data", "FirstString", "SecondString"));
        assertThat(exception.getMessage()).isEqualTo("Expected 'data' to be equal.\nActual  : \"FirstString\"\nExpected: \"SecondString\"\n");
    }

    @Test
    void given_equalObjects_when_assertEqual_then_noAssertionErrorIsThrown() {
        assertDoesNotThrow(() -> AssertionUtil.assertEqual("", "EqualString", "EqualString"));
    }

    @ParameterizedTest
    @MethodSource("notEqualJsonElements")
    void given_notEqualJsonElements_when_assertEqualJsonElements_then_AssertionErrorIsThrown(final String description, final String document,
                                                                                             final String anotherDocument, final String errorMessage) {
        final JsonElement jsonElement = document != null ? JsonParser.parseString(document) : null;
        final JsonElement anotherJsonElement = anotherDocument != null ? JsonParser.parseString(anotherDocument) : null;

        final Throwable exception = assertThrows(AssertionError.class, () -> AssertionUtil.assertEqualJsonElements(jsonElement, anotherJsonElement));
        assertThat(exception.getMessage()).isEqualTo(errorMessage);
    }

    @ParameterizedTest
    @MethodSource("equalJsonElements")
    void given_equalJsonElements_when_assertEqualJsonElements_then_noAssertionErrorIsThrown(final String document, final String anotherDocument) {
        final JsonElement jsonElement = document != null ? JsonParser.parseString(document) : null;
        final JsonElement anotherJsonElement = document != null ? JsonParser.parseString(anotherDocument) : null;

        assertDoesNotThrow(() -> AssertionUtil.assertEqualJsonElements(jsonElement, anotherJsonElement));
    }

    @ParameterizedTest
    @MethodSource("notEqualJsonObjects")
    void given_notEqualJsonObjects_when_assertEqualJsonObjects_then_AssertionErrorIsThrown(final String description, final String object,
                                                                                           final String anotherObject, final String errorMessage) {
        final JsonObject jsonObject = object != null ? JsonParser.parseString(object).getAsJsonObject() : null;
        final JsonObject anotherJsonObject = anotherObject != null ? JsonParser.parseString(anotherObject).getAsJsonObject() : null;

        final Throwable exception = assertThrows(AssertionError.class, () -> AssertionUtil.assertEqualJsonObjects("", jsonObject, anotherJsonObject));
        assertThat(exception.getMessage()).isEqualTo(errorMessage);
    }

    @ParameterizedTest
    @MethodSource("equalJsonObjects")
    void given_equalJsonObjects_when_assertEqualJsonObjects_then_noAssertionErrorIsThrown(final String object, final String anotherObject) {
        final JsonObject jsonObject = object != null ? JsonParser.parseString(object).getAsJsonObject() : null;
        final JsonObject anotherJsonObject = anotherObject != null ? JsonParser.parseString(anotherObject).getAsJsonObject() : null;

        assertDoesNotThrow(() -> AssertionUtil.assertEqualJsonObjects("", jsonObject, anotherJsonObject));
    }

    @ParameterizedTest
    @MethodSource("notEqualJsonArrays")
    void given_notEqualJsonArrays_when_assertEqualJsonArrays_then_AssertionErrorIsThrown(final String description, final String array,
                                                                                         final String anotherArray, final String errorMessage) {
        final JsonArray jsonArray = array != null ? JsonParser.parseString(array).getAsJsonArray() : null;
        final JsonArray anotherJsonArray = anotherArray != null ? JsonParser.parseString(anotherArray).getAsJsonArray() : null;

        final Throwable exception = assertThrows(AssertionError.class, () -> AssertionUtil.assertEqualJsonArrays("arrayPath", jsonArray, anotherJsonArray));
        assertThat(exception.getMessage()).isEqualTo(errorMessage);
    }

    @ParameterizedTest
    @MethodSource("equalJsonArrays")
    void given_equalJsonArrays_when_assertEqualJsonArrays_then_noAssertionErrorIsThrown(final String array, final String anotherArray) {
        final JsonArray jsonArray = JsonParser.parseString(array).getAsJsonArray();
        final JsonArray anotherJsonArray = JsonParser.parseString(anotherArray).getAsJsonArray();

        assertDoesNotThrow(() -> AssertionUtil.assertEqualJsonArrays("", jsonArray, anotherJsonArray));
    }
}
