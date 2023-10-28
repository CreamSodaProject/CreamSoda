package com.example.creamsoda.module.email.dto;

import com.example.creamsoda.module.email.enums.EmailType;
import com.example.creamsoda.module.email.model.Email;

public record EmailAuthRequestDto(
        String email

) {

    public Email toEntity(String authNum) {
        return new Email( email, authNum, EmailType.EMAIL);
    }
}
