package com.example.finalproject;

import com.example.finalproject.Model.User;
import com.example.finalproject.Repository.AuthRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;


import java.time.LocalDate;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)

//Reema
public class AuthRepositoryTest {

    @Autowired
    private AuthRepository authRepository;

    private User user;

    @BeforeEach
    public void setUp() {
        // Create a User instance
        user = new User();
        user.setUsername("Reema55");
        user.setPassword("password123");
        user.setEmail("reema55@gmail.com");
        user.setAge(25);
        user.setDateOfBirth(LocalDate.ofEpochDay(1999-8-8));
        user.setGender("Female");
        user.setEducation_level("UnderGraduate");
        user.setRole("ADMIN");


        authRepository.save(user);
    }

    @Test
    public void testFindUserByUsername() {
        // Test the findUserByUsername method
        User foundUser = authRepository.findUserByUsername("Reema55");
        Assertions.assertThat(foundUser).isNotNull();
        Assertions.assertThat(foundUser.getUsername()).isEqualTo("Reema55");
        Assertions.assertThat(foundUser.getEmail()).isEqualTo("reema55@gmail.com");
        Assertions.assertThat(foundUser.getRole()).isEqualTo("ADMIN");
    }

    @Test
    public void testFindUserById() {
        // Test the findUserById method
        Optional<User> foundUser = authRepository.findById(user.getId());
        Assertions.assertThat(foundUser).isPresent();
        Assertions.assertThat(foundUser.get().getId()).isEqualTo(user.getId());
        Assertions.assertThat(foundUser.get().getUsername()).isEqualTo("Reema55");
        Assertions.assertThat(foundUser.get().getRole()).isEqualTo("ADMIN");
    }
}
