package org.dilan.salinda.sonarqubedataextractor;

import org.dilan.salinda.sonarqubedataextractor.config.AppConfig;
import org.dilan.salinda.sonarqubedataextractor.service.impl.ProjectDataExtractorImpl;
import org.dilan.salinda.sonarqubedataextractor.service.SonarQubeService;
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

    public Application(AppConfig appConfig) {
        this.appConfig = appConfig;
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    SonarQubeService sonarQubeService() {
        WebClient client = WebClient.builder()
                .baseUrl("https://sonarcloud.io")
                .defaultHeader("authorization",appConfig.getAuthorization())
                .exchangeStrategies(ExchangeStrategies.builder().codecs(c -> c.defaultCodecs().enableLoggingRequestDetails(true)).build())
                .build();
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builder(WebClientAdapter.forClient(client)).build();
        return factory.createClient(SonarQubeService.class);

    }

    @Bean
    CommandLineRunner commandLineRunner(ProjectDataExtractorImpl projectDataExtractor) {
        return args -> projectDataExtractor.fetch();
    }

}
