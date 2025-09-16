package app.entities;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@ToString
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

    @ElementCollection
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
}
