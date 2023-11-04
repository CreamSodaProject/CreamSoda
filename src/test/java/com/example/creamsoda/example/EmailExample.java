package com.example.creamsoda.example;

import com.example.creamsoda.module.email.enums.EmailType;
import com.example.creamsoda.module.email.model.Email;
import org.joda.time.LocalDateTime;

public interface EmailExample {

    Email email = new Email(1, "khh9608@gmail.com", "12345", LocalDateTime.now() ,EmailType.EMAIL, "Y");

}
