package com.example.creamsoda.module.email.dto;

import com.example.creamsoda.module.email.enums.EmailType;
import com.example.creamsoda.module.email.model.Email;

public record PasswordFindRequestDto(
        String email

) {

    public Email toEntity(String authNum) {
        return new Email(null, email, authNum, EmailType.PASSWORD);
    }
}
