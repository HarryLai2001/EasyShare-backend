package com.easyshare.converter;

import org.springframework.core.convert.converter.Converter;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class DateToLocalDateTimeConverter implements Converter<Date, LocalDateTime> {
    @Override
    public LocalDateTime convert(Date date) {
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDateTime localDateTime = date.toInstant().atZone(zoneId).toLocalDateTime();
        return localDateTime;
    }
}
