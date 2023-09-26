package org.dilan.salinda.sonarqubedataextractor.service;

import org.dilan.salinda.sonarqubedataextractor.dto.IssuesDTO;

import java.util.List;

public interface IssueDataExtractor extends BaseExtractor {

    void fetch(List<String> projects);

    void save(List<IssuesDTO> projects);
}
