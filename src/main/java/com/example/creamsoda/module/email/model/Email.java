package com.example.creamsoda.module.email.model;

import com.example.creamsoda.common.BaseTime;
import com.example.creamsoda.module.email.enums.EmailType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Comment;
import org.joda.time.DateTime;
import org.joda.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "EMAIL")
public class Email extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Comment("받는유저 이메일")
    private String email;
    @Comment("인증번호")
    private String authNum;  // 최신 받은 인증번호는 관리 따로해봄
    @Comment("만료시간")
    private LocalDateTime time;
    @Enumerated(EnumType.STRING)
    @Comment("이메일인증 타입")
    private EmailType type;
    @Comment("삭제여부")
    private String DelYn;

    public Email(String email, String authNum, EmailType type, String DelYn) {
        this.email = email;
        this.authNum = authNum;
        this.type = type;
        this.DelYn = DelYn;
    }
}
