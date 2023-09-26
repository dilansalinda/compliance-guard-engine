package org.dilan.salinda.sonarqubedataextractor.service.impl;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.dilan.salinda.sonarqubedataextractor.DTO.IssuesDTO;
import org.dilan.salinda.sonarqubedataextractor.config.AppConfig;
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
    private String Organization;
    private String Authorization;



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
        this.Organization = appConfig.getOrganization();
        this.Authorization = appConfig.getAuthorization();
    }

    @Override
    public void fetch(List<String> projects) {

        of(findMaxPages(sonarQubeService.searchIssues(
                Map.of("organization", Organization,"componentKeys",projects),
                Map.of("authorization",Authorization)).getPaging()))
                .forEach((page) ->
                {
                   List<IssuesDTO> issuesDTO = sonarQubeService.searchIssues(
                            Map.of("organization", Organization,"componentKeys",projects),
                            Map.of("authorization",Authorization)).getIssues();

                   Save(issuesDTO);
                });
    }

    @Override
    public void Save(List<IssuesDTO> issues) {
            issues.forEach((issueObj -> {
                Issue issue = checkIssueExists(issueObj);
                if (Objects.isNull(issue)) {
                    Issue newIssue = new Issue();
                    BeanUtils.copyProperties(issueObj, newIssue);
                    Optional<Project> projectOptional = projectRepository.findByKey(issueObj.getProject());
                            if(projectOptional.isPresent()) {
                                newIssue.setProject(projectOptional.get());
                            } else {
                                log.info("Error while finding Issue Project ({})", issueObj.getProject());
                            }
                    Optional<Organization> oraganizationOptional = organizationRepository.findByKey(issueObj.getOrganization());
                            if(oraganizationOptional.isPresent()) {
                                newIssue.setOrganization(oraganizationOptional.get());
                            }else {
                                log.info("Error while finding Issue Organization ({})", issueObj.getOrganization());
                            }
                    try {
                        Long creationTimestamp = DateUtil.provideDateFormat().parse(issueObj.getCreationDate()).getTime();
                        Long updateTimestamp = DateUtil.provideDateFormat().parse(issueObj.getCreationDate()).getTime();

                        newIssue.setTextRangeStartLine(issueObj.getTextRange().getStartLine());
                        newIssue.setTextRangeEndLine(issueObj.getTextRange().getEndLine());
                        newIssue.setTextRangeStartOffset(issueObj.getTextRange().getStartOffset());
                        newIssue.setTextRangeEndOffset(issueObj.getTextRange().getEndOffset());
                        newIssue.setCreationDate(creationTimestamp);
                        newIssue.setUpdateDate(updateTimestamp);
                        newIssue.setImpactsSeverity(issueObj.getImpacts().get(0).getSeverity());
                        newIssue.setImpactsSoftwareQuality(issueObj.getImpacts().get(0).getSoftwareQuality());
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                    issueRepository.save(newIssue);

                    issueObj.getTags().forEach(tagObj -> {
                        Optional<IssueTag> issueTagOptional = issueTagRepository.findByName(tagObj);
                        IssueTag issueTag;
                        if (issueTagOptional.isPresent()) {
                            issueTag = issueTagOptional.get();
                        } else {
                            issueTag = new IssueTag();
                            issueTag.setName(tagObj);
                            issueTagRepository.save(issueTag);
                        }
                        Optional<IssueTags> issueTagsOptional = issueTagsRepository.findIssueTagsByIssueTagAndIssue(issueTag,newIssue);
                        if(issueTagsOptional.isPresent()) {
                            log.info("Issue Tag ({}) already exists for Issue Key ({})!!", issueTag.getName(), issue.getKey());
                        } else {
                            IssueTags issueTags = new IssueTags();
                            issueTags.setIssueTag(issueTag);
                            issueTags.setIssue(newIssue);
                            issueTagsRepository.save(issueTags);
                        }

                    });



                } else {
                    log.info("Issue ({}) already exists!! updating Issue", issue.getKey());
                }
            }));
    }

    private Issue checkIssueExists(IssuesDTO issue) {
        return issueRepository.findByKey(issue.getKey());
    }
}
