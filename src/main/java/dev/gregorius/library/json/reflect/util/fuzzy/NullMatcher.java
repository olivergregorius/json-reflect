package dev.gregorius.library.json.reflect.util.fuzzy;

import java.util.function.Predicate;

public class NullMatcher implements FuzzyMatcher {

    @Override
    public String getFuzzyTag() {
        return "#null";
    }

    @Override
    public Predicate<Object> getPredicate() {
        return value -> value == null || (new StringMatcher().matches(value) && value.toString().equalsIgnoreCase("null"));
    }
}
