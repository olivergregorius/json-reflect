package dev.gregorius.library.json.reflect.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.hamcrest.Matcher;
import org.springframework.http.ResponseEntity;

import static org.hamcrest.Matchers.equalTo;

@RequiredArgsConstructor
@Getter(AccessLevel.PACKAGE)
public class ApiResponse {

    private final ResponseEntity<String> responseEntity;

    /**
     * Syntactic sugar for improving readability - initiating the "then"-part.
     *
     * @return this {@link ApiResponse} instance
     */
    public ApiResponse then() {
        return this;
    }

    /**
     * Syntactic sugar for improving readability.
     *
     * @return this {@link ApiResponse} instance
     */
    public ApiResponse and() {
        return this;
    }

    /**
     * Asserts the HTTP status code to equal the expected value.
     *
     * @param expectedStatusCode the expected status code value
     * @return this {@link ApiResponse} instance
     * @throws AssertionError if the HTTP status code does not match the expected value
     */
    public ApiResponse httpStatusCodeIs(final Integer expectedStatusCode) throws AssertionError {
        final Matcher<Integer> matcher = equalTo(expectedStatusCode);
        if (!matcher.matches(responseEntity.getStatusCodeValue())) {
            final String errorMessage = String.format("Expected 'status code' to be equal.%nActual  : %s%nExpected: %s%n", responseEntity.getStatusCodeValue(), expectedStatusCode);
            throw new AssertionError(errorMessage);
        }

        return this;
    }

    /**
     * Returns the response body.
     *
     * @return a new {@link ResponseBody} instance containing the response body
     */
    public ResponseBody getResponseBody() {
        return new ResponseBody(responseEntity.getBody());
    }
}
