package com.example.streaminganalytics.infrastructure.configuration;

import com.example.streaminganalytics.domain.Organization;
import com.example.streaminganalytics.domain.repository.OrganizationRepository;
import com.example.streaminganalytics.infrastructure.controller.OrganizationController;
import com.example.streaminganalytics.infrastructure.database.DatabaseSequenceGenerator;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class OrganizationWebIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private OrganizationController organizationController;

    @Autowired
    private OrganizationRepository organizationRepository;

    @Autowired
    private DatabaseSequenceGenerator databaseSequenceGenerator;

    @AfterEach
    void cleanUpDatabase() {
        organizationRepository.deleteAll();
        databaseSequenceGenerator.deleteAll();
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

        mockMvc.perform(post("/organizations")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(organization1)))
                .andExpect(status().isOk());

        mockMvc.perform(post("/organizations")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(organization2)))
                .andExpect(status().isOk());

        List<Organization> organizationsInMongo = organizationRepository.findAll();

        assertThat(organizationsInMongo.stream().filter(organization -> organization.getOrganizationName()
                .equals("Company1")).findFirst().get().getOrganizationId()).isEqualTo(1L);

        assertThat(organizationsInMongo.stream().filter(organization -> organization.getOrganizationName()
                .equals("Company2")).findFirst().get().getOrganizationId()).isEqualTo(2L);

    }

    @Test
    void shouldGetAnOrganizationByOrganizationId() throws Exception {
        Organization organization = Organization.builder().organizationName("Company1").active(true)
                .organizationCountryISO("ES").build();

        mockMvc.perform(post("/organizations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(organization)))
                .andExpect(status().isOk());

        String response = mockMvc.perform(get("/organizations").param("organizationId", "1"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        organization.setOrganizationId(1L);

        assertThat(objectMapper.readValue(response, Organization.class)).isEqualTo(organization);
    }

    @Test
    void shouldGetANonExistingOrganizationAndGet404() throws Exception {
        mockMvc.perform(get("/organizations").param("organizationId", "1"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("There is no organization with that id."));
    }

    @Test
    void shouldSaveAndDeleteAnOrganization() throws Exception {
        Organization organizationToSave = Organization.builder().organizationName("Company1").active(true)
                .organizationCountryISO("ES").build();

        String responseBody = mockMvc.perform(post("/organizations")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(organizationToSave)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse().getContentAsString();

        Organization savedOrganization = objectMapper.readValue(responseBody, Organization.class);

        mockMvc.perform(delete("/organizations").param("organizationId", savedOrganization
                .getOrganizationId().toString()))
                .andExpect(status().isOk());

        assertThat(organizationRepository.findByOrganizationName("Company1")).isEmpty();

    }

    @Test
    void shouldFailWhenTryingToDeleteNonExistingOrganization() throws Exception {
        mockMvc.perform(delete("/organizations").param("organizationId", "1"))
                .andExpect(status().isNotFound());
    }


}
