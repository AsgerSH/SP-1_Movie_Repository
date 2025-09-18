package app.entities;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
@ToString
@NoArgsConstructor
@Entity
@AllArgsConstructor
@Builder

public class Director {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column (nullable = false)
    private int directorId;

    @Column (nullable = false)
    private String department;

    @Column (nullable = false)
    private String name;

    @Column (nullable = false)
    private Double popularity;


    // 1:m relationer

    // m:1 relationer
    @ManyToOne
    @Setter
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Movie movie;


    @Override
    public String toString() {
        return name + (department != null ? " (" + department + ")" : "");
    }
}
