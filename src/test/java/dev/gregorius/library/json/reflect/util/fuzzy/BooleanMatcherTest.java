package dev.gregorius.library.json.reflect.util.fuzzy;

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
            Arguments.of("true"),
            Arguments.of("false")
        );
    }

    @Override
    protected List<Arguments> nonMatchingArguments() {
        return List.of(
            Arguments.of("\"true\""),
            Arguments.of("\"false\""),
            Arguments.of("0"),
            Arguments.of("1"),
            Arguments.of("{}")
        );
    }
}
