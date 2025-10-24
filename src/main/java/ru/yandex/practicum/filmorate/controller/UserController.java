package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.validator.UserValidator;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

    private int idCount = 0;
    private final UserValidator userValidator = new UserValidator();
    private final Map<Integer, User> users = new HashMap<>();

    @PostMapping
    public User addUser(@RequestBody User user) {
        if (userValidator.userIsValid(user)) {
            user.setId(++idCount);
            users.put(user.getId(), user);
            log.info("пользователь id = {} добавлен", idCount);
        }
        return user;
    }

    @PutMapping
    public User updateUser(@RequestBody User user) {
        if (users.containsKey(user.getId())) {
            if (userValidator.userIsValid(user)) {
                users.put(user.getId(), user);
                log.info("пользователь id = {} обновлён", user.getId());
            }
        } else {
            log.info("пользователя с таким id нет");
            throw new NotFoundException("пользователя с таким id нет");
        }
        return user;
    }

    @GetMapping
    public Collection<User> getUsers() {
        return users.values();
    }
}
