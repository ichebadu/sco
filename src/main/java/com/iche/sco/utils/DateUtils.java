package com.iche.sco.utils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@Builder
@AllArgsConstructor
public class DateUtils {

    public static final long EXPIRATION_DURATION_MINUTES = 390;
    public static String currentTime(LocalDateTime localDateTime){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy-MM-dd");
        return formatter.format(localDateTime);
    }
    public static String saveDate(LocalDateTime date) {
        if (date == null) {
            return "null";
        } else {
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd h:mm:ss a");
            return date.format(dateTimeFormatter);
        }
    }
}
