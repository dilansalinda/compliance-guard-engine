package org.dilan.salinda.sonarqubedataextractor.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;


@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class Organization {

    @Id
    private Long id;
    private String name;

    @Column(name = "`key`")
    private String key;
}
