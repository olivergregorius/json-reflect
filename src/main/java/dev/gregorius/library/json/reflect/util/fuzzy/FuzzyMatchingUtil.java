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
