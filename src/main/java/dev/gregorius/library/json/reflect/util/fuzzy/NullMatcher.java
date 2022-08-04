package dev.gregorius.library.json.reflect.util.fuzzy;

import com.google.gson.JsonElement;

import java.util.function.Predicate;

public class NullMatcher extends AbstractFuzzyMatcher {

    @Override
    public String getFuzzyTag() {
        return "#null";
    }

    @Override
    public Predicate<JsonElement> getPredicate() {
        return value -> value == null || value.isJsonNull();
    }
}
