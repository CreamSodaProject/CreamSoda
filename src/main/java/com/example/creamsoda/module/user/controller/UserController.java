package com.example.creamsoda.module.user.controller;

import com.example.creamsoda.auth.JwtProvider;
import com.example.creamsoda.exception.Exception400;
import com.example.creamsoda.module.user.dto.UserJoinRequest;
import com.example.creamsoda.module.user.dto.UserLoginRequest;
import com.example.creamsoda.module.user.model.User;
import com.example.creamsoda.module.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("/join")
    public ResponseEntity<User> join(@Valid @RequestBody UserJoinRequest request, BindingResult result) {

        if (result.hasErrors()) {
            throw new Exception400(result.getAllErrors().get(0).getDefaultMessage());
        }

        Optional<User> optionalUser = userService.getEmailUser(request.email());

        if (optionalUser.isPresent()) {
            throw new Exception400("회원가입 내역이 있는 유저입니다.");
        }

        User user = userService.userJoin(request);

        return ResponseEntity.ok().body(user);
    }

    @PostMapping("/login")
    public ResponseEntity<User> login(@Valid @RequestBody UserLoginRequest request, BindingResult result) {

        if (result.hasErrors()) {
            throw new Exception400(result.getAllErrors().get(0).getDefaultMessage());
        }

        Optional<User> optionalUser = userService.userLogin(request);

        if (optionalUser.isEmpty()) {
            throw new Exception400("이메일과 비밀번호를 다시 확인 해주세요.");
        }

        String jwtToken = JwtProvider.create(optionalUser.get());

        return ResponseEntity.ok().header(JwtProvider.HEADER, jwtToken).body(optionalUser.get());
    }

}
