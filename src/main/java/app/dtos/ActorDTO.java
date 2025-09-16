package app.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Data

@JsonIgnoreProperties(ignoreUnknown = true)
public class ActorDTO {

    @JsonProperty("id")
    private int actorId;

    @JsonProperty("known_for_department")
    private String department;

    private String name;
    private String character;
    private Double popularity;

}