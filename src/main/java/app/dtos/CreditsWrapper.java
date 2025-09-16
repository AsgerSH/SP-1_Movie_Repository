package app.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreditsWrapper {
    private List<ActorDTO> cast;
    private List<DirectorDTO> crew;
}

