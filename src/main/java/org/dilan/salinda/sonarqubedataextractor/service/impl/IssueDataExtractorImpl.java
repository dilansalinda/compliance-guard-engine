package org.dilan.salinda.sonarqubedataextractor.service.impl;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.dilan.salinda.sonarqubedataextractor.config.AppConfig;
import org.dilan.salinda.sonarqubedataextractor.dto.IssuesDTO;
import org.dilan.salinda.sonarqubedataextractor.model.*;
import org.dilan.salinda.sonarqubedataextractor.repository.*;
import org.dilan.salinda.sonarqubedataextractor.service.IssueDataExtractor;
import org.dilan.salinda.sonarqubedataextractor.service.SonarQubeService;
import org.dilan.salinda.sonarqubedataextractor.util.DateUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.stream.LongStream.of;
import static org.dilan.salinda.sonarqubedataextractor.util.Utils.findMaxPages;

@Service
@Slf4j
public class IssueDataExtractorImpl implements IssueDataExtractor {

    private final AppConfig appConfig;
    private final SonarQubeService sonarQubeService;

    private final IssueRepository issueRepository;
    private final ProjectRepository projectRepository;
    private final OrganizationRepository organizationRepository;
    private final IssueTagsRepository issueTagsRepository;
    private final IssueTagRepository issueTagRepository;
    private String organization;
    private String authorization;


    public IssueDataExtractorImpl(AppConfig appConfig, SonarQubeService sonarQubeService, IssueRepository issueRepository, ProjectRepository projectRepository, OrganizationRepository organizationRepository, IssueTagsRepository issueTagsRepository, IssueTagRepository issueTagRepository) {
        this.appConfig = appConfig;
        this.sonarQubeService = sonarQubeService;
        this.issueRepository = issueRepository;
        this.projectRepository = projectRepository;
        this.organizationRepository = organizationRepository;
        this.issueTagsRepository = issueTagsRepository;
        this.issueTagRepository = issueTagRepository;
    }

    @Override
    @PostConstruct
    public void init() {
        this.organization = appConfig.getOrganization();
        this.authorization = appConfig.getAuthorization();
    }

    @Override
    public void fetch() {
        List<String> projects = fetchPublicProjects();
        of(findMaxPages(sonarQubeService.searchIssues(
                Map.of("organization", organization, "componentKeys", projects),
                Map.of("authorization", authorization)).getPaging()))
                .forEach(page ->
                {
                    List<IssuesDTO> issuesDTO = sonarQubeService.searchIssues(
                            Map.of("organization", organization, "componentKeys", projects),
                            Map.of("authorization", authorization)).getIssues();

                    save(issuesDTO);

                });
    }

    @Override
    public void save(List<IssuesDTO> issues) {
        issues.forEach((issueObj -> {
            Issue issue = checkIssueExists(issueObj);
            if (Objects.isNull(issue)) {
                issue = new Issue();
                BeanUtils.copyProperties(issueObj, issue);
                setProject(issueObj, issue);
                setOrganization(issueObj, issue);

                Long creationTimestamp = null;
                try {
                    creationTimestamp = DateUtil.provideDateFormat().parse(issueObj.getCreationDate()).getTime();
                    Long updateTimestamp = DateUtil.provideDateFormat().parse(issueObj.getCreationDate()).getTime();


                    issue.setTextRangeStartLine(issueObj.getTextRange().getStartLine());
                    issue.setTextRangeEndLine(issueObj.getTextRange().getEndLine());
                    issue.setTextRangeStartOffset(issueObj.getTextRange().getStartOffset());
                    issue.setTextRangeEndOffset(issueObj.getTextRange().getEndOffset());
                    issue.setCreationDate(creationTimestamp);
                    issue.setUpdateDate(updateTimestamp);
                    issue.setImpactsSeverity(issueObj.getImpacts().get(0).getSeverity());
                    issue.setImpactsSoftwareQuality(issueObj.getImpacts().get(0).getSoftwareQuality());

                    issueRepository.save(issue);

                    Issue finalIssue = issue;
                    issueObj.getTags().forEach(tagObj -> {
                        IssueTag issueTag = setTags(tagObj);
                        setIssueTags(issueTag, finalIssue);
                    });
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            } else {
                log.info("Issue ({}) already exists!! updating Issue", issue.getKey());
            }
        }));
    }

    private void setIssueTags(IssueTag issueTag, Issue finalIssue) {
        Optional<IssueTags> issueTagsOptional = issueTagsRepository.findIssueTagsByIssueTagAndIssue(issueTag, finalIssue);
        if (issueTagsOptional.isPresent()) {
            log.info("Issue Tag ({}) already exists for Issue Key ({})!!", issueTag.getName(), finalIssue.getKey());
        } else {
            IssueTags issueTags = new IssueTags();
            issueTags.setIssueTag(issueTag);
            issueTags.setIssue(finalIssue);
            issueTagsRepository.save(issueTags);
        }
    }

    private IssueTag setTags(String tagObj) {
        Optional<IssueTag> issueTagOptional = issueTagRepository.findByName(tagObj);
        IssueTag issueTag;
        if (issueTagOptional.isPresent()) {
            issueTag = issueTagOptional.get();
        } else {
            issueTag = new IssueTag();
            issueTag.setName(tagObj);
            issueTagRepository.save(issueTag);
        }
        return issueTag;
    }

    private void setOrganization(IssuesDTO issueObj, Issue newIssue) {
        Optional<Organization> oraganizationOptional = organizationRepository.findByKey(issueObj.getOrganization());
        if (oraganizationOptional.isPresent()) {
            newIssue.setOrganization(oraganizationOptional.get());
        } else {
            log.info("Error while finding Issue Organization ({})", issueObj.getOrganization());
        }
    }

    private void setProject(IssuesDTO issueObj, Issue newIssue) {
        Optional<Project> projectOptional = projectRepository.findByKey(issueObj.getProject());
        if (projectOptional.isPresent()) {
            newIssue.setProject(projectOptional.get());
        } else {
            log.info("Error while finding Issue Project ({})", issueObj.getProject());
        }
    }

    private Issue checkIssueExists(IssuesDTO issue) {
        return issueRepository.findByKey(issue.getKey());
    }

    private List<String> fetchPublicProjects() {
        return projectRepository.findByVisibility("public")
                .stream()
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(Project::getKey)
                .collect(Collectors.toList());
    }
}
