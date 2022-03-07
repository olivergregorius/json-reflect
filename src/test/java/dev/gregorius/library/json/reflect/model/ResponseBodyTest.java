package dev.gregorius.library.json.reflect.model;

import com.google.gson.JsonSyntaxException;
import dev.gregorius.library.json.reflect.JsonReflect;
import dev.gregorius.library.json.reflect.setup.BaseTest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ResponseBodyTest extends BaseTest {

    private static final String ACTUAL_JSON_DOCUMENT = """
        {
          "data": {
            "integerValue": 1,
            "stringValue": "This is a String",
            "booleanValue": true,
            "nullValue": null,
            "objectValue": {
              "nestedValue": "val"
            },
            "arrayValue": [
              1,
              2
            ]
          }
        }
        """;

    private static final String EXPECTED_JSON_DOCUMENT = """
        {
          "data": {
            "nullValue": null,
            "integerValue": 1,
            "objectValue": {
              "nestedValue": "val"
            },
            "booleanValue": true,
            "stringValue": "This is a String",
            "arrayValue": [
              2,
              1
            ]
          }
        }
        """;

    private static final String UNEQUAL_EXPECTED_JSON_DOCUMENT = """
        {
          "expectedKey": "expectedValue"
        }
        """;

    private static final String INVALID_JSON_DOCUMENT = """
        {
          "key": "value",
          "key2": 123
        """;

    @Test
    void when_getAsStringIsCalled_then_theResponseBodyIsReturnedAsString() {
        final String actualResponseBodyString = JsonReflect.endpoint("/post-reflecting-body")
            .body(ACTUAL_JSON_DOCUMENT)
            .when()
            .post()
            .then()
            .getResponseBody()
            .getAsString();

        assertThat(actualResponseBodyString).isEqualTo(ACTUAL_JSON_DOCUMENT);
    }

    @Test
    void given_twoEqualJsonDocuments_when_callingIsEqualTo_then_noAssertionErrorShouldBeThrown() {
        assertDoesNotThrow(
            () -> JsonReflect.endpoint("/post-reflecting-body")
                .body(ACTUAL_JSON_DOCUMENT)
                .when()
                .post()
                .then()
                .getResponseBody()
                .isEqualTo(EXPECTED_JSON_DOCUMENT)
        );
    }

    @Test
    void given_twoUnequalJsonDocuments_when_callingIsEqualTo_then_AssertionErrorIsThrown() {
        assertThrows(AssertionError.class,
            () -> JsonReflect.endpoint("/post-reflecting-body")
                .body(ACTUAL_JSON_DOCUMENT)
                .when()
                .post()
                .then()
                .getResponseBody()
                .isEqualTo(UNEQUAL_EXPECTED_JSON_DOCUMENT)
        );
    }

    @Test
    void given_InvalidJsonDocument_when_callingIsEqualTo_then_JsonSyntaxExceptionIsThrown() {
        assertThrows(JsonSyntaxException.class,
            () -> JsonReflect.endpoint("/post-reflecting-body")
                .body(INVALID_JSON_DOCUMENT)
                .when()
                .post()
                .then()
                .getResponseBody()
                .isEqualTo(EXPECTED_JSON_DOCUMENT)
        );
    }
}
