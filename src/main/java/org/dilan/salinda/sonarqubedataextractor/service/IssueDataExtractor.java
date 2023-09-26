package org.dilan.salinda.sonarqubedataextractor.service;

import org.dilan.salinda.sonarqubedataextractor.DTO.IssuesDTO;

import java.util.List;

public interface IssueDataExtractor extends BaseExtractor {

    void fetch(List<String> projects);
    void Save(List<IssuesDTO> projects);
}
