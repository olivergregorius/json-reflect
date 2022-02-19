package dev.gregorius.library.json.reflect.util.fuzzy;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import org.junit.jupiter.params.provider.Arguments;

import java.util.List;

class ObjectMatcherTest extends FuzzyMatcherTest {

    @Override
    protected FuzzyMatcher fuzzyMatcherUnderTest() {
        return new ObjectMatcher();
    }

    @Override
    protected String expectedFuzzyTag() {
        return "#object";
    }

    @Override
    protected List<Arguments> matchingArguments() {
        return List.of(
            Arguments.of(new JsonObject())
        );
    }

    @Override
    protected List<Arguments> nonMatchingArguments() {
        return List.of(
            Arguments.of(new JsonArray()),
            Arguments.of(new JsonPrimitive("{}"))
        );
    }
}
