package app.dtos;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MovieDTO {

    @JsonProperty("id")
    private int movieId;

    @JsonProperty("genre_ids")
    private List<Integer> genreId;

    @JsonProperty("title")
    private String title;

    @JsonProperty("original_language")
    private String originalLanguage;

    @JsonProperty("vote_average")
    private Double rating;

    @JsonProperty("popularity")
    private Double popularity;

    @JsonProperty("release_date")
    private String releaseDate;

    @JsonProperty("overview")
    private String description;

}