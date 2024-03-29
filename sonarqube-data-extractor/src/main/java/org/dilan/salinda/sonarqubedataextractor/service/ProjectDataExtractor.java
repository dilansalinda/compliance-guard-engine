package org.dilan.salinda.sonarqubedataextractor.service;

import org.dilan.salinda.sonarqubedataextractor.dto.ComponentsDTO;

import java.util.List;

public interface ProjectDataExtractor extends BaseExtractor {

    void fetch();

    void save(List<ComponentsDTO> projects);
}
