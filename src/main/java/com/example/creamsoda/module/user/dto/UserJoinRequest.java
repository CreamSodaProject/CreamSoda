package com.example.creamsoda.module.user.dto;

import com.example.creamsoda.module.user.model.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record UserJoinRequest(
        @NotBlank(message = "이메일 입력은 필수 입니다.")
        @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,6}$", message = "이메일 형식에 맞지 않습니다.")
        String email,
        @NotBlank(message = "비밀번호 입력은 필수 입니다.")
        String password,
        @NotBlank(message = "이름 입력은 필수 입니다.")
        String name,
        @Pattern(regexp = "^01(?:0|1|[6-9])[.-]?(\\d{3}|\\d{4})[.-]?(\\d{4})$", message = "10 ~ 11 자리의 숫자만 입력 가능합니다.")
        String tel

) {
    public User toEntity(String EncoderPassword) {
        return new User(email, EncoderPassword, name, tel);
    }
}
