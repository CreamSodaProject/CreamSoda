package com.example.creamsoda.module.user.model;

import com.example.creamsoda.common.BaseTime;
import com.example.creamsoda.module.file.model.FileInfo;
import com.example.creamsoda.module.schdule.model.Schedule;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Table(name = "USERS")
public class User extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Comment("이메일")
    private String email;
    @Comment("비밀번호")
    private String password;
    @Comment("이름")
    private String name;
    @Comment("프로필사진")
    @ManyToOne
    private FileInfo profile;


    public User(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;

    }
}
