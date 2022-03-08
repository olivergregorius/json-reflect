package dev.gregorius.library.json.reflect.model;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import dev.gregorius.library.json.reflect.JsonReflect;
import dev.gregorius.library.json.reflect.setup.BaseTest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ResponseBodyFieldTest extends BaseTest {

    private static final String NO_JSON_OBJECT = """
        ["value", "second"]
        """;

    private static final String JSON_OBJECT = """
        {
          "data": {
            "nestedObject": {
              "value": 5432,
              "anotherValue": "HelloWorld"
            }
          },
          "value": 1234
        }
        """;

    @Test
    void given_noJsonObject_when_creatingResponseBodyField_then_IllegalArgumentExceptionIsThrown() {
        final Throwable exception = assertThrows(IllegalArgumentException.class,
            () -> JsonReflect.endpoint("/post-reflecting-body")
                .body(NO_JSON_OBJECT)
                .when()
                .post()
                .then()
                .getResponseBody()
                .valueOf(".value"));

        assertThat(exception.getMessage()).isEqualTo("Unable to traverse document as it is no JSON object");
    }

    @Test
    void given_invalidPathTraversingNonJsonObject_when_creatingResponseBodyField_then_IllegalArgumentExceptionIsThrown() {
        final Throwable exception = assertThrows(IllegalArgumentException.class,
            () -> JsonReflect.endpoint("/post-reflecting-body")
                .body(JSON_OBJECT)
                .when()
                .post()
                .then()
                .getResponseBody()
                .valueOf(".value.invalidPath"));

        assertThat(exception.getMessage()).isEqualTo("The path '.value.invalidPath' is not valid");
    }

    @Test
    void given_validPath_when_creatingResponseBodyField_then_extractedValueIsAsExpected() {
        final ResponseBody responseBody = JsonReflect.endpoint("/post-reflecting-body")
            .body(JSON_OBJECT)
            .when()
            .post()
            .then()
            .getResponseBody();

        final JsonElement fieldValue = responseBody.valueOf(".data.nestedObject.value").getValue();
        assertThat(fieldValue.getAsInt()).isEqualTo(5432);

        final JsonElement anotherFieldValue = responseBody.valueOf(".data.nestedObject.anotherValue").getValue();
        assertThat(anotherFieldValue.getAsString()).isEqualTo("HelloWorld");
    }

    @Test
    void given_matchingExpectedValue_when_checkingEquality_then_beHappy() {
        assertDoesNotThrow(
            () -> JsonReflect.endpoint("/post-reflecting-body")
                .body(JSON_OBJECT)
                .when()
                .post()
                .then()
                .getResponseBody()
                .valueOf(".data.nestedObject.value")
                .isEqualTo("5432")
        );

        assertDoesNotThrow(
            () -> JsonReflect.endpoint("/post-reflecting-body")
                .body(JSON_OBJECT)
                .when()
                .post()
                .then()
                .getResponseBody()
                .valueOf(".data.nestedObject.anotherValue")
                .isEqualTo("\"HelloWorld\"")
        );
    }

    @Test
    void given_nonMatchingExpectedValue_when_checkingEquality_then_AssertionErrorIsThrown() {
        assertThrows(AssertionError.class,
            () -> JsonReflect.endpoint("/post-reflecting-body")
                .body(JSON_OBJECT)
                .when()
                .post()
                .then()
                .getResponseBody()
                .valueOf(".data.nestedObject.value")
                .isEqualTo("1234")
        );
    }

    @Test
    void when_assignToIsCalled_then_valueIsStoredInJsonValueContainer() {
        final JsonValueContainer jsonValueContainer = new JsonValueContainer();

        JsonReflect.endpoint("/post-reflecting-body")
            .body(JSON_OBJECT)
            .when()
            .post()
            .then()
            .getResponseBody()
            .valueOf(".data.nestedObject.value")
            .assignTo(jsonValueContainer);

        assertThat(jsonValueContainer.getValue()).isEqualTo(new JsonPrimitive(5432));
    }
}
