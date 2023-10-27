package com.example.creamsoda.module.user.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;
import java.util.Random;

import static org.springframework.security.core.context.SecurityContextHolder.setContext;

@Service
@RequiredArgsConstructor
public class EmailService {

	//의존성 주입을 통해서 필요한 객체를 가져온다.
    private final JavaMailSender emailSender;
    private String authNum; //랜덤 인증 코드

    //랜덤 인증 코드 생성
    public void createCode() {
        Random random = new Random();
        StringBuilder key = new StringBuilder();

        for (int i = 0; i < 5; i++) {
            // 0부터 9까지의 난수 생성
            int digit = random.nextInt(10);
            key.append(digit);
        }
        authNum = key.toString();
    }
    public void passwordRandom() {
        SecureRandom secureRandom = new SecureRandom();
        StringBuilder key = new StringBuilder();
        boolean hasDigit = false;
        boolean hasSpecialChar = false;

        while (key.length() < 8 || !hasDigit || !hasSpecialChar) {
            int index = secureRandom.nextInt(4);

            switch (index) {
                case 0:
                    char lowercaseLetter = (char) (secureRandom.nextInt(26) + 97);
                    key.append(lowercaseLetter);
                    break;
                case 1:
                    char uppercaseLetter = (char) (secureRandom.nextInt(26) + 65);
                    key.append(uppercaseLetter);
                    break;
                case 2:
                    int digit = secureRandom.nextInt(10);
                    key.append(digit);
                    hasDigit = true;
                    break;
                case 3:
                    char specialChar = (char) (secureRandom.nextInt(15) + 33); // Special characters in ASCII range 33-47
                    key.append(specialChar);
                    hasSpecialChar = true;
                    break;
            }
        }

        authNum = key.toString();
    }



    //메일 양식 작성 (이메일 확인)
    public MimeMessage createEmailForm(String email) throws MessagingException, UnsupportedEncodingException {

        createCode(); //인증 코드 생성
        String setFrom = "khh4569@naver.com"; //email-config에 설정한 자신의 이메일 주소(보내는 사람)
        String toEmail = email; //받는 사람
        String title = "CREAM SODA 회원가입 인증 번호"; //제목

        MimeMessage message = emailSender.createMimeMessage();
        message.addRecipients(MimeMessage.RecipientType.TO, email); //보낼 이메일 설정
        message.setSubject(title); //제목 설정
        message.setFrom(setFrom); //보내는 이메일
        message.setText(
                " <p><strong>Cream Soda 회원가입 인증 번호 : </strong><span class=\"auth-num\">authNum</span></p>\n" +
                        "        <p>이용해 주셔서 감사합니다.</p>\n" +
                        "        <p>다음 단계를 진행해주세요.</p>"
                , "utf-8", "html");

        return message;
    }

    //메일 양식 작성 (임시비밀번호)
    public MimeMessage createEmailFormPassword(String email) throws MessagingException, UnsupportedEncodingException {

        passwordRandom(); //인증 코드 생성
        String setFrom = "khh4569@naver.com"; //email-config에 설정한 자신의 이메일 주소(보내는 사람)
        String title = "CREAM SODA 임시비밀번호"; //제목

        MimeMessage message = emailSender.createMimeMessage();
        message.addRecipients(MimeMessage.RecipientType.TO, email); //보낼 이메일 설정
        message.setSubject(title); //제목 설정
        message.setFrom(setFrom); //보내는 이메일
        message.setText(
                " <p><strong>Cream Soda 임시비밀번호 : </strong><span class=\"auth-num\"> "+authNum+" </span></p>\n" +
                        "        <p>이용해 주셔서 감사합니다.</p>\n" +
                        "        <p>다음 단계를 진행해주세요.</p>"
                , "utf-8", "html");

        return message;
    }

    //실제 메일 전송
    public String sendEmail(String toEmail) throws MessagingException, UnsupportedEncodingException {
    
        //메일전송에 필요한 정보 설정
        MimeMessage emailForm = createEmailForm(toEmail);
        //실제 메일 전송
        emailSender.send(emailForm);

        return authNum; //인증 코드 반환
    }

    //실제 메일 전송
    public String sendPassword(String toEmail) throws MessagingException, UnsupportedEncodingException {

        //메일전송에 필요한 정보 설정
        MimeMessage emailForm = createEmailFormPassword(toEmail);
        //실제 메일 전송
        emailSender.send(emailForm);

        return authNum; //인증 코드 반환
    }



}