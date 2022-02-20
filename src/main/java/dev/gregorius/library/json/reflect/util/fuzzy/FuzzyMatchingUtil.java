package dev.gregorius.library.json.reflect.util.fuzzy;

import com.google.gson.JsonElement;

import java.util.List;
import java.util.Optional;

public class FuzzyMatchingUtil {

    private FuzzyMatchingUtil() {
    }

    private static final List<FuzzyMatcher> FUZZY_MATCHERS = List.of(
        new ArrayMatcher(),
        new BooleanMatcher(),
        new DateMatcher(),
        new DateTimeMatcher(),
        new FloatMatcher(),
        new IntegerMatcher(),
        new NullMatcher(),
        new ObjectMatcher(),
        new StringMatcher(),
        new TimeMatcher(),
        new UUIDMatcher()
    );

    /**
     * Returns the determined FuzzyMatcher for the given value.
     *
     * @param jsonElement the value for which the FuzzyMatcher lookup will be performed
     * @return {@link Optional} containing the {@link FuzzyMatcher} if the lookup was successful and {@link Optional#empty()} otherwise
     */
    public static Optional<FuzzyMatcher> getFuzzyMatcher(final JsonElement jsonElement) {
        if (!new StringMatcher().matches(jsonElement)) {
            return Optional.empty();
        }

        final String potentialFuzzyTag = jsonElement.getAsString();
        return FUZZY_MATCHERS.stream()
            .filter(fuzzyMatcher -> fuzzyMatcher.getFuzzyTag().equals(potentialFuzzyTag))
            .findFirst();
    }
}
