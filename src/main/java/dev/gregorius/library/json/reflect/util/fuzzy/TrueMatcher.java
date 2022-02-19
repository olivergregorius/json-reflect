package dev.gregorius.library.json.reflect.util.fuzzy;

import com.google.gson.JsonElement;
import org.apache.commons.lang3.StringUtils;

import java.util.function.Predicate;

/**
 * Pseudo-matcher, always returning true for every value to be matched.
 */
public class TrueMatcher implements FuzzyMatcher {

    @Override
    public String getFuzzyTag() {
        return StringUtils.EMPTY;
    }

    @Override
    public Predicate<JsonElement> getPredicate() {
        return value -> true;
    }
}
