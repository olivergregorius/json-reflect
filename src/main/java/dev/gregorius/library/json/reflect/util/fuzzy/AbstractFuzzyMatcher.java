package dev.gregorius.library.json.reflect.util.fuzzy;

public abstract class AbstractFuzzyMatcher implements FuzzyMatcher {

    private boolean optional = false;

    @Override
    public boolean isOptional() {
        return optional;
    }

    @Override
    public void setOptional(final boolean optional) {
        this.optional = optional;
    }
}
