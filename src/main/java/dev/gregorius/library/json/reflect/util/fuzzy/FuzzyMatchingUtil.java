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
        new IgnoreMatcher(),
        new IntegerMatcher(),
        new NotNullMatcher(),
        new NullMatcher(),
        new ObjectMatcher(),
        new PresentMatcher(),
        new StringMatcher(),
        new TimeMatcher(),
        new UUIDMatcher()
    );

    private static final List<FuzzyMatcher> FUZZY_MATCHERS_WITH_ARGUMENT = List.of(
        new RegexMatcher()
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

        final String fuzzyExpression = jsonElement.getAsString();

        // Look up FuzzyMatcher with fuzzy expression matching the fuzzy tag exactly
        final Optional<FuzzyMatcher> fuzzyMatcherOptional = FUZZY_MATCHERS.stream()
            .filter(fuzzyMatcher -> fuzzyExpression.equalsIgnoreCase(fuzzyMatcher.getFuzzyTag()))
            .findFirst();

        if (fuzzyMatcherOptional.isPresent()) {
            return fuzzyMatcherOptional;
        }

        // Look up FuzzyMatcher with fuzzy expression containing the fuzzy tag additional to the given arguments for the FuzzyMatcher
        return FUZZY_MATCHERS_WITH_ARGUMENT.stream()
            .filter(fuzzyMatcherWithArgument -> fuzzyExpression.contains(fuzzyMatcherWithArgument.getFuzzyTag()))
            .findFirst();
    }
}
