package app.entities;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private int movieId;

    @Column(nullable = false)
    private List<Integer> genreId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String originalLanguage;

    @Column(nullable = false)
    private Double rating;

    @Column(nullable = false)
    private Double popularity;

    @Column(nullable = false)
    private LocalDate releaseDate;

    @Column(nullable = false)
    private String description;


}
