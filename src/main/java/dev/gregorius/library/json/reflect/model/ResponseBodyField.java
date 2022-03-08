package dev.gregorius.library.json.reflect.model;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dev.gregorius.library.json.reflect.util.AssertionUtil;
import org.apache.commons.lang3.StringUtils;

public class ResponseBodyField {

    private final ResponseBody responseBody;
    private final String jsonPath;
    private final JsonElement fieldValue;

    ResponseBodyField(final ResponseBody responseBody, final String jsonPath) {
        this.responseBody = responseBody;
        this.jsonPath = jsonPath;
        this.fieldValue = getFieldValue();
    }

    private JsonElement getFieldValue() {
        final JsonElement responseBodyJsonElement = responseBody.getAsJsonElement();
        JsonObject currentNode;
        if (!responseBodyJsonElement.isJsonObject()) {
            throw new IllegalArgumentException("Unable to traverse document as it is no JSON object");
        } else {
            currentNode = responseBodyJsonElement.getAsJsonObject();
        }

        final String[] pathNodes = StringUtils.split(jsonPath, '.');
        for (int level = 0; level < pathNodes.length; level++) {
            final String currentPathNode = pathNodes[level];
            final boolean isLastPathNode = level == pathNodes.length - 1;

            // Each traversed node in the JSON must be of Type JsonObject, instead of the last one which might be of any type
            if (!isLastPathNode && !(currentNode.has(currentPathNode) && currentNode.get(currentPathNode).isJsonObject())) {
                throw new IllegalArgumentException(String.format("The path '%s' is not valid", jsonPath));
            }

            if (isLastPathNode) {
                return currentNode.get(currentPathNode);
            }

            currentNode = currentNode.getAsJsonObject(currentPathNode);
        }

        throw new IllegalArgumentException(String.format("The path '%s' is not valid", jsonPath));
    }

    /**
     * Checks if the actual field value equals the expected value.
     *
     * @param expectedValue the expected value
     * @return the {@link ResponseBody} instance belonging to this field
     * @throws AssertionError if the values are not equal
     */
    public ResponseBody isEqualTo(final String expectedValue) throws AssertionError {
        AssertionUtil.assertEqual(jsonPath, fieldValue, JsonParser.parseString(expectedValue));

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
