package org.dilan.salinda.sonarqubedataextractor;

import org.apache.tomcat.websocket.Constants;
import org.dilan.salinda.sonarqubedataextractor.config.AppConfig;
import org.dilan.salinda.sonarqubedataextractor.model.Organization;
import org.dilan.salinda.sonarqubedataextractor.repository.OrganizationRepository;
import org.dilan.salinda.sonarqubedataextractor.service.IssueDataExtractor;
import org.dilan.salinda.sonarqubedataextractor.service.SonarQubeService;
import org.dilan.salinda.sonarqubedataextractor.service.impl.ProjectDataExtractorImpl;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@SpringBootApplication
public class Application {
    private final AppConfig appConfig;
    private final OrganizationRepository organizationRepository;

    public Application(AppConfig appConfig, OrganizationRepository organizationRepository) {
        this.appConfig = appConfig;
        this.organizationRepository = organizationRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    SonarQubeService sonarQubeService() {
        WebClient client = WebClient.builder()
                .baseUrl(appConfig.getSonarqubeBaseURL())
                .defaultHeader(Constants.AUTHORIZATION_HEADER_NAME, appConfig.getAuthorization())
                .exchangeStrategies(ExchangeStrategies.builder().codecs(c -> c.defaultCodecs().enableLoggingRequestDetails(true)).build())
                .build();
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builder(WebClientAdapter.forClient(client)).build();
        return factory.createClient(SonarQubeService.class);

    }

    @Bean
    CommandLineRunner commandLineRunner(ProjectDataExtractorImpl projectDataExtractor, IssueDataExtractor issueDataExtractor) {
        organizationRepository.save(new Organization(1, appConfig.getOrganizationName(), appConfig.getOrganizationKey()));
        projectDataExtractor.fetch();
        return args -> issueDataExtractor.fetch();

    }



}
