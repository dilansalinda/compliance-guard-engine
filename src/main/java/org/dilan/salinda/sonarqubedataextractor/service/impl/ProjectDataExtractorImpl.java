package org.dilan.salinda.sonarqubedataextractor.service.impl;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.dilan.salinda.sonarqubedataextractor.config.AppConfig;
import org.dilan.salinda.sonarqubedataextractor.dto.ComponentsDTO;
import org.dilan.salinda.sonarqubedataextractor.model.Project;
import org.dilan.salinda.sonarqubedataextractor.model.ProjectTag;
import org.dilan.salinda.sonarqubedataextractor.model.Tag;
import org.dilan.salinda.sonarqubedataextractor.repository.OrganizationRepository;
import org.dilan.salinda.sonarqubedataextractor.repository.ProjectRepository;
import org.dilan.salinda.sonarqubedataextractor.repository.ProjectTagRepository;
import org.dilan.salinda.sonarqubedataextractor.repository.TagRepository;
import org.dilan.salinda.sonarqubedataextractor.service.ProjectDataExtractor;
import org.dilan.salinda.sonarqubedataextractor.service.SonarQubeService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static java.util.stream.LongStream.of;
import static org.dilan.salinda.sonarqubedataextractor.util.Utils.findMaxPages;

@Service
@Slf4j
public class ProjectDataExtractorImpl implements ProjectDataExtractor {

    private final AppConfig appConfig;
    private final SonarQubeService sonarQubeService;
    private String organization;
    private String authorization;
    private final ProjectRepository projectRepository;
    private final TagRepository tagRepository;
    private final ProjectTagRepository projectTagRepository;
    private final OrganizationRepository organizationRepository;

    public ProjectDataExtractorImpl(AppConfig appConfig, SonarQubeService sonarQubeService, ProjectRepository projectRepository,
                                    TagRepository tagRepository, ProjectTagRepository projectTagRepository, OrganizationRepository organizationRepository) {
        this.appConfig = appConfig;
        this.sonarQubeService = sonarQubeService;
        this.projectRepository = projectRepository;
        this.tagRepository = tagRepository;
        this.projectTagRepository = projectTagRepository;
        this.organizationRepository = organizationRepository;
    }

    @Override
    @PostConstruct
    public void init() {
        this.organization = appConfig.getOrganization();
        this.authorization = appConfig.getAuthorization();
    }


    @Override
    public void fetch() {
        of(findMaxPages(sonarQubeService.searchProjects(
                Map.of("organization", organization),
                Map.of("authorization", authorization)).getPaging()))
                .forEach(page -> {
                            List<ComponentsDTO> projectComponents =
                                    sonarQubeService.searchProjects(
                                                    Map.of("organization", organization, "p", page, "ps", 500),
                                                    Map.of("authorization", authorization))
                                            .getComponents();
                    save(projectComponents);
                        }
                );

    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public void save(List<ComponentsDTO> projects) {

        projects.forEach(componentsDTO -> {
            Optional<Project> project = projectRepository.findByNameAndKey(componentsDTO.getName(), componentsDTO.getKey());
            if (project.isPresent()) {
                log.info("Project ({}) already exists!!", project.get().getName());
            } else {
                Project newProject = new Project();
                BeanUtils.copyProperties(componentsDTO, newProject);
                Optional<org.dilan.salinda.sonarqubedataextractor.model.Organization> organizationOptional = organizationRepository.findByKey(componentsDTO.getOrganization());
                if (organizationOptional.isPresent()) {
                    newProject.setOrganization(organizationOptional.get());
                } else {
                    log.info("Unable to find Project ({}) organization ({})!!", project.get().getName(), componentsDTO.getOrganization());
                }
                List<Tag> tags = saveTags(componentsDTO);
                Project savedProject = projectRepository.save(newProject);
                saveProjectTags(tags, savedProject);
            }
        });
    }



    @Transactional(readOnly = true)
    public List<Tag> saveTags(ComponentsDTO componentsDTO) {
        List<Tag> tagList = new ArrayList<>();
        componentsDTO.getTags().forEach((tag -> {
                    Tag dbTag = tagRepository.findByName(tag);
                    if (!Objects.isNull(dbTag)) {
                        tagList.add(dbTag);
                    } else {
                        Tag newTag = new Tag();
                        newTag.setName(tag);
                        tagList.add(tagRepository.save(newTag));
                        log.info("New Tag ({}) saved!", tag);
                    }
                })
        );
        return tagList;
    }

    private void saveProjectTags(List<Tag> tagList, Project project) {
        tagList.forEach(tag -> {
            ProjectTag projectTag = new ProjectTag();
            projectTag.setTag(tag);
            projectTag.setProject(project);

            projectTagRepository.save(projectTag);
            log.info("Project ({}) - Tag ({}) Saved!", project.getName(), tag.getName());
        });
    }


}
