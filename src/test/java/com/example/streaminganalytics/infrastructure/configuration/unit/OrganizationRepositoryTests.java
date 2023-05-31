package com.example.streaminganalytics.infrastructure.configuration.unit;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.streaminganalytics.domain.Organization;
import com.example.streaminganalytics.domain.repository.OrganizationRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

@DataMongoTest()
public class OrganizationRepositoryTests {

    @Autowired
    private OrganizationRepository organizationRepository;

    @AfterEach
    void cleanUpDatabase() {
        organizationRepository.deleteAll();
    }

    @Test
    void shouldSaveNewOrganization() {
        Organization organization = Organization.builder().organizationId(1L).organizationName("Company1").active(true)
                .organizationCountryISO("ES").build();
        Organization expectedOrganization = Organization.builder().organizationId(1L).organizationName("Company1")
                .active(true).organizationCountryISO("ES").build();

        Organization savedOrganization = organizationRepository.save(organization);

        assertThat(savedOrganization).isEqualTo(expectedOrganization);

    }


}
