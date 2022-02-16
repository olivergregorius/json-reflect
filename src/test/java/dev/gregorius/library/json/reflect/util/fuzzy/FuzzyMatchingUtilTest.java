package dev.gregorius.library.json.reflect.util.fuzzy;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FuzzyMatchingUtilTest {

    @Test
    void given_nonStringValue_when_getFuzzyMatcher_then_noFuzzyMatcherIsReturned() {
        assertThat(FuzzyMatchingUtil.getFuzzyMatcher(1234)).isEmpty();
    }

    @Test
    void given_invalidFuzzyTag_when_getFuzzyMatcher_then_noFuzzyMatcherIsReturned() {
        assertThat(FuzzyMatchingUtil.getFuzzyMatcher("#value")).isEmpty();
        assertThat(FuzzyMatchingUtil.getFuzzyMatcher("#fuzzy")).isEmpty();
        assertThat(FuzzyMatchingUtil.getFuzzyMatcher("array")).isEmpty();
    }

    @Test
    void given_validFuzzyTag_when_getFuzzyMatcher_then_FuzzyMatcherIsReturned() {
        assertThat(FuzzyMatchingUtil.getFuzzyMatcher("#array")).get().isInstanceOf(ArrayMatcher.class);
        assertThat(FuzzyMatchingUtil.getFuzzyMatcher("#boolean")).get().isInstanceOf(BooleanMatcher.class);
        assertThat(FuzzyMatchingUtil.getFuzzyMatcher("#date")).get().isInstanceOf(DateMatcher.class);
        assertThat(FuzzyMatchingUtil.getFuzzyMatcher("#datetime")).get().isInstanceOf(DateTimeMatcher.class);
        assertThat(FuzzyMatchingUtil.getFuzzyMatcher("#float")).get().isInstanceOf(FloatMatcher.class);
        assertThat(FuzzyMatchingUtil.getFuzzyMatcher("#integer")).get().isInstanceOf(IntegerMatcher.class);
        assertThat(FuzzyMatchingUtil.getFuzzyMatcher("#null")).get().isInstanceOf(NullMatcher.class);
        assertThat(FuzzyMatchingUtil.getFuzzyMatcher("#object")).get().isInstanceOf(ObjectMatcher.class);
        assertThat(FuzzyMatchingUtil.getFuzzyMatcher("#string")).get().isInstanceOf(StringMatcher.class);
        assertThat(FuzzyMatchingUtil.getFuzzyMatcher("#time")).get().isInstanceOf(TimeMatcher.class);
        assertThat(FuzzyMatchingUtil.getFuzzyMatcher("#uuid")).get().isInstanceOf(UUIDMatcher.class);
    }
}
