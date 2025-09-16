package app.dtos;

import app.dtos.MovieDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MovieWrapper {
    private List<MovieDTO> results;
}
