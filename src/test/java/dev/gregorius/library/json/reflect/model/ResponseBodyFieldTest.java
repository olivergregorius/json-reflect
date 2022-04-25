package dev.gregorius.library.json.reflect.model;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import dev.gregorius.library.json.reflect.JsonReflect;
import dev.gregorius.library.json.reflect.setup.BaseTest;
import io.burt.jmespath.parser.ParseException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ResponseBodyFieldTest extends BaseTest {

    private static final String JSON_OBJECT = """
        {
          "data": {
            "nestedObject": {
              "value": 5432,
              "anotherValue": "HelloWorld"
            }
          },
          "value": 1234,
          "array": [
            {
              "id": "0001",
              "value": "first value",
              "category": "one"
            },
            {
              "id": "0002",
              "value": "second value",
              "category": "two"
            },
            {
              "id": "0003",
              "value": "third value",
              "category": "one"
            }
          ]
        }
        """;

    private static Stream<Arguments> jmesPathMappings() {
        return Stream.of(
            Arguments.of("data.nestedObject.value", "5432"),
            Arguments.of("data.nestedObject.anotherValue", "HelloWorld"),
            Arguments.of("value", "1234"),
            Arguments.of("array[1]", """
                {
                  "id": "0002",
                  "value": "second value",
                  "category": "two"
                }
                """),
            Arguments.of("array[?id == '0002']", """
                [
                  {
                    "id": "0002",
                    "value": "second value",
                    "category": "two"
                  }
                ]
                """),
            Arguments.of("array[?category == 'one']", """
                [
                  {
                    "id": "0001",
                    "value": "first value",
                    "category": "one"
                  },
                  {
                    "id": "0003",
                    "value": "third value",
                    "category": "one"
                  }
                ]
                """)
        );
    }

    @Test
    void given_invalidJmesPath_when_creatingResponseBodyField_then_ParseExceptionIsThrown() {
        final Throwable exception = assertThrows(ParseException.class,
            () -> JsonReflect.endpoint("/post-reflecting-body")
                .body(JSON_OBJECT)
                .when()
                .post()
                .then()
                .responseBody()
                .valueOf("$.data.nestedObject.value"));

        assertThat(exception.getMessage()).startsWith("Unable to compile expression \"$.data.nestedObject.value\"");
    }

    @ParameterizedTest
    @MethodSource("jmesPathMappings")
    void given_validJmesPath_when_creatingResponseBodyField_then_extractedValueIsAsExpected(final String jmesPath, final String resultJson) {
        final ResponseBody responseBody = JsonReflect.endpoint("/post-reflecting-body")
            .body(JSON_OBJECT)
            .when()
            .post()
            .then()
            .responseBody();

        final JsonElement fieldValue = responseBody.valueOf(jmesPath).getValue();
        assertThat(fieldValue).isEqualTo(JsonParser.parseString(resultJson));
    }

    @Test
    void given_matchingExpectedValue_when_checkingEquality_then_beHappy() {
        assertDoesNotThrow(
            () -> JsonReflect.endpoint("/post-reflecting-body")
                .body(JSON_OBJECT)
                .when()
                .post()
                .then()
                .responseBody()
                .valueOf("data.nestedObject.value")
                .isEqualTo("5432")
        );

        assertDoesNotThrow(
            () -> JsonReflect.endpoint("/post-reflecting-body")
                .body(JSON_OBJECT)
                .when()
                .post()
                .then()
                .responseBody()
                .valueOf("data.nestedObject.anotherValue")
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
                .responseBody()
                .valueOf("data.nestedObject.value")
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
            .responseBody()
            .valueOf("data.nestedObject.value")
            .assignTo(jsonValueContainer);

        assertThat(jsonValueContainer.getValue()).isEqualTo(new JsonPrimitive(5432));
    }
}
