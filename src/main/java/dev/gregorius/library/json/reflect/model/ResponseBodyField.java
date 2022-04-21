package dev.gregorius.library.json.reflect.model;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import dev.gregorius.library.json.reflect.util.AssertionUtil;
import io.burt.jmespath.Expression;
import io.burt.jmespath.JmesPath;
import io.burt.jmespath.gson.GsonRuntime;

public class ResponseBodyField {

    private final ResponseBody responseBody;
    private final String jmesPath;
    private final JsonElement fieldValue;

    ResponseBodyField(final ResponseBody responseBody, final String jmesPath) {
        this.responseBody = responseBody;
        this.jmesPath = jmesPath;
        this.fieldValue = getFieldValue();
    }

    private JsonElement getFieldValue() {
        final JmesPath<JsonElement> jmesPathRuntime = new GsonRuntime();
        final Expression<JsonElement> expression = jmesPathRuntime.compile(jmesPath);
        final JsonElement responseBodyJsonElement = responseBody.getAsJsonElement();
        return expression.search(responseBodyJsonElement);
    }

    /**
     * Checks if the actual field value equals the expected value.
     *
     * @param expectedValue the expected value
     * @return the {@link ResponseBody} instance belonging to this field
     * @throws AssertionError if the values are not equal
     */
    public ResponseBody isEqualTo(final String expectedValue) throws AssertionError {
        AssertionUtil.assertEqualJsonElements(fieldValue, JsonParser.parseString(expectedValue));

        return responseBody;
    }

    /**
     * Returns the field value.
     *
     * @return the field value as {@link JsonElement}
     */
    public JsonElement getValue() {
        return fieldValue;
    }

    /**
     * Assigns the field value to a JsonValueContainer for further usage.
     *
     * @param jsonValueContainer a {@link JsonValueContainer} instance to hold the field value
     * @return the {@link ResponseBody} instance belonging to this field
     */
    public ResponseBody assignTo(final JsonValueContainer jsonValueContainer) {
        jsonValueContainer.setValue(fieldValue);

        return responseBody;
    }
}
