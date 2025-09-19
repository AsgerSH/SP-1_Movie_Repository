package app.entities;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import app.services.MovieService;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Setter
@Getter
@ToString(exclude = "genreId")
@NoArgsConstructor
@Entity
@AllArgsConstructor
@Builder
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private int movieId;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "movie_genres", joinColumns = @JoinColumn(name = "movie_id"))
    @Column(name = "genre_id")
    private List<Integer> genreId;

    @Column(nullable = false, length = 1000)
    private String title;

    @Column(nullable = false)
    private String originalLanguage;

    @Column(nullable = false)
    private Double rating;

    @Column(nullable = false)
    private Double popularity;

    @Column(nullable = false)
    private String releaseDate;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;


    // 1:m relationer
    @OneToMany(mappedBy = "movie", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @Builder.Default
    private Set<Actor> actorSet = new HashSet<>();

    @OneToMany(mappedBy = "movie", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @Builder.Default
    private Set<Director> directorSet = new HashSet<>();

    @Override
    public String toString() {
        // Genres
        String genreNames = "Unknown";
        if (genreId != null && !genreId.isEmpty()) {
            genreNames = genreId.stream()
                    .map(MovieService.GENRE_MAP::get)
                    .filter(Objects::nonNull)
                    .collect(Collectors.joining(" + "));
        }

        // Release year
        String releaseYear = "Unknown";
        if (releaseDate != null && releaseDate.length() >= 4) {
            releaseYear = releaseDate.substring(0, 4);
        }

        // Safe rating/popularity
        double safeRating = rating != null ? rating : 0.0;
        double safePopularity = popularity != null ? popularity : 0.0;

        // Format string
        return String.format("%s (%s) - Genres: %s | Rating: %.1f | Popularity: %.1f | Actors: %d | Directors: %d",
                title, releaseYear, genreNames, safeRating, safePopularity,
                actorSet != null ? actorSet.size() : 0,
                directorSet != null ? directorSet.size() : 0
        );
    }
}
