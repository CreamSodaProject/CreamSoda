package com.example.creamsoda.module.user.model;

import com.example.creamsoda.common.BaseTime;
import jakarta.persistence.*;
import lombok.*;
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
    private String email;
    private String password;
    private String name;
    private String birthDate;

    public User(String email, String password, String name, String birthDate) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.birthDate = birthDate;
    }
}
