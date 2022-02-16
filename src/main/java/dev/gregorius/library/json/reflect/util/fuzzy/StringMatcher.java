package dev.gregorius.library.json.reflect.util.fuzzy;

import java.util.function.Predicate;

public class StringMatcher implements FuzzyMatcher {

    @Override
    public String getFuzzyTag() {
        return "#string";
    }

    @Override
    public Predicate<Object> getPredicate() {
        return String.class::isInstance;
    }
}
