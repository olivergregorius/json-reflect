package dev.gregorius.library.json.reflect.util.fuzzy;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class TrueMatcherTest {

    private static final Gson GSON = new Gson();

    protected FuzzyMatcher fuzzyMatcherUnderTest() {
        return new TrueMatcher();
    }

    protected String expectedFuzzyTag() {
        return StringUtils.EMPTY;
    }

    protected static List<Arguments> matchingArguments() {
        return List.of(
            Arguments.of(1),
            Arguments.of("String"),
            Arguments.of(true),
            Arguments.of(new JsonObject()),
            Arguments.of(new JsonArray()),
            Arguments.of(10.0f),
            Arguments.of(UUID.randomUUID().toString())
        );
    }

    @Test
    void when_getFuzzyIdentifier_then_expectedIdentifierIsReturned() {
        assertThat(fuzzyMatcherUnderTest().getFuzzyTag()).isEqualTo(expectedFuzzyTag());
    }

    @ParameterizedTest
    @MethodSource("matchingArguments")
    void given_JsonArray_when_matches_then_returnTrue(final Object value) {
        final JsonElement jsonElement = GSON.toJsonTree(value);
        assertThat(fuzzyMatcherUnderTest().matches(jsonElement)).isTrue();
    }
}
