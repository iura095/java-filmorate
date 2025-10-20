package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.validator.UserValidator;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserValidator userValidator = new UserValidator();
    private final Map<Integer, User> users = new HashMap<>();

    @PostMapping
    public User addUser(@RequestBody User user) {
        if (userValidator.newUserIsValid(user)) {
            user.setId(getNextId());
            users.put(user.getId(), user);
        }
        return user;
    }

    @PutMapping
    public User updateUser(@RequestBody User newUser) {
        if (users.containsKey(newUser.getId())) {
            if (userValidator.updateUserIsValid(newUser)) {
                User oldUser = users.get(newUser.getId());
                updateUserFields(newUser, oldUser);
            }
        } else {
            log.info("пользователя с таким id нет");
            throw new ValidationException("пользователя с таким id нет");
        }
        return users.get(newUser.getId());
    }

    @GetMapping
    public Collection<User> getUsers() {
        return users.values();
    }

    private void updateUserFields(User newUser, User oldUser) {
        if (newUser.getName() != null) {
            oldUser.setName(newUser.getName());
        }
        if (newUser.getLogin() != null) {
            oldUser.setLogin(newUser.getLogin());
        }
        if (newUser.getBirthday() != null) {
            oldUser.setBirthday(newUser.getBirthday());
        }
        if (newUser.getEmail() != null) {
            oldUser.setEmail(newUser.getEmail());
        }
    }

    private int getNextId() {
        int currentMaxId = users.keySet()
                .stream()
                .mapToInt(id -> id)
                .max()
                .orElse(0);
        return ++currentMaxId;
    }
}
