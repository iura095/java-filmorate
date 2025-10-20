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

    public boolean updateUserIsValid(User user) {
        if (emptyFieldCheck(user.getEmail())
                && emptyFieldCheck(user.getLogin())
                && emptyFieldCheck(user.getName())
                && user.getBirthday() == null) {
            log.info("нет полей для обновления");
            throw new ValidationException("нет полей для обновления");
        }
        if (!nullBlankSpaceCheck(user.getEmail())) {
            emailValidator.validate(user.getEmail());
        }
        return true;
    }

    public boolean newUserIsValid(User user) {
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
