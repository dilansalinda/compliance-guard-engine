package org.dilan.salinda.sonarqubedataextractor.service.impl;

import org.dilan.salinda.sonarqubedataextractor.model.Project;
import org.dilan.salinda.sonarqubedataextractor.service.ProjectDataExtractor;
import org.dilan.salinda.sonarqubedataextractor.util.SonarQubeService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class ProjectDataExtractorImpl implements ProjectDataExtractor {

    private final SonarQubeService sonarQubeService;

    public ProjectDataExtractorImpl(SonarQubeService sonarQubeService) {
        this.sonarQubeService = sonarQubeService;
    }

    @Override
    public List<Project> fetch() {
       Project projects = sonarQubeService.findProjects(Map.of("Authorization","b69487a06f4e38767f00a79985366284a7e56b7f"), Map.of("organization","orgdev"));
        System.out.println(projects);

       return null;

    }

    @Override
    public void Save(List<Project> projects) {

    }


}
