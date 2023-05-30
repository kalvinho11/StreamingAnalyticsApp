package com.example.streaminganalytics.application.utils;

import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Date;

@Component
public class DateTimeUtilImpl implements DateTimeUtil{

    @Override
    public Date getDate() {
        return Date.from(Instant.now());
    }
}
