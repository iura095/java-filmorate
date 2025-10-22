package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.Duration;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class FilmControllerTest {

    private static FilmController filmController;
    private static Film film;

    @BeforeEach
    void setUp() {
        filmController = new FilmController();
        film = new Film(1, "Inception", "Good movie", LocalDate.now(), Duration.ofMinutes(90));
    }


    @Test
    void addNameValidation() {
        assertEquals("Inception", filmController.addFilm(film).getName());
        film.setName("");
        assertThrows(ValidationException.class, () -> filmController.addFilm(film));
        film.setName("     ");
        assertThrows(ValidationException.class, () -> filmController.addFilm(film));
    }

    @Test
    void addDescriptionLess200SymbolsValidation() {
        assertEquals("Good movie", filmController.addFilm(film).getDescription());
        film.setDescription("It is used to display fonts, generate text for testing, and other purposes. " +
                "The characters are spread out evenly so the reader's attention is focused on the layout instead" +
                " of the content. Test text)))");
        assertDoesNotThrow(() -> filmController.addFilm(film));
        assertEquals(200, filmController.addFilm(film).getDescription().length());
    }

    @Test
    void addDescriptionMore200SymbolsValidation() {
        assertEquals("Good movie", filmController.addFilm(film).getDescription());
        film.setDescription("It is used to display fonts, generate text for testing, and other purposes. " +
                "The characters are spread out evenly so the reader's attention is focused on the layout instead" +
                " of the content. Test text))))");
        assertEquals(201, film.getDescription().length());
        assertThrows(ValidationException.class, () -> filmController.addFilm(film));
    }

    @Test
    void addReleaseDateValidate() {
        film.setReleaseDate((LocalDate.of(1985, 12, 28)));
        assertDoesNotThrow(() -> filmController.addFilm(film));
        film.setReleaseDate((LocalDate.of(1985, 12, 27)));
        assertThrows(ValidationException.class, () -> filmController.addFilm(film));
    }

    @Test
    void addDurationValidate() {
        film.setDuration(Duration.ZERO);
        assertThrows(ValidationException.class, () -> filmController.addFilm(film));
        film.setDuration(Duration.ofMinutes(-1));
        assertThrows(ValidationException.class, () -> filmController.addFilm(film));
    }

}