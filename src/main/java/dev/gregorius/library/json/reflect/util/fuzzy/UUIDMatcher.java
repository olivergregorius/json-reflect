package dev.gregorius.library.json.reflect.util.fuzzy;

import java.util.UUID;
import java.util.function.Predicate;

public class UUIDMatcher implements FuzzyMatcher {

    @Override
    public String getFuzzyTag() {
        return "#uuid";
    }

    @Override
    public Predicate<Object> getPredicate() {
        return value -> {
            if (!new StringMatcher().matches(value)) {
                return false;
            }

            try {
                UUID.fromString((String) value);
            } catch (final IllegalArgumentException e) {
                return false;
            }

            return true;
        };
    }
}
