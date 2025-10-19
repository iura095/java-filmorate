package ru.yandex.practicum.filmorate.controller;

import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

@RestController
@RequestMapping("/users")
public class UserController {

    private final Logger log = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(UserController.class);

    @PostMapping
    public User addUser(@RequestBody User user) {
        if (user.getEmail() == null
                || user.getEmail().isBlank()
                || !user.getEmail().contains("@")) {
            log.info("электронная почта не может быть пустой и должна содержать символ @");
            throw new ValidationException("электронная почта не может быть пустой и должна содержать символ @");
        }
        if (user.getLogin() == null
                || user.getLogin().isBlank()
                || user.getLogin().contains(" ")) {
            log.info("логин не может быть пустым и содержать пробелы");
            throw new ValidationException("логин не может быть пустым и содержать пробелы");
        }
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        if (user.getBirthday().isAfter(LocalDate.now())) {
            log.info("дата рождения не может быть в будущем");
            throw new ValidationException("дата рождения не может быть в будущем");
        }

        return user;
    }

    @PutMapping
    public User updateUser(@RequestBody User user) {

        return user;
    }

    @GetMapping
    public Collection<User> getUsers() {

        return new ArrayList<>();
    }
}
