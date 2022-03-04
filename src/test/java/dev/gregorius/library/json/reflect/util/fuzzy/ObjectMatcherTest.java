package dev.gregorius.library.json.reflect.util.fuzzy;

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
            Arguments.of("{}")
        );
    }

    @Override
    protected List<Arguments> nonMatchingArguments() {
        return List.of(
            Arguments.of("[]"),
            Arguments.of("\"{}\"")
        );
    }
}
