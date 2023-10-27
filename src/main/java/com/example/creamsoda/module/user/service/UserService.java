package com.example.creamsoda.module.user.service;

import com.example.creamsoda.module.user.dto.UserJoinRequest;
import com.example.creamsoda.module.user.dto.UserLoginRequest;
import com.example.creamsoda.module.user.model.User;
import com.example.creamsoda.module.user.model.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public User userJoin(UserJoinRequest request) {
        String encodePassword = passwordEncoder.encode(request.password());
        User entity = request.toEntity();
        entity.setPassword(encodePassword);
        return userRepository.save(entity);

    }

    public Optional<User> userLogin(UserLoginRequest request) {
        Optional<User> optional = userRepository.findByEmail(request.email());
        if (optional.isPresent()) {
            User user = optional.get();
            if (!passwordEncoder.matches(request.password(), user.getPassword())) {
                return Optional.empty();
            }
        }
        return optional;

    }

    public Optional<User> getUser(Integer id) {
        return userRepository.findById(id);
    }
}
