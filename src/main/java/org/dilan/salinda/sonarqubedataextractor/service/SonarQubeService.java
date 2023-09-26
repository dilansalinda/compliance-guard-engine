package org.dilan.salinda.sonarqubedataextractor.service;


import org.dilan.salinda.sonarqubedataextractor.DTO.IssueSearchDTO;
import org.dilan.salinda.sonarqubedataextractor.DTO.ProjectDTO;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;

import java.util.Map;


public interface SonarQubeService {

    @GetExchange(url = "/api/components/search_projects",accept = "application/json")
    ProjectDTO searchProjects(@RequestParam Map<String,Object> queryParam, @RequestHeader Map<String,Object> requestHeader);

    @GetExchange(url = "api/issues/search",accept = "application/json")
    IssueSearchDTO searchIssues(@RequestParam Map<String,Object> queryParam, @RequestHeader Map<String,Object> requestHeader);
}

