package dev.gregorius.library.json.reflect.util.fuzzy;

import com.google.gson.JsonElement;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.function.Predicate;

public class TimeMatcher extends DateTimeFuzzyMatcher {

    @Override
    protected List<DateTimeFormatter> getDateTimeFormatters() {
        return List.of(
            DateTimeFormatter.ofPattern("HH:mmXXX"),
            DateTimeFormatter.ofPattern("HH:mm:ssXXX"),
            DateTimeFormatter.ofPattern("HH:mm:ss.SXXX"),
            DateTimeFormatter.ofPattern("HH:mm:ss.SSXXX"),
            DateTimeFormatter.ofPattern("HH:mm:ss.SSSXXX"),
            DateTimeFormatter.ofPattern("HH:mm:ss.SSSSXXX"),
            DateTimeFormatter.ofPattern("HH:mm:ss.SSSSSXXX"),
            DateTimeFormatter.ofPattern("HH:mm:ss.SSSSSSXXX"),
            DateTimeFormatter.ofPattern("HH:mm:ss.SSSSSSSXXX"),
            DateTimeFormatter.ofPattern("HH:mm:ss.SSSSSSSSXXX"),
            DateTimeFormatter.ofPattern("HH:mm:ss.SSSSSSSSSXXX")
        );
    }

    @Override
    public String getFuzzyTag() {
        return "#time";
    }

    @Override
    public Predicate<JsonElement> getPredicate() {
        return this::dateTimeFormattersMatch;
    }
}
