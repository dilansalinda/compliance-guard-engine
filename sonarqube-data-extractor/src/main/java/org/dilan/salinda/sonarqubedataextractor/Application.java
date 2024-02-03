package org.dilan.salinda.sonarqubedataextractor;

import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.websocket.Constants;
import org.dilan.salinda.sonarqubedataextractor.config.AppConfig;
import org.dilan.salinda.sonarqubedataextractor.service.SonarQubeService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@SpringBootApplication
@EnableScheduling
@Slf4j
public class Application {
    private final AppConfig appConfig;

    public Application(AppConfig appConfig) {
        this.appConfig = appConfig;
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    SonarQubeService sonarQubeService() {
        WebClient client = WebClient.builder()
                .baseUrl(appConfig.getSonarqubeBaseURL())
                .defaultHeader(Constants.AUTHORIZATION_HEADER_NAME, appConfig.getAuthorization())
                .exchangeStrategies(
                        ExchangeStrategies.builder()
                                .codecs(c -> c.defaultCodecs()
                                        .enableLoggingRequestDetails(true)).build())
                .build();
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builder(WebClientAdapter.forClient(client)).build();
        return factory.createClient(SonarQubeService.class);

    }
//
//    @Bean
//    public void dataExtraction() {
//        log.info("Data Extraction Starting : {}", Instant.now());
//        organizationRepository.save(new Organization(1, appConfig.getOrganizationName(), appConfig.getOrganizationKey()));
//        projectDataExtractor.fetch();
//        issueDataExtractor.fetch();
//    }



}
