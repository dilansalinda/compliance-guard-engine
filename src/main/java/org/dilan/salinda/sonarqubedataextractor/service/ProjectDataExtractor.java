package org.dilan.salinda.sonarqubedataextractor.service;

import org.dilan.salinda.sonarqubedataextractor.model.Project;

import java.util.List;

public interface ProjectDataExtractor {

    List<Project> fetch();

    void Save(List<Project> projects);


}
