package dev.gregorius.library.json.reflect.model;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import dev.gregorius.library.json.reflect.util.AssertionUtil;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ResponseBody {

    private final String content;

    /**
     * Syntactic sugar for improving readability.
     *
     * @return this {@link ResponseBody} instance
     */
    public ResponseBody and() {
        return this;
    }

    /**
     * Returns the response body.
     *
     * @return the response body as {@link String}
     */
    public final String getAsString() {
        return content;
    }

    final JsonElement getAsJsonElement() {
        return JsonParser.parseString(content);
    }

    /**
     * Asserts the body content to equal the expected value.
     *
     * @param expectedResponseBody the expected value
     * @return this {@link ResponseBody} instance
     * @throws AssertionError if the body content is not equal to the expected value
     */
    public final ResponseBody isEqualTo(final String expectedResponseBody) throws AssertionError {
        final JsonElement expectedJsonElement = JsonParser.parseString(expectedResponseBody);

        AssertionUtil.assertEqualJsonElements(getAsJsonElement(), expectedJsonElement);

        return this;
    }

    /**
     * Extracts the value specified by the given JSON path from the response body.
     *
     * @param jmesPath the JMESPath expression of the value to be extracted
     * @return a new {@link ResponseBodyField} instance containing the extracted value
     */
    public ResponseBodyField valueOf(final String jmesPath) {
        return new ResponseBodyField(this, jmesPath);
    }
}
