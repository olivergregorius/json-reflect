package dev.gregorius.library.json.reflect.util.fuzzy;

import java.util.function.Predicate;

public interface FuzzyMatcher {

    String getFuzzyTag();

    Predicate<Object> getPredicate();

    default boolean matches(final Object object) {
        return getPredicate().test(object);
    }
}
