package dev.sbohdan.vacancies_service.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.lang.NonNull;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@ReadingConverter
public class LongToLocalDateTimeReadConverter implements Converter<Long, LocalDateTime> {
    @Override
    public LocalDateTime convert(@NonNull Long source) {
        return LocalDateTime.ofInstant(
                Instant.ofEpochSecond(source),
                ZoneOffset.UTC);
    }
}
