package dev.gregorius.library.json.reflect.util.fuzzy;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import org.junit.jupiter.params.provider.Arguments;

import java.util.List;

class BooleanMatcherTest extends FuzzyMatcherTest {

    @Override
    protected FuzzyMatcher fuzzyMatcherUnderTest() {
        return new BooleanMatcher();
    }

    @Override
    protected String expectedFuzzyTag() {
        return "#boolean";
    }

    @Override
    protected List<Arguments> matchingArguments() {
        return List.of(
            Arguments.of(new JsonPrimitive(true)),
            Arguments.of(new JsonPrimitive(false)),
            Arguments.of(new JsonPrimitive(Boolean.TRUE)),
            Arguments.of(new JsonPrimitive(Boolean.FALSE))
        );
    }

    @Override
    protected List<Arguments> nonMatchingArguments() {
        return List.of(
            Arguments.of(new JsonPrimitive("true")),
            Arguments.of(new JsonPrimitive("False")),
            Arguments.of(new JsonPrimitive(0)),
            Arguments.of(new JsonPrimitive(1)),
            Arguments.of(new JsonObject())
        );
    }
}
