package app.util;

import app.entities.Movie;
import app.entities.Actor;
import app.entities.Director;

import java.util.HashSet;
import java.util.List;

public class TestDataFactory {

    public static Movie createMovie(String title) {
        return Movie.builder()
                .movieId((int) (Math.random() * 10000))
                .genreId(List.of(18))
                .title(title)
                .originalLanguage("da")
                .rating(7.0)
                .popularity(50.0)
                .releaseDate("2023-01-01")
                .description("Test movie")
                .actorSet(new HashSet<>())
                .directorSet(new HashSet<>())
                .build();
    }

    public static Actor createActor(String name, Movie movie) {
        return Actor.builder()
                .actorId((int) (Math.random() * 10000))
                .department("Acting")
                .name(name)
                .character("Some Character")
                .popularity(60.0)
                .movie(movie)
                .build();
    }

    public static Director createDirector(String name, Movie movie) {
        return Director.builder()
                .directorId((int) (Math.random() * 10000))
                .department("Directing")
                .name(name)
                .popularity(70.0)
                .movie(movie)
                .build();
    }
}
