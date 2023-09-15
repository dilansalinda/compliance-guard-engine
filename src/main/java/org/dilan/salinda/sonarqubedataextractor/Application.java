package org.dilan.salinda.sonarqubedataextractor;

import org.dilan.salinda.sonarqubedataextractor.service.impl.ProjectDataExtractorImpl;
import org.dilan.salinda.sonarqubedataextractor.util.SonarQubeService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    SonarQubeService sonarQubeService() {
        WebClient client = WebClient.builder()
                .baseUrl("https://sonarcloud.io")
                .build();
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builder(WebClientAdapter.forClient(client)).build();
        return factory.createClient(SonarQubeService.class);

    }

    @Bean
    CommandLineRunner commandLineRunner(ProjectDataExtractorImpl projectDataExtractor) {
        return args -> projectDataExtractor.fetch();
    }

}
