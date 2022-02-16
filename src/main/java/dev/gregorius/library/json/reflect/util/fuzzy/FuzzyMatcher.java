package dev.gregorius.library.json.reflect.util.fuzzy;

import java.util.function.Predicate;

public interface FuzzyMatcher {

    /**
     * Returns the fuzzy tag of the FuzzyMatcher.
     *
     * @return the fuzzy tag of the matcher as {@link String}
     */
    String getFuzzyTag();

    /**
     * Returns the matching-Predicate of the FuzzyMatcher.
     *
     * @return the matching-{@link Predicate} of the matcher
     */
    Predicate<Object> getPredicate();

    /**
     * Performs fuzzy match for the given value.
     *
     * @param object the value to be fuzzy matched
     * @return true if the value matched, false otherwise
     */
    default boolean matches(final Object object) {
        return getPredicate().test(object);
    }
}
