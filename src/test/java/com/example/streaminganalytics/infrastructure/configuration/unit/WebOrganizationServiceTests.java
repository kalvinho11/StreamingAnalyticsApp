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
import java.util.Collections;

@ExtendWith(MockitoExtension.class)
public class WebOrganizationServiceTests {

    @Mock
    private OrganizationRepository organizationRepository;

    @Mock
    private DatabaseSequenceGenerator databaseSequenceGenerator;

    @InjectMocks
    private WebOrganizationServiceImpl webOrganizationService;


    @Test
    void shouldAddOrganization() {
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

    @Test
    void shouldThrowExceptionWhenTryingToAddAnExistingOrganization() {
        Organization organizationToSave = Organization.builder().organizationId(100L).organizationName("Company1")
                .active(true).organizationCountryISO("ES").build();

        when(organizationRepository.findByOrganizationName(organizationToSave.getOrganizationName())).thenReturn(Arrays.
                asList(organizationToSave));

        Assertions.assertThrows(ResponseStatusException.class, () -> webOrganizationService.addOrganization(
                organizationToSave));

    }

    @Test
    void shouldGetOrganizationByOrganizationId() {
        Organization organization = Organization.builder().organizationId(1L).organizationName("Company1")
                .active(true).organizationCountryISO("ES").build();

        when(organizationRepository.findByOrganizationId(1L)).thenReturn(Collections.singletonList(organization));

        assertThat(webOrganizationService.getOrganization(1L)).isEqualTo(organization);
    }

    @Test
    void shouldThrowExceptionWhenTryingToGETANonExistingOrganization() {

        when(organizationRepository.findByOrganizationId(1L)).thenReturn(Collections.emptyList());

        Assertions.assertThrows(ResponseStatusException.class, () -> webOrganizationService.getOrganization(1L));
    }

    @Test
    void shouldDeleteOrganization() {
        when(organizationRepository.deleteByOrganizationId(1L)).thenReturn(1);

        assertThat(webOrganizationService.deleteOrganization(1L)).isEqualTo(1);
    }

    @Test
    void shouldThrowExceptionWhenTryingToDeleteANonExistingOrganization() {
        when(organizationRepository.deleteByOrganizationId(1L)).thenReturn(0);

        Assertions.assertThrows(ResponseStatusException.class, () -> webOrganizationService.deleteOrganization(1L));
    }


}
