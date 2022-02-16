package dev.gregorius.library.json.reflect.util.fuzzy;

import java.util.List;
import java.util.Optional;

public class FuzzyMatchingUtil {

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
     * @param value the value for which the FuzzyMatcher lookup will be performed
     * @return {@link Optional} containing the {@link FuzzyMatcher} if the lookup was successful, {@link Optional#empty()} otherwise
     */
    public static Optional<FuzzyMatcher> getFuzzyMatcher(final Object value) {
        if (!new StringMatcher().matches(value)) {
            return Optional.empty();
        }

        final String potentialFuzzyTag = (String) value;
        return FUZZY_MATCHERS.stream()
            .filter(fuzzyMatcher -> fuzzyMatcher.getFuzzyTag().equals(potentialFuzzyTag))
            .findFirst();
    }
}
