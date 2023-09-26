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
public class Issue {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @Column(name = "`key`")
    private String key;
    private String rule;
    private String severity;
    private String component;

    @ManyToOne
    @JoinColumn(name="project_id", nullable=false)
    private Project project;
    private float line;
    private String hash;
    private int textRangeStartLine;
    private int textRangeEndLine;
    private int textRangeStartOffset;
    private int textRangeEndOffset;

    private String status;
    private String message;
    private String effort;
    private String debt;
    private String author;

    @OneToMany(mappedBy = "issue")
    private Set<IssueTags> issueTags;

    private Long creationDate;
    private Long updateDate;
    @Column(name = "`type`")
    private String type;

    @ManyToOne
    @JoinColumn(name="organization_id", nullable=false)
    private Organization organization;

    private String cleanCodeAttribute;
    private String cleanCodeAttributeCategory;
    private String impactsSoftwareQuality;
    private String impactsSeverity;


}
