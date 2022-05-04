package dev.gregorius.library.json.reflect.util.fuzzy;

import com.google.gson.JsonElement;

import java.util.function.Predicate;

public class NotNullMatcher implements FuzzyMatcher {

    @Override
    public String getFuzzyTag() {
        return "#notnull";
    }

    @Override
    public Predicate<JsonElement> getPredicate() {
        return value -> value != null && !value.isJsonNull();
    }
}
