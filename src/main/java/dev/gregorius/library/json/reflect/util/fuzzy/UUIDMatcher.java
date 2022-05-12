package dev.gregorius.library.json.reflect.util.fuzzy;

import com.google.gson.JsonElement;

import java.util.UUID;
import java.util.function.Predicate;

public class UUIDMatcher extends AbstractFuzzyMatcher {

    @Override
    public String getFuzzyTag() {
        return "#uuid";
    }

    @Override
    public Predicate<JsonElement> getPredicate() {
        return value -> {
            if (!new StringMatcher().matches(value)) {
                return false;
            }

            try {
                UUID.fromString(value.getAsString());
            } catch (final IllegalArgumentException e) {
                return false;
            }

            return true;
        };
    }
}
