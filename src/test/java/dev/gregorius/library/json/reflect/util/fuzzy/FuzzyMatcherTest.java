package dev.gregorius.library.json.reflect.util.fuzzy;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
abstract class FuzzyMatcherTest {

    private static final Gson GSON = new Gson();

    protected abstract FuzzyMatcher fuzzyMatcherUnderTest();

    protected abstract String expectedFuzzyTag();

    protected abstract List<Arguments> matchingArguments();

    protected abstract List<Arguments> nonMatchingArguments();

    @Test
    void when_getFuzzyIdentifier_then_expectedIdentifierIsReturned() {
        assertThat(fuzzyMatcherUnderTest().getFuzzyTag()).isEqualTo(expectedFuzzyTag());
    }

    @ParameterizedTest
    @MethodSource("matchingArguments")
    void given_matchingArgument_when_matches_then_returnTrue(final Object value) {
        final JsonElement jsonElement = GSON.toJsonTree(value);
        assertThat(fuzzyMatcherUnderTest().matches(jsonElement)).isTrue();
    }

    @ParameterizedTest
    @MethodSource("nonMatchingArguments")
    void given_nonMatchingArgument_when_matches_then_returnFalse(final Object value) {
        final JsonElement jsonElement = GSON.toJsonTree(value);
        assertThat(fuzzyMatcherUnderTest().matches(jsonElement)).isFalse();
    }
}
