package dev.gregorius.library.json.reflect.util.fuzzy;

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
            Arguments.of(10.4f),
            Arguments.of(10.12345),
            Arguments.of(-123.12),
            Arguments.of(Double.MAX_VALUE)
        );
    }

    @Override
    protected List<Arguments> nonMatchingArguments() {
        return List.of(
            Arguments.of(10),
            Arguments.of(0),
            Arguments.of(-1),
            Arguments.of("10.4"),
            Arguments.of(true)
        );
    }
}
