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

public class Actor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column (nullable = false)
    private int actorId;

    @Column (nullable = false)
    private String department;

    @Column (nullable = false)
    private String name;

    @Column (nullable = false)
    private String character;

    @Column (nullable = false)
    private Double popularity;


    // 1:m relationer


    // m:1 relationer
    @ManyToOne
    @Setter
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Movie movie;
}
