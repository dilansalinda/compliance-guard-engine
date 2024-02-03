package org.dilan.salinda.sonarqubedataextractor.model;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Getter
@Setter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class Organization {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(name = "`key`")
    private String key;

    public Organization(long id, String name, String key) {
        this.id = id;
        this.name = name;
        this.key = key;
    }
}
