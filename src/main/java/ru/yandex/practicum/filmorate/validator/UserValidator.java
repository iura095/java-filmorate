package ru.yandex.practicum.filmorate.validator;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

@Slf4j
@NoArgsConstructor
public class UserValidator {

    private final EmailValidator emailValidator = new EmailValidator();

    public boolean userIsValid(User user) {
        emailValidator.validate(user.getEmail());
        if (nullBlankSpaceCheck(user.getLogin()) || user.getLogin().contains(" ")) {
            log.info("логин не может быть пустым и содержать пробелы");
            throw new ValidationException("логин не может быть пустым и содержать пробелы");
        }
        if (emptyFieldCheck(user.getName())) {
            user.setName(user.getLogin());
        }
        if (user.getBirthday().isAfter(LocalDate.now())) {
            log.info("дата рождения не может быть в будущем");
            throw new ValidationException("дата рождения не может быть в будущем");
        }
        return true;
    }

    private boolean emptyFieldCheck(String s) {
        return s == null || s.isBlank();
    }

    private boolean nullBlankSpaceCheck(String s) {
        return s == null || s.isBlank() || s.contains(" ");
    }
}
