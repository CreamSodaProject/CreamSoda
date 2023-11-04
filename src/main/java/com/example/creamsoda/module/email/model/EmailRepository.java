package com.example.creamsoda.module.email.model;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmailRepository extends JpaRepository<Email, Integer> {

    Optional<Email> findByEmail(String email);
    Optional<Email> findByAuthNum(String authNum);
    Optional<Email> findByEmailAndAuthNum(String email, String authNum);
}
