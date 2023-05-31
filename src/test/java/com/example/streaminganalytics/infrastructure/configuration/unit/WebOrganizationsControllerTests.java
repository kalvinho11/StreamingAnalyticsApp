package com.example.streaminganalytics.infrastructure.configuration.unit;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

import com.example.streaminganalytics.application.service.impl.WebOrganizationServiceImpl;
import com.example.streaminganalytics.domain.Organization;
import com.example.streaminganalytics.infrastructure.controller.StatisticsController;
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
    private StatisticsController statisticsController;

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


}
