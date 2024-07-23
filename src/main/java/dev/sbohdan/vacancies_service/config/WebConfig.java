package dev.sbohdan.vacancies_service.config;

import dev.sbohdan.vacancies_service.converter.LongToLocalDateTimeReadConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.r2dbc.convert.R2dbcCustomConversions;
import org.springframework.data.r2dbc.dialect.H2Dialect;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class WebConfig {
    @Bean
    public R2dbcCustomConversions customConversions() {
        List<Converter<?, ?>> converters = new ArrayList<>();
        converters.add(new LongToLocalDateTimeReadConverter());
        return R2dbcCustomConversions.of(H2Dialect.INSTANCE, converters);
    }
}
