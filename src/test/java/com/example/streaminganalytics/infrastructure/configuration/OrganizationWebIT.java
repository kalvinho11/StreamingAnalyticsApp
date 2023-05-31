package com.example.streaminganalytics.infrastructure.configuration;

import com.example.streaminganalytics.domain.Organization;
import com.example.streaminganalytics.domain.repository.OrganizationRepository;
import com.example.streaminganalytics.infrastructure.controller.StatisticsController;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class OrganizationWebIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private StatisticsController statisticsController;

    @Autowired
    private OrganizationRepository organizationRepository;

    @AfterEach
    void cleanUpDatabase() {
        organizationRepository.deleteAll();
    }

    @Test
    void shouldSaveOneOrganization() throws Exception {
        Organization organizationToSave = Organization.builder().organizationName("Company1").active(true)
                .organizationCountryISO("ES").build();
        Organization expectedOrganization = Organization.builder().organizationId(1L).organizationName("Company1")
                .active(true).organizationCountryISO("ES").build();

        mockMvc.perform(post("/organizations").contentType(MediaType.APPLICATION_JSON).content(objectMapper
                .writeValueAsString(organizationToSave))).andExpect(status().isOk());

        Organization organizationInMongo = organizationRepository.findAll().get(0);

        assertThat(organizationInMongo).isEqualTo(expectedOrganization);

    }

    @Test
    void shouldSaveTwoOrganizations() throws Exception {
        Organization organization1 = Organization.builder().organizationName("Company1").active(true)
                .organizationCountryISO("ES").build();
        Organization organization2 = Organization.builder().organizationName("Company2").active(true)
                .organizationCountryISO("FR").build();

        mockMvc.perform(post("/organizations").contentType(MediaType.APPLICATION_JSON).content(objectMapper
                .writeValueAsString(organization1))).andExpect(status().isOk());
        mockMvc.perform(post("/organizations").contentType(MediaType.APPLICATION_JSON).content(objectMapper
                .writeValueAsString(organization2))).andExpect(status().isOk());

        List<Organization> organizationsInMongo = organizationRepository.findAll();

        assertThat(organizationsInMongo.stream().filter(organization -> organization.getOrganizationName()
                .equals("Company1")).findFirst().get().getOrganizationId()).isEqualTo(1L);

        assertThat(organizationsInMongo.stream().filter(organization -> organization.getOrganizationName()
                .equals("Company2")).findFirst().get().getOrganizationId()).isEqualTo(2L);

    }


}
