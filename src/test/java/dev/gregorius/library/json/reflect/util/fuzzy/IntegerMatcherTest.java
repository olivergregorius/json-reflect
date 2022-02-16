package dev.gregorius.library.json.reflect.util.fuzzy;

import org.junit.jupiter.params.provider.Arguments;

import java.util.List;

public class IntegerMatcherTest extends FuzzyMatcherTest {

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
            Arguments.of(10),
            Arguments.of(0),
            Arguments.of(-1),
            Arguments.of(Long.MAX_VALUE)
        );
    }

    @Override
    protected List<Arguments> nonMatchingArguments() {
        return List.of(
            Arguments.of(10.4),
            Arguments.of(10.12345),
            Arguments.of(-123.12),
            Arguments.of("10"),
            Arguments.of(true)
        );
    }
}
