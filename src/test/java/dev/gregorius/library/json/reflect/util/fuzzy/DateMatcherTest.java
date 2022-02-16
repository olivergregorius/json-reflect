package dev.gregorius.library.json.reflect.util.fuzzy;

import org.junit.jupiter.params.provider.Arguments;

import java.util.List;

class DateMatcherTest extends FuzzyMatcherTest {

    @Override
    protected FuzzyMatcher fuzzyMatcherUnderTest() {
        return new DateMatcher();
    }

    @Override
    protected String expectedFuzzyTag() {
        return "#date";
    }

    @Override
    protected List<Arguments> matchingArguments() {
        return List.of(
            Arguments.of("2021"),
            Arguments.of("2021-09"),
            Arguments.of("2021-09-29")
        );
    }

    @Override
    protected List<Arguments> nonMatchingArguments() {
        return List.of(
            Arguments.of("2021-09-29T18:34Z"),
            Arguments.of("18:34Z"),
            Arguments.of("20210929"),
            Arguments.of("29.09.2021"),
            Arguments.of("29-09-2021"),
            Arguments.of("2021-31-12"),
            Arguments.of("2021-12-32"),
            Arguments.of(20210929)
        );
    }
}
