package org.dilan.salinda.sonarqubedataextractor.service;

import org.dilan.salinda.sonarqubedataextractor.config.AppConfig;
import org.springframework.core.annotation.Order;

public interface BaseExtractor {
    void init();
}
