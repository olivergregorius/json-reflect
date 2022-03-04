package dev.gregorius.library.json.reflect.util.fuzzy;

import org.junit.jupiter.params.provider.Arguments;

import java.util.List;

class StringMatcherTest extends FuzzyMatcherTest {

    @Override
    protected FuzzyMatcher fuzzyMatcherUnderTest() {
        return new StringMatcher();
    }

    @Override
    protected String expectedFuzzyTag() {
        return "#string";
    }

    @Override
    protected List<Arguments> matchingArguments() {
        return List.of(
            Arguments.of("\"string\""),
            Arguments.of("\"bf53bd42-1c0c-44c4-b94c-cc4d2a27bb99\"")
        );
    }

    @Override
    protected List<Arguments> nonMatchingArguments() {
        return List.of(
            Arguments.of("1234"),
            Arguments.of("true"),
            Arguments.of("{}")
        );
    }
}
