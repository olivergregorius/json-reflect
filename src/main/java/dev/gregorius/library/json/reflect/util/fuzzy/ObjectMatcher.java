package dev.gregorius.library.json.reflect.util.fuzzy;

import com.google.gson.JsonElement;

import java.util.function.Predicate;

public class ObjectMatcher extends AbstractFuzzyMatcher {

    @Override
    public String getFuzzyTag() {
        return "#object";
    }

    @Override
    public Predicate<JsonElement> getPredicate() {
        return JsonElement::isJsonObject;
    }
}
