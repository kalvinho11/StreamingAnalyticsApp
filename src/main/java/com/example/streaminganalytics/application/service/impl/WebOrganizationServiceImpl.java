package com.example.streaminganalytics.application.service.impl;

import com.example.streaminganalytics.application.service.WebOrganizationService;
import com.example.streaminganalytics.domain.Organization;
import com.example.streaminganalytics.domain.repository.OrganizationRepository;
import com.example.streaminganalytics.infrastructure.database.DatabaseSequenceGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class WebOrganizationServiceImpl implements WebOrganizationService {

    @Autowired
    private OrganizationRepository organizationRepository;

    @Autowired
    private DatabaseSequenceGenerator databaseSequenceGenerator;

    @Override
    public Organization addOrganization(Organization organization) {

        if (organizationExists(organization.getOrganizationName())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Organization name already exists");
        }
        organization.setOrganizationId(databaseSequenceGenerator.generateSequence(Organization.SEQUENCE_NAME));

        return organizationRepository.save(organization);
    }

    @Override
    public void getOrganization() {

    }

    private boolean organizationExists(String organizationName) {
        return !organizationRepository.findByOrganizationName(organizationName).isEmpty();
    }

}
