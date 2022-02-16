package dev.gregorius.library.json.reflect.util.fuzzy;

import java.util.function.Predicate;

public class IntegerMatcher extends FuzzyMatcher {

    @Override
    public String getFuzzyTag() {
        return "#integer";
    }

    @Override
    public Predicate<Object> getPredicate() {
        return value -> value instanceof Integer || value instanceof Long;
    }
}
