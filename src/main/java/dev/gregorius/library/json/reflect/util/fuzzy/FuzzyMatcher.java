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
     * Sets an additional argument for the FuzzyMatcher to perform the match, if required
     *
     * @param fuzzyMatchingString the String containing the fuzzy matching declaration, passed as {@link JsonElement}
     */
    default void setArgument(final JsonElement fuzzyMatchingString) {
        // Nothing to do by default
    }

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
