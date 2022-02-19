package dev.gregorius.library.json.reflect.util.fuzzy;

import com.google.gson.JsonNull;
import org.junit.jupiter.params.provider.Arguments;

import java.util.List;

class NullMatcherTest extends FuzzyMatcherTest {

    @Override
    protected FuzzyMatcher fuzzyMatcherUnderTest() {
        return new NullMatcher();
    }

    @Override
    protected String expectedFuzzyTag() {
        return "#null";
    }

    @Override
    protected List<Arguments> matchingArguments() {
        return List.of(
            Arguments.of(null, ""), // added another dummy value for fulfilling the precondition check
            Arguments.of(JsonNull.INSTANCE)
        );
    }

    @Override
    protected List<Arguments> nonMatchingArguments() {
        return List.of(
            Arguments.of(0),
            Arguments.of("anyString"),
            Arguments.of(false),
            Arguments.of("null"),
            Arguments.of("NuLl")
        );
    }
}
