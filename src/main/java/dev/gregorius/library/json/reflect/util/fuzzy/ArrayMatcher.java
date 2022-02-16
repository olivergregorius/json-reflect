package dev.gregorius.library.json.reflect.util.fuzzy;

import com.google.gson.JsonArray;

import java.util.function.Predicate;

public class ArrayMatcher extends FuzzyMatcher {

    @Override
    public String getFuzzyTag() {
        return "#array";
    }

    @Override
    public Predicate<Object> getPredicate() {
        return JsonArray.class::isInstance;
    }
}
