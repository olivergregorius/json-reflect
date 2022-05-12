package dev.gregorius.library.json.reflect.util.fuzzy;

import com.google.gson.JsonElement;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Optional;

public class FuzzyMatchingUtil {

    private FuzzyMatchingUtil() {
    }

    private static final List<FuzzyMatcher> FUZZY_MATCHERS = List.of(
        new ArrayMatcher(),
        new BooleanMatcher(),
        new DateMatcher(),
        new DateTimeMatcher(),
        new FloatMatcher(),
        new IgnoreMatcher(),
        new IntegerMatcher(),
        new NotNullMatcher(),
        new NullMatcher(),
        new ObjectMatcher(),
        new PresentMatcher(),
        new StringMatcher(),
        new TimeMatcher(),
        new UUIDMatcher()
    );

    private static final List<FuzzyMatcher> FUZZY_MATCHERS_WITH_ARGUMENT = List.of(
        new RegexMatcher()
    );

    /**
     * Returns the determined FuzzyMatcher for the given value.
     *
     * @param jsonElement the value for which the FuzzyMatcher lookup will be performed
     * @return {@link Optional} containing the {@link FuzzyMatcher} if the lookup was successful and {@link Optional#empty()} otherwise
     */
    public static Optional<FuzzyMatcher> getFuzzyMatcher(final JsonElement jsonElement) {
        if (!new StringMatcher().matches(jsonElement)) {
            return Optional.empty();
        }

        final String fuzzyExpression = jsonElement.getAsString();
        final String optionalMarker = StringUtils.contains(fuzzyExpression, FuzzyMatcher.OPTIONAL_MARKER) ? FuzzyMatcher.OPTIONAL_MARKER : StringUtils.EMPTY;

        // Look up FuzzyMatcher with fuzzy expression matching the fuzzy tag exactly
        Optional<FuzzyMatcher> fuzzyMatcherOptional = FUZZY_MATCHERS.stream()
            .filter(fuzzyMatcher -> fuzzyExpression.equalsIgnoreCase(fuzzyMatcher.getFuzzyTag() + optionalMarker))
            .findFirst();

        if (fuzzyMatcherOptional.isPresent()) {
            // If a FuzzyMatcher was found with optional-marker then set the FuzzyMatcher's optional flag to true
            if (StringUtils.isNotEmpty(optionalMarker)) {
                fuzzyMatcherOptional.get().setOptional(true);
            }
            return fuzzyMatcherOptional;
        }

        // Look up FuzzyMatcher with fuzzy expression containing the fuzzy tag additional to the given arguments for the FuzzyMatcher. We cannot look up the
        // FuzzyMatcher with the optional-marker as suffix as the argument could also include the optional marker - leading to unexpected results. The presence
        // check of the optional-marker for setting the optional flag is done in the next step.
        fuzzyMatcherOptional = FUZZY_MATCHERS_WITH_ARGUMENT.stream()
            .filter(fuzzyMatcherWithArgument -> fuzzyExpression.contains(fuzzyMatcherWithArgument.getFuzzyTag()))
            .findFirst();

        // If a FuzzyMatcher was found with optional-marker then set the FuzzyMatcher's optional flag to true.
        if (fuzzyMatcherOptional.isPresent() && fuzzyExpression.contains(fuzzyMatcherOptional.get().getFuzzyTag() + FuzzyMatcher.OPTIONAL_MARKER)) {
            fuzzyMatcherOptional.get().setOptional(true);
        }

        return fuzzyMatcherOptional;
    }
}
