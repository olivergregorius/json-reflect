package dev.gregorius.library.json.reflect.util.fuzzy;

import com.google.gson.JsonElement;

import java.util.function.Predicate;

/**
 * Pseudo matcher for key presence check
 */
public class PresentMatcher implements FuzzyMatcher {

    @Override
    public String getFuzzyTag() {
        return "#present";
    }

    @Override
    public Predicate<JsonElement> getPredicate() {
        return value -> true;
    }
}
