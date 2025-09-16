package app.dtos;

import app.entities.MovieDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Data

@JsonIgnoreProperties(ignoreUnknown = true)
public class DirectorDTO {

    @JsonProperty("id")
    private int directorId;

    @JsonProperty("known_for_department")
    private String department;

    @JsonProperty("name")
    private String name;

    @JsonProperty("popularity")
    private Double popularity;

    private MovieDTO movieDTO;

}