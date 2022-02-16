package dev.gregorius.library.json.reflect.util.fuzzy;

import java.util.function.Predicate;

public abstract class FuzzyMatcher {

    public abstract String getFuzzyTag();

    public abstract Predicate<Object> getPredicate();

    public final boolean matches(final Object object) {
        return getPredicate().test(object);
    }
}
