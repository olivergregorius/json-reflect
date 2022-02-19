package dev.gregorius.library.json.reflect.util.fuzzy;

import com.google.gson.JsonElement;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.function.Predicate;

public class DateMatcher extends DateTimeFuzzyMatcher {

    @Override
    protected List<DateTimeFormatter> getDateTimeFormatters() {
        return List.of(
            DateTimeFormatter.ofPattern("yyyy"),
            DateTimeFormatter.ofPattern("yyyy-MM"),
            DateTimeFormatter.ofPattern("yyyy-MM-dd")
        );
    }

    @Override
    public String getFuzzyTag() {
        return "#date";
    }

    @Override
    public Predicate<JsonElement> getPredicate() {
        return this::dateTimeFormattersMatch;
    }
}
