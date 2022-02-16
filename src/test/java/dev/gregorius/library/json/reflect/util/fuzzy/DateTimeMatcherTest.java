package dev.gregorius.library.json.reflect.util.fuzzy;

import org.junit.jupiter.params.provider.Arguments;

import java.util.List;

public class DateTimeMatcherTest extends FuzzyMatcherTest {

    @Override
    protected FuzzyMatcher fuzzyMatcherUnderTest() {
        return new DateTimeMatcher();
    }

    @Override
    protected String expectedFuzzyTag() {
        return "#datetime";
    }

    @Override
    protected List<Arguments> matchingArguments() {
        return List.of(
            Arguments.of("2021-09-29T18:34Z"),
            Arguments.of("2021-09-29T18:34+02:00"),
            Arguments.of("2021-09-29T18:34-02:00"),
            Arguments.of("2021-09-29T18:34:56Z"),
            Arguments.of("2021-09-29T18:34:56+03:00"),
            Arguments.of("2021-09-29T18:34:56-03:00"),
            Arguments.of("2021-09-29T18:34:56.1Z"),
            Arguments.of("2021-09-29T18:34:56.12Z"),
            Arguments.of("2021-09-29T18:34:56.123Z"),
            Arguments.of("2021-09-29T18:34:56.1234Z"),
            Arguments.of("2021-09-29T18:34:56.12345Z"),
            Arguments.of("2021-09-29T18:34:56.123456Z"),
            Arguments.of("2021-09-29T18:34:56.1234567Z"),
            Arguments.of("2021-09-29T18:34:56.12345678Z"),
            Arguments.of("2021-09-29T18:34:56.123456789Z"),
            Arguments.of("2021-09-29T18:34:56.1+03:00"),
            Arguments.of("2021-09-29T18:34:56.12+03:00"),
            Arguments.of("2021-09-29T18:34:56.123+03:00"),
            Arguments.of("2021-09-29T18:34:56.1234+03:00"),
            Arguments.of("2021-09-29T18:34:56.12345+03:00"),
            Arguments.of("2021-09-29T18:34:56.123456+03:00"),
            Arguments.of("2021-09-29T18:34:56.1234567+03:00"),
            Arguments.of("2021-09-29T18:34:56.12345678+03:00"),
            Arguments.of("2021-09-29T18:34:56.123456789+03:00")
        );
    }

    @Override
    protected List<Arguments> nonMatchingArguments() {
        return List.of(
            Arguments.of("2021-09-29"),
            Arguments.of("18:34:56Z"),
            Arguments.of("2021-13-10T18:34Z"),
            Arguments.of("2021-09-32T18:34Z"),
            Arguments.of("2021-09-29T25:34Z"),
            Arguments.of("2021-09-29T23:65Z"),
            Arguments.of("2021-09-2918:34:56Z"),
            Arguments.of("2021-09-29T18:34:56"),
            Arguments.of("2021-09-29T18:34:56.1234567890Z"),
            Arguments.of(20211834)
        );
    }
}
