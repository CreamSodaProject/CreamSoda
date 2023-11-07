package com.example.creamsoda.module.email.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import java.time.LocalDateTime;
public record CheckRequest(
        String email,
        String check
) {
}
