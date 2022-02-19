package dev.gregorius.library.json.reflect.util.fuzzy;

import com.google.gson.JsonPrimitive;
import org.junit.jupiter.params.provider.Arguments;

import java.util.List;

class IntegerMatcherTest extends FuzzyMatcherTest {

    @Override
    protected FuzzyMatcher fuzzyMatcherUnderTest() {
        return new IntegerMatcher();
    }

    @Override
    protected String expectedFuzzyTag() {
        return "#integer";
    }

    @Override
    protected List<Arguments> matchingArguments() {
        return List.of(
            Arguments.of(new JsonPrimitive(10)),
            Arguments.of(new JsonPrimitive(0)),
            Arguments.of(new JsonPrimitive(-1)),
            Arguments.of(new JsonPrimitive(Long.MAX_VALUE))
        );
    }

    @Override
    protected List<Arguments> nonMatchingArguments() {
        return List.of(
            Arguments.of(new JsonPrimitive(10.4)),
            Arguments.of(new JsonPrimitive(10.12345)),
            Arguments.of(new JsonPrimitive(-123.12)),
            Arguments.of(new JsonPrimitive("10")),
            Arguments.of(new JsonPrimitive(true))
        );
    }
}
