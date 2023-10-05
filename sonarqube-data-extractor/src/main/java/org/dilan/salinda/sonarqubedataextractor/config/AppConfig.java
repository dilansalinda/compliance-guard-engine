package org.dilan.salinda.sonarqubedataextractor.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class AppConfig {

    @Value("${authorization}")
    private String authorization;

    @Value("${organization.name}")
    private String organizationName;

    @Value("${organization.key}")
    private String organizationKey;

    @Value("${sonarqube.base.url}")
    private String sonarqubeBaseURL;
}
