package dev.gregorius.library.json.reflect.util.fuzzy;

import com.google.gson.JsonPrimitive;
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
            Arguments.of(new JsonPrimitive("2021")),
            Arguments.of(new JsonPrimitive("2021-09")),
            Arguments.of(new JsonPrimitive("2021-09-29"))
        );
    }

    @Override
    protected List<Arguments> nonMatchingArguments() {
        return List.of(
            Arguments.of(new JsonPrimitive("2021-09-29T18:34Z")),
            Arguments.of(new JsonPrimitive("18:34Z")),
            Arguments.of(new JsonPrimitive("20210929")),
            Arguments.of(new JsonPrimitive("29.09.2021")),
            Arguments.of(new JsonPrimitive("29-09-2021")),
            Arguments.of(new JsonPrimitive("2021-31-12")),
            Arguments.of(new JsonPrimitive("2021-12-32")),
            Arguments.of(new JsonPrimitive(20210929))
        );
    }
}
