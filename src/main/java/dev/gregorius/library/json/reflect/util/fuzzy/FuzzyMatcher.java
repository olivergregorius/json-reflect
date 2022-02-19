package dev.gregorius.library.json.reflect.util.fuzzy;

import com.google.gson.JsonElement;

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
    Predicate<JsonElement> getPredicate();

    /**
     * Performs fuzzy match for the given value.
     *
     * @param jsonElement the value to be fuzzy matched
     * @return {@code true} if the value matched and {@code false} otherwise
     */
    default boolean matches(final JsonElement jsonElement) {
        return getPredicate().test(jsonElement);
    }
}
