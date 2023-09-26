package org.dilan.salinda.sonarqubedataextractor.service;

import org.dilan.salinda.sonarqubedataextractor.DTO.ComponentsDTO;

import java.util.List;

public interface ProjectDataExtractor extends BaseExtractor {

    void fetch();
    void Save(List<ComponentsDTO> projects);
}
