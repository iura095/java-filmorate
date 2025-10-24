package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.validator.FilmValidator;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {

    private int idCount = 0;
    private final FilmValidator filmValidator = new FilmValidator();
    private final Map<Integer, Film> films = new HashMap<>();

    @PostMapping
    public Film addFilm(@RequestBody Film film) {
        if (filmValidator.filmIsValid(film)) {
            film.setId(++idCount);
            films.put(film.getId(), film);
            log.info("фильм с id = {} добавлен", idCount);
        }
        return film;
    }

    @PutMapping
    public Film updateFilm(@RequestBody Film film) {
        if (filmValidator.filmIsValid(film)) {
            if (films.containsKey(film.getId())) {
                films.put(film.getId(), film);
                log.info("фильм с id = {} обновлён", film.getId());
            } else {
                log.info("фильма с таким id нет");
                throw new NotFoundException("фильма с таким id нет");
            }
        }
        return film;
    }

    @GetMapping
    public Collection<Film> getFilms() {
        return films.values();
    }
}
