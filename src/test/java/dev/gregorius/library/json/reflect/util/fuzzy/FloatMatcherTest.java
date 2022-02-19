package dev.gregorius.library.json.reflect.util.fuzzy;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import org.junit.jupiter.params.provider.Arguments;

import java.util.List;

class FloatMatcherTest extends FuzzyMatcherTest {

    @Override
    protected FuzzyMatcher fuzzyMatcherUnderTest() {
        return new FloatMatcher();
    }

    @Override
    protected String expectedFuzzyTag() {
        return "#float";
    }

    @Override
    protected List<Arguments> matchingArguments() {
        return List.of(
            Arguments.of(new JsonPrimitive(10.4f)),
            Arguments.of(new JsonPrimitive(10.12345)),
            Arguments.of(new JsonPrimitive(-123.12)),
            Arguments.of(new JsonPrimitive(Double.MAX_VALUE))
        );
    }

    @Override
    protected List<Arguments> nonMatchingArguments() {
        return List.of(
            Arguments.of(new JsonPrimitive(10)),
            Arguments.of(new JsonPrimitive(0)),
            Arguments.of(new JsonPrimitive(-1)),
            Arguments.of(new JsonPrimitive("10.4")),
            Arguments.of(new JsonPrimitive(true)),
            Arguments.of(new JsonObject())
        );
    }
}
