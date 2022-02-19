package dev.gregorius.library.json.reflect.util.fuzzy;

import com.google.gson.JsonElement;

import java.util.function.Predicate;

public class FloatMatcher implements FuzzyMatcher {

    @Override
    public String getFuzzyTag() {
        return "#float";
    }

    @Override
    public Predicate<JsonElement> getPredicate() {
        return value -> {
            if (!value.isJsonPrimitive() || !value.getAsJsonPrimitive().isNumber()) {
                return false;
            }

            return value.getAsNumber() instanceof Float || value.getAsNumber() instanceof Double;
        };
    }
}
