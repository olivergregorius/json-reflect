package dev.gregorius.library.json.reflect.util.fuzzy;

import com.google.gson.JsonElement;

import java.util.function.Predicate;

public class ArrayMatcher extends AbstractFuzzyMatcher {

    @Override
    public String getFuzzyTag() {
        return "#array";
    }

    @Override
    public Predicate<JsonElement> getPredicate() {
        return JsonElement::isJsonArray;
    }
}
