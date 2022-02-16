package dev.gregorius.library.json.reflect.util.fuzzy;

import java.util.function.Predicate;

public class BooleanMatcher implements FuzzyMatcher {

    @Override
    public String getFuzzyTag() {
        return "#boolean";
    }

    @Override
    public Predicate<Object> getPredicate() {
        return Boolean.class::isInstance;
    }
}
