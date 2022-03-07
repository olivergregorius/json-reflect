package dev.gregorius.library.json.reflect.model;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import dev.gregorius.library.json.reflect.util.AssertionUtil;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ResponseBody {

    final String content;

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

    public final ResponseBody isEqualTo(final String expectedResponseBody) {
        final JsonElement actualJsonElement = JsonParser.parseString(content);
        final JsonElement expectedJsonElement = JsonParser.parseString(expectedResponseBody);

        AssertionUtil.assertEqualJsonElements(actualJsonElement, expectedJsonElement);

        return this;
    }
}
