package dev.gregorius.library.json.reflect.util.fuzzy;

import org.apache.commons.lang3.StringUtils;

import java.util.function.Predicate;

/**
 * Pseudo-matcher, always returning true for every value to be matched.
 */
class TrueMatcher implements FuzzyMatcher {

    @Override
    public String getFuzzyTag() {
        return StringUtils.EMPTY;
    }

    @Override
    public Predicate<Object> getPredicate() {
        return value -> true;
    }
}
