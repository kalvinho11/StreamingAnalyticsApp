package com.example.streaminganalytics.infrastructure.configuration.unit;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

import com.example.streaminganalytics.application.service.impl.WebOrganizationServiceImpl;
import com.example.streaminganalytics.domain.Organization;
import com.example.streaminganalytics.infrastructure.controller.OrganizationController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

@ExtendWith(MockitoExtension.class)
public class WebOrganizationsControllerTests {

    @Mock
    private WebOrganizationServiceImpl webOrganizationService;

    @InjectMocks
    private OrganizationController statisticsController;

    @Test
    void shouldSaveOrganizationOK() {
        Organization organization = Organization.builder().organizationId(100L).organizationName("Company1")
                .active(true).organizationCountryISO("ES").build();

        when(webOrganizationService.addOrganization(organization)).thenReturn(organization);

        ResponseEntity<Object> response = statisticsController.addOrganization(organization);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

    }

    @Test
    @DisplayName("Try to create an existing organization.")
    void shouldGetAnHTTP409() {
        Organization organization = Organization.builder().organizationId(100L).organizationName("Company1")
                .active(true).organizationCountryISO("ES").build();

        when(webOrganizationService.addOrganization(organization)).thenThrow(new ResponseStatusException(HttpStatus
                .CONFLICT, "Organization name already exists"));

        ResponseEntity<Object> response = statisticsController.addOrganization(organization);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
        assertThat(response.getBody()).isEqualTo("Organization name already exists");

    }

    @Test
    void shouldGetOrganization() {
        Organization organization = Organization.builder().organizationId(1L).organizationName("Company1")
                .active(true).organizationCountryISO("ES").build();

        when(webOrganizationService.getOrganization(1L)).thenReturn(organization);

        ResponseEntity<Object> response = statisticsController.getOrganization(1L);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(organization);
    }

    @Test
    void shouldGetA404WhenTryingToGetANonExistingOrganization() {

        when(webOrganizationService.getOrganization(1L)).thenThrow(new ResponseStatusException(HttpStatus
                .NOT_FOUND, "There is no organization with that id."));

        ResponseEntity<Object> response = statisticsController.getOrganization(1L);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isEqualTo("There is no organization with that id.");
    }

    @Test
    void shouldDeleteOrganization() {
        when(webOrganizationService.deleteOrganization(1L)).thenReturn(1);

        ResponseEntity<Object> response = statisticsController.deleteOrganization(1L);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void shouldGetA404WhenTryingToDeleteANonExistingOrganization() {

        when(webOrganizationService.deleteOrganization(1L)).thenThrow(new ResponseStatusException(HttpStatus
                .NOT_FOUND, "There is no organization with that id."));

        ResponseEntity<Object> response = statisticsController.deleteOrganization(1L);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isEqualTo("There is no organization with that id.");
    }


}
