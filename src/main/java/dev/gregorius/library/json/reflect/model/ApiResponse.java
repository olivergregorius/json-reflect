package dev.gregorius.library.json.reflect.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

import static dev.gregorius.library.json.reflect.util.AssertionUtil.assertEqual;

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
     */
    public ApiResponse httpStatusCodeIs(final Integer expectedStatusCode) {
        assertEqual("status code", responseEntity.getStatusCodeValue(), expectedStatusCode);

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
