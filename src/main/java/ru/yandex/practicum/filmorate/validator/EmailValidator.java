package ru.yandex.practicum.filmorate.validator;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.exception.ValidationException;

@NoArgsConstructor
@Slf4j
public class EmailValidator {

    public void validate(String s) {
        if (s == null || s.isBlank()) {
            log.info("электронная почта не может быть пустой");
            throw new ValidationException("электронная почта не может быть пустой");
        }
        if (!s.contains("@")) {
            log.info("электронная почта должна содержать символ @");
            throw new ValidationException("электронная должна содержать символ @");
        }
        if (s.contains(" ")) {
            log.info("электронная почта не должна содержать пробелы");
            throw new ValidationException("электронная почта не должна содержать пробелы");
        }
        if (s.startsWith("@")) {
            log.info("электронная почта не может начинаться с @");
            throw new ValidationException("электронная не может начинаться с @");
        }
        if (s.endsWith("@")) {
            log.info("электронная почта не может заканчиваться на @");
            throw new ValidationException("электронная не может заканчиваться на @");
        }
    }
}
