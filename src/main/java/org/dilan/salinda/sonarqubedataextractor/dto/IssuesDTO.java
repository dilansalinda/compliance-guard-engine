package org.dilan.salinda.sonarqubedataextractor.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class IssuesDTO {

    private String key;
    private String rule;
    private String severity;
    private String component;
    private String project;
    private float line;
    private String hash;
    private IssueTextRangeDTO textRange;
    private String status;
    private String message;
    private String effort;
    private String debt;
    private String author;
    private List<String> tags;
    private String creationDate;
    private String updateDate;
    private String type;
    private String organization;
    private String cleanCodeAttribute;
    private String cleanCodeAttributeCategory;
    private List<IssueImpactsDTO> impacts = new ArrayList<>();

}