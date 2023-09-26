package org.dilan.salinda.sonarqubedataextractor.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class Project  {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    private String name;

    @Column(name = "`key`")
    private String key;

    private String visibility;
    private boolean isFavorite;
    private boolean isNew;

    @OneToOne
    Organization organization;

    @OneToMany(mappedBy = "project")
    private Set<ProjectTag> projectTags;


}
