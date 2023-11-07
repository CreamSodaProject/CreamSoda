package com.example.creamsoda.module.email.controller;

import com.example.creamsoda.exception.Exception400;
import com.example.creamsoda.module.email.dto.CheckRequest;
import com.example.creamsoda.module.email.dto.EmailAuthRequestDto;
import com.example.creamsoda.module.email.dto.PasswordFindRequestDto;
import com.example.creamsoda.module.email.model.Email;
import com.example.creamsoda.module.email.service.EmailService;
import com.example.creamsoda.module.user.model.User;
import com.example.creamsoda.module.user.service.UserService;
import jakarta.mail.MessagingException;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.joda.time.DateTime;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Data
@RestController
@RequiredArgsConstructor
@RequestMapping("/send")
public class EmailController {

    private final EmailService emailService;

    private final UserService userService;

    @PostMapping("/email")
    public ResponseEntity<HashMap<String, String>> emailConfirm(@RequestBody EmailAuthRequestDto emailDto) throws MessagingException, UnsupportedEncodingException {

        Email authCode = emailService.sendEmail(emailDto);

        Optional<User> optionalUser = userService.getEmailUser(emailDto.email());

//        if (optionalUser.isPresent()) {
//            throw new Exception400("회원가입 내역이 있는 유저입니다.");
//        }

        // save 한 이메일이 존재하면 새로운 인증번호로 업데이트
//        Optional<Email> optionalEmail = emailService.emailCheck(emailDto.email(), authCode.getAuthNum());
//        if (optionalEmail.isPresent()){
//            if (optionalEmail.get().getEmail().equals(emailDto.email()))
//            emailService.updateEmail(emailDto);
//        }

        HashMap<String, String> messageMap = new HashMap<>();
        messageMap.put("detail", "이메일 전송 완료");

        return ResponseEntity.ok(messageMap);
    }

    @PostMapping("/password")
    public ResponseEntity<HashMap<String, String>> passwordFind(@RequestBody PasswordFindRequestDto requestDto) throws MessagingException, UnsupportedEncodingException {

        Email authCode = emailService.sendPassword(requestDto);

        HashMap<String, String> messageMap = new HashMap<>();
        messageMap.put("detail", "임시비밀번호 전송 완료");

        return ResponseEntity.ok(messageMap);
    }

    @PostMapping("/check")
    public ResponseEntity<HashMap<String, String>> check(@RequestBody CheckRequest checkDto) {

        Optional<Email> optionalEmail = emailService.emailCheck(checkDto.email(), checkDto.check());

        if (optionalEmail.isPresent()) {

            if (optionalEmail.get().getDelYn().equals("N")){
                throw new Exception400("인증 토큰이 만료 되었습니다.");
            }

            LocalDateTime startTime = LocalDateTime.now();
            LocalDateTime endTime = startTime.plus(3, ChronoUnit.MINUTES);
            Duration duration = Duration.between(startTime, endTime);
            long minutes = duration.toMinutes();

            if (minutes > 3) {
                Email email = optionalEmail.get();
                email.setDelYn("N");
                throw new Exception400("만료시간 3분이 초과 되었습니다.");
            }

        } else {
            throw new Exception400("인증번호가 일치하지 않습니다.");
        }

        HashMap<String, String> messageMap = new HashMap<>();
        messageMap.put("detail", "인증번호 체크 완료");

        return ResponseEntity.ok(messageMap);
    }
}