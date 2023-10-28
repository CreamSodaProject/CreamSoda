package com.example.creamsoda.module.email.controller;

import com.example.creamsoda.module.email.dto.CheckRequest;
import com.example.creamsoda.module.email.dto.EmailAuthRequestDto;
import com.example.creamsoda.module.email.dto.PasswordFindRequestDto;
import com.example.creamsoda.module.email.enums.EmailType;
import com.example.creamsoda.module.email.model.Email;
import com.example.creamsoda.module.email.service.EmailService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/send")
public class EmailController {

    private final EmailService emailService;

    @PostMapping("/email")
    public ResponseEntity<Email> emailConfirm(@RequestBody EmailAuthRequestDto emailDto) throws MessagingException, UnsupportedEncodingException {

        Email authCode = emailService.sendEmail(emailDto);

        return ResponseEntity.ok(authCode);
    }

    @PostMapping("/password")
    public ResponseEntity<Email> passwordFind(@RequestBody PasswordFindRequestDto requestDto) throws MessagingException, UnsupportedEncodingException {

        Email authCode = emailService.sendPassword(requestDto);

        return ResponseEntity.ok(authCode);
    }

    @PostMapping("/check")
    public ResponseEntity<Email> check(@RequestBody CheckRequest checkDto) {

        Email email = emailService.emailCheck(checkDto.check());

        return ResponseEntity.ok(email);
    }
}