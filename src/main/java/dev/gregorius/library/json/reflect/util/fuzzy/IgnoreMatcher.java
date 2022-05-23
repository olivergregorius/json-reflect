package dev.gregorius.library.json.reflect.util.fuzzy;

import com.google.gson.JsonElement;

import java.util.function.Predicate;

/**
 * Pseudo matcher for ignore check
 */
public class IgnoreMatcher extends AbstractFuzzyMatcher {

    @Override
    public String getFuzzyTag() {
        return "#ignore";
    }

    @Override
    public Predicate<JsonElement> getPredicate() {
        return value -> true;
    }
}
