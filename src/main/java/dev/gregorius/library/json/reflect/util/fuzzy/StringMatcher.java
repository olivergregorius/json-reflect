package dev.gregorius.library.json.reflect.util.fuzzy;

import com.google.gson.JsonElement;

import java.util.function.Predicate;

public class StringMatcher extends AbstractFuzzyMatcher {

    @Override
    public String getFuzzyTag() {
        return "#string";
    }

    @Override
    public Predicate<JsonElement> getPredicate() {
        return value -> value.isJsonPrimitive() && value.getAsJsonPrimitive().isString();
    }
}
