package com.example.creamsoda.repository;

import com.example.creamsoda.module.user.model.User;
import com.example.creamsoda.module.user.model.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@DisplayName("유저 Repository 테스트")
@ExtendWith(SpringExtension.class)
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @BeforeEach
    public void init() {
        setup("David@gmail.com", "1234", "David");
    }

    @Test
    @DisplayName("유저 findAll(Select) 테스트")
    void selectAll() {
        List<User> userList = userRepository.findAll();
        Assertions.assertNotEquals(userList.size(), 0);

        User user = userList.get(0);
        Assertions.assertEquals(user.getEmail(), "David@gmail.com");
        Assertions.assertEquals(user.getPassword(), "1234");
        Assertions.assertEquals(user.getName(), "David");
    }

    @Test
    @DisplayName("유저 findById And merge(Update) 테스트")
    void selectOne() {
        Optional<User> optionalUser = userRepository.findById(1);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            Assertions.assertEquals(user.getEmail(), "David@gmail.com");
            Assertions.assertEquals(user.getPassword(), "1234");
            Assertions.assertEquals(user.getName(), "David");

            user.setName("Ho");
            User userMerge = testEntityManager.merge(user);

            Assertions.assertEquals(userMerge.getName(), "Ho");
        } else {
            Assertions.assertNotNull(optionalUser.get());
        }
    }

    @Test
    @DisplayName("유저 save(Insert) And remove(Delete) 테스트")
    void InsertAndDelete() {
        User user = userRepository.save(setup("Ho@gmail.com", "1234", "Ho"));

        Assertions.assertEquals(user.getEmail(), "Ho@gmail.com");
        Assertions.assertEquals(user.getPassword(), "1234");
        Assertions.assertEquals(user.getName(), "Ho");

        Optional<User> optionalUser = userRepository.findById(user.getId());
        if (optionalUser.isPresent()) {
            User deleteUser = optionalUser.get();
            testEntityManager.remove(deleteUser);

            Optional<User> optionalDeleteUser = userRepository.findById(deleteUser.getId());
            optionalDeleteUser.ifPresent(Assertions::assertNull);
        } else {
            Assertions.assertNotNull(optionalUser.get());
        }
    }

    public User setup(String email, String password, String name) {
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        user.setName(name);
        return testEntityManager.persist(user);
    }
}
