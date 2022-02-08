package dev.gregorius.library.json.reflect.model;

import dev.gregorius.library.json.reflect.setup.BaseTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class ApiResponseTest extends BaseTest {

    @Test
    void when_callingGetReturning200_then_httpStatusCodeIs200ShouldBeHappy() {
        JsonReflect.endpoint("/get-returning-200")
            .when()
            .get()
            .then()
            .httpStatusCodeIs(200);
    }

    @Test
    void when_callingGetReturning200_then_httpStatusCodeIs404ShouldFail() {
        assertThrows(AssertionError.class,
            () -> JsonReflect.endpoint("/get-returning-200")
                .when()
                .get()
                .then()
                .httpStatusCodeIs(404));
    }
}
