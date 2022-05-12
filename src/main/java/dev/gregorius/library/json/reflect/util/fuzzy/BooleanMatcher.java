package dev.gregorius.library.json.reflect.util.fuzzy;

import com.google.gson.JsonElement;

import java.util.function.Predicate;

public class BooleanMatcher extends AbstractFuzzyMatcher {

    @Override
    public String getFuzzyTag() {
        return "#boolean";
    }

    @Override
    public Predicate<JsonElement> getPredicate() {
        return jsonElement -> jsonElement.isJsonPrimitive() && jsonElement.getAsJsonPrimitive().isBoolean();
    }
}
