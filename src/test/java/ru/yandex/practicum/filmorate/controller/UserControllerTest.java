package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class UserControllerTest {

    UserController userController;
    User user;

    @BeforeEach
    void setUp() {
        userController = new UserController();
        user = new User(1, "user@gmail.com", "user1", "Vasilii",
                LocalDate.of(1963, 11, 17));
    }

    @Test
    void emailValidate() {
        assertDoesNotThrow(() -> userController.addUser(user));
        user.setEmail("usergmail.com");
        assertThrows(ValidationException.class, () -> userController.addUser(user));
        user.setEmail("");
        assertThrows(ValidationException.class, () -> userController.addUser(user));
        user.setEmail("   ");
        assertThrows(ValidationException.class, () -> userController.addUser(user));
    }

    @Test
    void loginValidation() {
        assertDoesNotThrow(() -> userController.addUser(user));
        user.setLogin("user 1");
        assertThrows(ValidationException.class, () -> userController.addUser(user));
        user.setLogin("");
        assertThrows(ValidationException.class, () -> userController.addUser(user));
    }

    @Test
    void emptyNameEqualLogin() {
        user.setName("");
        assertEquals(user.getLogin(), userController.addUser(user).getName());
    }

    @Test
    void birthdayValidation() {
        assertDoesNotThrow(() -> userController.addUser(user));
        user.setBirthday(LocalDate.now().plusDays(1));
        assertThrows(ValidationException.class, () -> userController.addUser(user));
    }
}