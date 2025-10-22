package ru.yandex.practicum.filmorate.validator;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;

@Slf4j
@NoArgsConstructor
public class FilmValidator {

    private static final int DESC_MAX_LENGTH = 200;

    public boolean updateFilmIsValid(Film film) {
        if (film.getId() == 0) {
            log.info("id фильма должен быть указан");
            throw new ValidationException("id фильма должен быть указан");
        }
        if ((film.getName() == null || film.getName().isBlank())
                && (film.getDescription() == null || film.getDescription().isBlank())
                && film.getReleaseDate() == null
                && film.getDuration() == null) {
            log.info("нет полей для обновления");
            throw new ValidationException("нет полей для обновления");
        }
        if (film.getDescription() != null && film.getDescription().length() > DESC_MAX_LENGTH) {
            log.info("максимальная длина описания = 200 символов");
            throw new ValidationException("максимальная длина описания — 200 символов");
        }
        return true;
    }

    public boolean newFilmIsValid(Film film) {
        if (film.getName() == null || film.getName().isBlank()) {
            log.info("название не может быть пустым");
            throw new ValidationException("название не может быть пустым");
        }
        if (film.getDescription().length() > DESC_MAX_LENGTH) {
            log.info("максимальная длина описания — 200 символов");
            throw new ValidationException("максимальная длина описания — 200 символов");
        }
        if (film.getReleaseDate().isBefore((LocalDate.of(1985, 12, 28)))) {
            log.info("дата релиза — не раньше 28 декабря 1895 года");
            throw new ValidationException("дата релиза — не раньше 28 декабря 1895 года");
        }
        if (film.getDuration().isNegative()) {
            log.info("продолжительность фильма должна быть положительным числом");
            throw new ValidationException("продолжительность фильма должна быть положительным числом");
        }
        return true;
    }
}
