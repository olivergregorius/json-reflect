package dev.gregorius.library.json.reflect.util.fuzzy;

import com.google.gson.JsonPrimitive;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.provider.Arguments;

import java.util.List;

class RegexMatcherTest extends FuzzyMatcherTest {

    private RegexMatcher regexMatcher;

    @BeforeEach
    void setUp() {
        regexMatcher = new RegexMatcher();
        regexMatcher.setArgument(new JsonPrimitive("#regex ^.*a Message\\s+for you$"));
    }

    @Override
    protected FuzzyMatcher fuzzyMatcherUnderTest() {
        return regexMatcher;
    }

    @Override
    protected String expectedFuzzyTag() {
        return "#regex";
    }

    @Override
    protected List<Arguments> matchingArguments() {
        return List.of(
            Arguments.of("\"This is a Message for you\""),
            Arguments.of("\"That is a Message  for you\""),
            Arguments.of("\"Here is a Message    for you\"")
        );
    }

    @Override
    protected List<Arguments> nonMatchingArguments() {
        return List.of(
            Arguments.of("\"This is a Message for you!\""),
            Arguments.of("\"This is another String\""),
            Arguments.of("\"That is a MessageFor you\""),
            Arguments.of("1"),
            Arguments.of("{}")
        );
    }
}
