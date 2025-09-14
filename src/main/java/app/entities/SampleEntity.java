package app.entities;

import jakarta.persistence.*;
import lombok.*;

@Getter
@ToString
@NoArgsConstructor
@Entity
@AllArgsConstructor
@Builder

public class SampleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false)
    private String brewery;
    private String name;
    private String type;
    private String taste_type;
}

