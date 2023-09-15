package org.dilan.salinda.sonarqubedataextractor.util;

import org.dilan.salinda.sonarqubedataextractor.model.Project;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;

import java.util.List;
import java.util.Map;


public interface SonarQubeService {

    @GetExchange(url = "/api/components/search_projects",accept = "application/json")
   Project findProjects(@RequestHeader Map<String,String> header, @RequestParam Map<String,String> queryParam);
}
