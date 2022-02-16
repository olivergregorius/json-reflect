package dev.gregorius.library.json.reflect.util.fuzzy;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.function.Predicate;

public class DateTimeMatcher extends DateTimeFuzzyMatcher {

    @Override
    protected List<DateTimeFormatter> getDateTimeFormatters() {
        return List.of(
            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mmXXX"),
            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX"),
            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SXXX"),
            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSXXX"),
            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX"),
            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSXXX"),
            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSXXX"),
            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSSXXX"),
            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSSSXXX"),
            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSSSSXXX"),
            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSSSSSXXX")
        );
    }

    @Override
    public String getFuzzyTag() {
        return "#datetime";
    }

    @Override
    public Predicate<Object> getPredicate() {
        return this::dateTimeFormattersMatch;
    }
}
