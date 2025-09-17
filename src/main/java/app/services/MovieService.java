package app.services;


import app.config.HibernateConfig;
import app.dtos.*;
import app.entities.Actor;
import app.entities.Director;
import app.entities.Movie;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.hibernate.annotations.Fetch;

import java.util.*;
import java.util.stream.Collectors;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


import app.services.FetchTools;

import javax.swing.text.html.parser.Entity;

public class MovieService {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final HttpClient client = HttpClient.newHttpClient();
    private final String apiKey = System.getenv("API_KEY");
    private final FetchTools fetchTools = new FetchTools();

    private final EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
    private final EntityManager em = emf.createEntityManager();


    public List<MovieDTO> getAllDanishMovies(int totalPages) {
        List<MovieDTO> allMovies = new ArrayList<>();

        try {
            for (int page = 1; page <= totalPages; page++) {
                String url = "https://api.themoviedb.org/3/discover/movie"
                        + "?api_key=" + apiKey
                        + "&with_origin_country=DK"
                        + "&with_original_language=da"
                        + "&language=en-US"
                        + "&sort_by=popularity.desc"
                        + "&primary_release_date.gte=2020-09-17"
                        + "&primary_release_date.lte=2025-09-17"
                        + "&page=" + page;

                HttpRequest request = HttpRequest.newBuilder()
                        .uri(new URI(url))
                        .GET()
                        .build();

                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

                if (response.statusCode() == 200) {
                    MovieWrapper wrapper = objectMapper.readValue(response.body(), MovieWrapper.class);
                    allMovies.addAll(wrapper.getResults());
                } else {
                    System.out.println("GET request failed. Status code: " + response.statusCode());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return allMovies;
    }

    public void addActorsAndDirectors(Movie movie) {
        try {
            String url = "https://api.themoviedb.org/3/movie/"
                    + movie.getMovieId()
                    + "/credits?api_key=" + apiKey;

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(url))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                CreditsWrapper credits = objectMapper.readValue(response.body(), CreditsWrapper.class);

                // Map cast -> Actor entities

                for (ActorDTO actorDTO : credits.getCast()) {
                    Actor actor = Actor.builder()
                            .actorId(actorDTO.getActorId())
                            .department(actorDTO.getDepartment())
                            .name(actorDTO.getName())
                            .character(actorDTO.getCharacter())
                            .popularity(actorDTO.getPopularity())
                            .movie(movie)
                            .build();
                    movie.getActorSet().add(actor);
                }
                for (DirectorDTO directorDTO : credits.getCrew()) {
                    if ("Directing".equalsIgnoreCase(directorDTO.getDepartment())) {
                        Director director = Director.builder()
                                .directorId(directorDTO.getDirectorId())
                                .department(directorDTO.getDepartment())
                                .name(directorDTO.getName())
                                .popularity(directorDTO.getPopularity())
                                .movie(movie)
                                .build();

                        movie.getDirectorSet().add(director);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Movie mapToEntity(MovieDTO dto) {
        Movie movieEntity = Movie.builder()
                .movieId(dto.getMovieId())
                .genreId(dto.getGenreId())
                .title(dto.getTitle())
                .originalLanguage(dto.getOriginalLanguage())
                .rating(dto.getRating())
                .popularity(dto.getPopularity())
                .releaseDate(dto.getReleaseDate())
                .description(dto.getDescription())
                .build();

        return movieEntity;
    }

    public void importDanishMovies(int totalPages) {
        List<MovieDTO> movieDTOList = getAllDanishMovies(totalPages);

        List<Movie> movieEntities = movieDTOList.stream()
                .map(this::mapToEntity)
                .toList();

        try {
            em.getTransaction().begin();

            for (int i = 0; i < movieEntities.size(); i++) {
                Movie movie = movieEntities.get(i);
                addActorsAndDirectors(movie);

                em.persist(movie);

                // Flush & clear every 50 entities - prevents memory(RAM) - loss
                if (i > 0 && i % 50 == 0) {
                    em.flush();
                    em.clear();

                    System.out.println((i) + " movies imported so far...");
                }
            }

            em.getTransaction().commit();
            System.out.println("Imported " + movieDTOList.size() + " Danish movies into DB.");
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            throw e;
        }
    }

    public static final Map<Integer, String> GENRE_MAP = new HashMap<>();
    static {
        GENRE_MAP.put(28, "Action");
        GENRE_MAP.put(12, "Adventure");
        GENRE_MAP.put(16, "Animation");
        GENRE_MAP.put(35, "Comedy");
        GENRE_MAP.put(80, "Crime");
        GENRE_MAP.put(99, "Documentary");
        GENRE_MAP.put(18, "Drama");
        GENRE_MAP.put(10751, "Family");
        GENRE_MAP.put(14, "Fantasy");
        GENRE_MAP.put(36, "History");
        GENRE_MAP.put(27, "Horror");
        GENRE_MAP.put(10402, "Music");
        GENRE_MAP.put(9648, "Mystery");
        GENRE_MAP.put(10749, "Romance");
        GENRE_MAP.put(878, "Sci-Fi");
        GENRE_MAP.put(10770, "TV Movie");
        GENRE_MAP.put(53, "Thriller");
        GENRE_MAP.put(10752, "War");
        GENRE_MAP.put(37, "Western");
    }

    public String genresAsString(Movie movie) {
        if (movie.getGenreId() == null || movie.getGenreId().isEmpty()) return "Unknown";

        return movie.getGenreId().stream()
                .map(GENRE_MAP::get)
                .filter(Objects::nonNull)
                .collect(Collectors.joining(" + "));
    }

}


