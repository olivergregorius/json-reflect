package dev.gregorius.library.json.reflect.util.fuzzy;

import com.google.gson.JsonElement;

import java.util.function.Predicate;

public class IntegerMatcher implements FuzzyMatcher {

    @Override
    public String getFuzzyTag() {
        return "#integer";
    }

    @Override
    public Predicate<JsonElement> getPredicate() {
        return value -> {
            if (!value.isJsonPrimitive() || !value.getAsJsonPrimitive().isNumber()) {
                return false;
            }

            return value.getAsNumber() instanceof Integer || value.getAsNumber() instanceof Long;
        };
    }
}
