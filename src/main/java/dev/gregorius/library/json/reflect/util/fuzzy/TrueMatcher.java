package dev.gregorius.library.json.reflect.util.fuzzy;

import org.apache.commons.lang3.StringUtils;

import java.util.function.Predicate;

public class TrueMatcher implements FuzzyMatcher {

    @Override
    public String getFuzzyTag() {
        return StringUtils.EMPTY;
    }

    @Override
    public Predicate<Object> getPredicate() {
        return value -> true;
    }
}
