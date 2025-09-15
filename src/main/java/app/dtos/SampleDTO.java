package app.dtos;

import lombok.*;

import java.time.LocalTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString

public class SampleDTO {
    private LocalTime exerciseDate;
    private LocalTime timeOfDay;
    private double duration;
    private double distance;
    private String comment;
}
