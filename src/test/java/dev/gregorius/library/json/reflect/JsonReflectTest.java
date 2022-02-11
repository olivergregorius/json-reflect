package dev.gregorius.library.json.reflect;

import ch.qos.logback.classic.Level;
import dev.gregorius.library.json.reflect.setup.BaseTest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class JsonReflectTest extends BaseTest {

    @Test
    void given_validJsonBodySet_when_performingApiCallReturningBody_then_requestBodyAndStatusCodeAndResponseBodyAreLogged() {
        final String requestBody = """
            {
                "data": "val1"
            }
            """;
        JsonReflect.endpoint("/post-reflecting-body")
            .given()
            .body(requestBody)
            .when()
            .post();

        assertThat(testLogAppender.containsOnce(Level.INFO, "Request body:\n{\n  \"data\": \"val1\"\n}")).isTrue();
        assertThat(testLogAppender.containsOnce(Level.INFO, "Response status code: 200")).isTrue();
        assertThat(testLogAppender.containsOnce(Level.INFO, "Response body:\n{\n  \"data\": \"val1\"\n}")).isTrue();
    }

    @Test
    void given_noBodySet_when_performingApiCallReturningNoBody_then_noRequestBodyAndStatusCodeAndNoResponseBodyAreLogged() {
        JsonReflect.endpoint("/get-returning-200")
            .given()
            .when()
            .get();

        assertThat(testLogAppender.containsOnce(Level.INFO, "No request body")).isTrue();
        assertThat(testLogAppender.containsOnce(Level.INFO, "Response status code: 200")).isTrue();
        assertThat(testLogAppender.containsOnce(Level.INFO, "No response body")).isTrue();
    }

    @Test
    void given_invalidJsonBodySet_when_performingApiCallReturningNoBody_then_requestBodyAndErrorAndNoResponseBodyAreLogged() {
        final String requestBody = """
            {
                "data": "val1"
            """;
        JsonReflect.endpoint("/get-returning-200")
            .given()
            .body(requestBody)
            .when()
            .get();

        assertThat(testLogAppender.containsOnce(Level.INFO, "Request body:\n{\n    \"data\": \"val1\"")).isTrue();
        assertThat(testLogAppender.containsOnce(Level.ERROR, "Error during logging of request/response body. May not be valid Json.")).isTrue();
        assertThat(testLogAppender.containsOnce(Level.INFO, "No response body")).isTrue();
    }

    @Test
    void given_noBodySet_when_performingApiCallReturningInvalidBody_then_noRequestBodyAndStatusCodeAndErrorAndResponseBodyAreLogged() {
        JsonReflect.endpoint("/get-returning-invalid-json")
            .given()
            .when()
            .get();

        assertThat(testLogAppender.containsMultiple(Level.INFO, "No request body", 2)).isTrue();
        assertThat(testLogAppender.containsOnce(Level.INFO, "Response status code: 200")).isTrue();
        assertThat(testLogAppender.containsOnce(Level.ERROR, "Error during logging of request/response body. May not be valid Json.")).isTrue();
        assertThat(testLogAppender.containsOnce(Level.INFO, "Response body:\n{\n    \"data\": \"val1\"")).isTrue();
    }
}
