package dev.gregorius.library.json.reflect.util.fuzzy;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.junit.jupiter.params.provider.Arguments;

import java.util.List;

class ArrayMatcherTest extends FuzzyMatcherTest {

    @Override
    protected FuzzyMatcher fuzzyMatcherUnderTest() {
        return new ArrayMatcher();
    }

    @Override
    protected String expectedFuzzyTag() {
        return "#array";
    }

    @Override
    protected List<Arguments> matchingArguments() {
        return List.of(
            Arguments.of(new JsonArray())
        );
    }

    @Override
    protected List<Arguments> nonMatchingArguments() {
        return List.of(
            Arguments.of(new JsonObject()),
            Arguments.of("[]"),
            Arguments.of((Object) new String[]{"first"})
        );
    }
}
