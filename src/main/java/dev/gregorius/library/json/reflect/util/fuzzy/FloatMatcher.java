package dev.gregorius.library.json.reflect.util.fuzzy;

import java.util.function.Predicate;

public class FloatMatcher implements FuzzyMatcher {

    @Override
    public String getFuzzyTag() {
        return "#float";
    }

    @Override
    public Predicate<Object> getPredicate() {
        return value -> value instanceof Float || value instanceof Double;
    }
}
