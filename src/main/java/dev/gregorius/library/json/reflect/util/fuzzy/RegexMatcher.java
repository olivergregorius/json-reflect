package dev.gregorius.library.json.reflect.util.fuzzy;

import com.google.gson.JsonElement;
import org.apache.commons.lang3.StringUtils;

import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexMatcher implements FuzzyMatcher {

    private String regexPattern;

    @Override
    public String getFuzzyTag() {
        return "#regex";
    }

    @Override
    public Predicate<JsonElement> getPredicate() {
        return value -> {
            if (!new StringMatcher().matches(value)) {
                return false;
            }

            final Pattern pattern = Pattern.compile(regexPattern);
            final Matcher matcher = pattern.matcher(value.getAsString());
            return matcher.matches();
        };
    }

    @Override
    public void setArgument(final JsonElement fuzzyMatchingString) {
        if (!new StringMatcher().matches(fuzzyMatchingString)) {
            throw new IllegalArgumentException("Expected String argument for RegexMatcher");
        }

        this.regexPattern = StringUtils.replace(fuzzyMatchingString.getAsString(), getFuzzyTag(), StringUtils.EMPTY).trim();
    }
}
