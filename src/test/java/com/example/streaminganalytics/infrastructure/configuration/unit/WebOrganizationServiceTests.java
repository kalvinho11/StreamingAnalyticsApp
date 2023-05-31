package com.example.streaminganalytics.infrastructure.configuration.unit;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;


import com.example.streaminganalytics.application.service.impl.WebOrganizationServiceImpl;
import com.example.streaminganalytics.domain.Organization;
import com.example.streaminganalytics.domain.repository.OrganizationRepository;
import com.example.streaminganalytics.infrastructure.database.DatabaseSequenceGenerator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;

@ExtendWith(MockitoExtension.class)
public class WebOrganizationServiceTests {

    @Mock
    private OrganizationRepository organizationRepository;

    @Mock
    private DatabaseSequenceGenerator databaseSequenceGenerator;

    @InjectMocks
    private WebOrganizationServiceImpl webOrganizationService;


    @Test
    public void addOrganization() {
        Organization organizationToSave = Organization.builder().organizationName("Company1")
                .active(true).organizationCountryISO("ES").build();
        Organization expectedOrganization = Organization.builder().organizationId(1L).organizationName("Company1")
                .active(true).organizationCountryISO("ES").build();

        when(organizationRepository.save(expectedOrganization)).thenReturn(expectedOrganization);
        when(databaseSequenceGenerator.generateSequence(Organization.SEQUENCE_NAME)).thenReturn(1L);

        Organization organization = webOrganizationService.addOrganization(organizationToSave);

        assertThat(organization).isNotNull();
        assertThat(organization).isEqualTo(expectedOrganization);
    }

    @Test()
    public void addAnExistingOrganization() {
        Organization organizationToSave = Organization.builder().organizationId(100L).organizationName("Company1")
                .active(true).organizationCountryISO("ES").build();

        when(organizationRepository.findByOrganizationName(organizationToSave.getOrganizationName())).thenReturn(Arrays.
                asList(organizationToSave));

        Assertions.assertThrows(ResponseStatusException.class, () -> {
            webOrganizationService.addOrganization(organizationToSave);
        });

    }


}
