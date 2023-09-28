package com.example.creamsoda.example;

import com.example.creamsoda.module.user.model.User;
import org.springframework.data.domain.PageRequest;

public interface UserExample {
    User user = new User(1, "David@naver.com",  "1234", "David");

}
