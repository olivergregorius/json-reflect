package dev.gregorius.library.json.reflect.util.fuzzy;

import com.google.gson.JsonNull;
import org.junit.jupiter.params.provider.Arguments;

import java.util.List;

class NotNullMatcherTest extends FuzzyMatcherTest {

    @Override
    protected FuzzyMatcher fuzzyMatcherUnderTest() {
        return new NotNullMatcher();
    }

    @Override
    protected String expectedFuzzyTag() {
        return "#notnull";
    }

    @Override
    protected List<Arguments> matchingArguments() {
        return List.of(
            Arguments.of("\"null\""),
            Arguments.of("0"),
            Arguments.of("\"anyString\""),
            Arguments.of("false")
        );
    }

    @Override
    protected List<Arguments> nonMatchingArguments() {
        return List.of(
            Arguments.of("null"),
            Arguments.of("NuLl"),
            Arguments.of(JsonNull.INSTANCE.toString())
        );
    }
}
