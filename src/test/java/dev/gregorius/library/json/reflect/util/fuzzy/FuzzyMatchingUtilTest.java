package dev.gregorius.library.json.reflect.util.fuzzy;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class FuzzyMatchingUtilTest {

    @Test
    void given_nonJsonPrimitiveValue_when_getFuzzyMatcher_then_noFuzzyMatcherIsReturned() {
        assertThat(FuzzyMatchingUtil.getFuzzyMatcher(new JsonObject())).isEmpty();
    }

    @Test
    void given_invalidFuzzyTag_when_getFuzzyMatcher_then_noFuzzyMatcherIsReturned() {
        assertThat(FuzzyMatchingUtil.getFuzzyMatcher(new JsonPrimitive("#value"))).isEmpty();
        assertThat(FuzzyMatchingUtil.getFuzzyMatcher(new JsonPrimitive("#fuzzy"))).isEmpty();
        assertThat(FuzzyMatchingUtil.getFuzzyMatcher(new JsonPrimitive("array"))).isEmpty();
        assertThat(FuzzyMatchingUtil.getFuzzyMatcher(new JsonPrimitive("#array ?"))).isEmpty();
    }

    @Test
    void given_validFuzzyTag_when_getFuzzyMatcher_then_FuzzyMatcherIsReturned() {
        assertThat(FuzzyMatchingUtil.getFuzzyMatcher(new JsonPrimitive("#array"))).get().isInstanceOf(ArrayMatcher.class);
        assertThat(FuzzyMatchingUtil.getFuzzyMatcher(new JsonPrimitive("#array?"))).get().isInstanceOf(ArrayMatcher.class);
        assertThat(FuzzyMatchingUtil.getFuzzyMatcher(new JsonPrimitive("#boolean"))).get().isInstanceOf(BooleanMatcher.class);
        assertThat(FuzzyMatchingUtil.getFuzzyMatcher(new JsonPrimitive("#boolean?"))).get().isInstanceOf(BooleanMatcher.class);
        assertThat(FuzzyMatchingUtil.getFuzzyMatcher(new JsonPrimitive("#date"))).get().isInstanceOf(DateMatcher.class);
        assertThat(FuzzyMatchingUtil.getFuzzyMatcher(new JsonPrimitive("#date?"))).get().isInstanceOf(DateMatcher.class);
        assertThat(FuzzyMatchingUtil.getFuzzyMatcher(new JsonPrimitive("#datetime"))).get().isInstanceOf(DateTimeMatcher.class);
        assertThat(FuzzyMatchingUtil.getFuzzyMatcher(new JsonPrimitive("#datetime?"))).get().isInstanceOf(DateTimeMatcher.class);
        assertThat(FuzzyMatchingUtil.getFuzzyMatcher(new JsonPrimitive("#float"))).get().isInstanceOf(FloatMatcher.class);
        assertThat(FuzzyMatchingUtil.getFuzzyMatcher(new JsonPrimitive("#float?"))).get().isInstanceOf(FloatMatcher.class);
        assertThat(FuzzyMatchingUtil.getFuzzyMatcher(new JsonPrimitive("#ignore"))).get().isInstanceOf(IgnoreMatcher.class);
        assertThat(FuzzyMatchingUtil.getFuzzyMatcher(new JsonPrimitive("#ignore?"))).get().isInstanceOf(IgnoreMatcher.class);
        assertThat(FuzzyMatchingUtil.getFuzzyMatcher(new JsonPrimitive("#integer"))).get().isInstanceOf(IntegerMatcher.class);
        assertThat(FuzzyMatchingUtil.getFuzzyMatcher(new JsonPrimitive("#integer?"))).get().isInstanceOf(IntegerMatcher.class);
        assertThat(FuzzyMatchingUtil.getFuzzyMatcher(new JsonPrimitive("#notnull"))).get().isInstanceOf(NotNullMatcher.class);
        assertThat(FuzzyMatchingUtil.getFuzzyMatcher(new JsonPrimitive("#notnull?"))).get().isInstanceOf(NotNullMatcher.class);
        assertThat(FuzzyMatchingUtil.getFuzzyMatcher(new JsonPrimitive("#null"))).get().isInstanceOf(NullMatcher.class);
        assertThat(FuzzyMatchingUtil.getFuzzyMatcher(new JsonPrimitive("#null?"))).get().isInstanceOf(NullMatcher.class);
        assertThat(FuzzyMatchingUtil.getFuzzyMatcher(new JsonPrimitive("#object"))).get().isInstanceOf(ObjectMatcher.class);
        assertThat(FuzzyMatchingUtil.getFuzzyMatcher(new JsonPrimitive("#object?"))).get().isInstanceOf(ObjectMatcher.class);
        assertThat(FuzzyMatchingUtil.getFuzzyMatcher(new JsonPrimitive("#present"))).get().isInstanceOf(PresentMatcher.class);
        assertThat(FuzzyMatchingUtil.getFuzzyMatcher(new JsonPrimitive("#present?"))).get().isInstanceOf(PresentMatcher.class);
        assertThat(FuzzyMatchingUtil.getFuzzyMatcher(new JsonPrimitive("#regex"))).get().isInstanceOf(RegexMatcher.class);
        assertThat(FuzzyMatchingUtil.getFuzzyMatcher(new JsonPrimitive("#regex ^argument$"))).get().isInstanceOf(RegexMatcher.class);
        assertThat(FuzzyMatchingUtil.getFuzzyMatcher(new JsonPrimitive("#regex? ^argument$"))).get().isInstanceOf(RegexMatcher.class);
        assertThat(FuzzyMatchingUtil.getFuzzyMatcher(new JsonPrimitive("#string"))).get().isInstanceOf(StringMatcher.class);
        assertThat(FuzzyMatchingUtil.getFuzzyMatcher(new JsonPrimitive("#string?"))).get().isInstanceOf(StringMatcher.class);
        assertThat(FuzzyMatchingUtil.getFuzzyMatcher(new JsonPrimitive("#time"))).get().isInstanceOf(TimeMatcher.class);
        assertThat(FuzzyMatchingUtil.getFuzzyMatcher(new JsonPrimitive("#time?"))).get().isInstanceOf(TimeMatcher.class);
        assertThat(FuzzyMatchingUtil.getFuzzyMatcher(new JsonPrimitive("#uuid"))).get().isInstanceOf(UUIDMatcher.class);
        assertThat(FuzzyMatchingUtil.getFuzzyMatcher(new JsonPrimitive("#uuid?"))).get().isInstanceOf(UUIDMatcher.class);
    }

    @Test
    void given_invalidArgument_when_setArgumentForRegexMatcher_then_IllegalArgumentExceptionIsThrown() {
        final RegexMatcher regexMatcher = new RegexMatcher();
        assertThatThrownBy(() -> regexMatcher.setArgument(new JsonPrimitive(1234))).isInstanceOf(IllegalArgumentException.class);
    }
}
