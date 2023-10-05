package org.dilan.salinda.sonarqubedataextractor.repository;

import org.dilan.salinda.sonarqubedataextractor.model.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface OrganizationRepository extends JpaRepository<Organization, Long> {
    Optional<Organization> findByKey(String organization);
}
