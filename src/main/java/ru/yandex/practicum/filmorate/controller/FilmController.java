package ru.yandex.practicum.filmorate.controller;

import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

@RestController
@RequestMapping("/films")
public class FilmController {

    private final static Logger log = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(FilmController.class);

    @PostMapping
    public Film addFilm(@RequestBody Film film) {
        if (film.getName() == null || film.getName().isBlank()) {
            log.info("название не может быть пустым");
            throw new ValidationException("название не может быть пустым");
        }
        if (film.getDescription().length() > 200) {
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

        return film;
    }

    @PutMapping
    public Film updateFilm(@RequestBody Film film) {

        return film;
    }

    @GetMapping
    public Collection<Film> getFilms() {

        return new ArrayList<>();
    }
}