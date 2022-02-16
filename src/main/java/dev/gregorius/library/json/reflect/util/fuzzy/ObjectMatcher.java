package dev.gregorius.library.json.reflect.util.fuzzy;

import com.google.gson.JsonObject;

import java.util.function.Predicate;

public class ObjectMatcher implements FuzzyMatcher {

    @Override
    public String getFuzzyTag() {
        return "#object";
    }

    @Override
    public Predicate<Object> getPredicate() {
        return JsonObject.class::isInstance;
    }
}
