package dev.gregorius.library.json.reflect.util.fuzzy;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

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
    }

    @Test
    void given_validFuzzyTag_when_getFuzzyMatcher_then_FuzzyMatcherIsReturned() {
        assertThat(FuzzyMatchingUtil.getFuzzyMatcher(new JsonPrimitive("#array"))).get().isInstanceOf(ArrayMatcher.class);
        assertThat(FuzzyMatchingUtil.getFuzzyMatcher(new JsonPrimitive("#boolean"))).get().isInstanceOf(BooleanMatcher.class);
        assertThat(FuzzyMatchingUtil.getFuzzyMatcher(new JsonPrimitive("#date"))).get().isInstanceOf(DateMatcher.class);
        assertThat(FuzzyMatchingUtil.getFuzzyMatcher(new JsonPrimitive("#datetime"))).get().isInstanceOf(DateTimeMatcher.class);
        assertThat(FuzzyMatchingUtil.getFuzzyMatcher(new JsonPrimitive("#float"))).get().isInstanceOf(FloatMatcher.class);
        assertThat(FuzzyMatchingUtil.getFuzzyMatcher(new JsonPrimitive("#integer"))).get().isInstanceOf(IntegerMatcher.class);
        assertThat(FuzzyMatchingUtil.getFuzzyMatcher(new JsonPrimitive("#null"))).get().isInstanceOf(NullMatcher.class);
        assertThat(FuzzyMatchingUtil.getFuzzyMatcher(new JsonPrimitive("#object"))).get().isInstanceOf(ObjectMatcher.class);
        assertThat(FuzzyMatchingUtil.getFuzzyMatcher(new JsonPrimitive("#string"))).get().isInstanceOf(StringMatcher.class);
        assertThat(FuzzyMatchingUtil.getFuzzyMatcher(new JsonPrimitive("#time"))).get().isInstanceOf(TimeMatcher.class);
        assertThat(FuzzyMatchingUtil.getFuzzyMatcher(new JsonPrimitive("#uuid"))).get().isInstanceOf(UUIDMatcher.class);
    }
}
