package org.dilan.salinda.sonarqubedataextractor.service;

import org.dilan.salinda.sonarqubedataextractor.DTO.ComponentsDTO;
import org.dilan.salinda.sonarqubedataextractor.DTO.ProjectDTO;
import org.springframework.core.annotation.Order;

import java.util.List;

public interface ProjectDataExtractor extends BaseExtractor {

    @Order(2)
    void fetch();

    void Save(List<ComponentsDTO> projects);


}
