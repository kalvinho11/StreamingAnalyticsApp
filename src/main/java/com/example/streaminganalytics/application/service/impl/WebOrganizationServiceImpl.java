package com.example.streaminganalytics.application.service.impl;

import com.example.streaminganalytics.application.service.WebOrganizationService;
import com.example.streaminganalytics.domain.Organization;
import com.example.streaminganalytics.domain.repository.OrganizationRepository;
import com.example.streaminganalytics.infrastructure.database.DatabaseSequenceGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

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
    public Organization getOrganization(Long organizationId) {
        List<Organization> organizations = organizationRepository.findByOrganizationId(organizationId);
        if (organizations.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "There is no organization with that id.");
        }
        return organizations.get(0);
    }

    @Override
    public Integer deleteOrganization(Long organizationId) {
        Integer deletedOrganizations = organizationRepository.deleteByOrganizationId(organizationId);
        if (deletedOrganizations == 0) {
            throw  new ResponseStatusException(HttpStatus.NOT_FOUND, "There is no organization with that id.");
        }
        return deletedOrganizations;
    }

    private boolean organizationExists(String organizationName) {
        return !organizationRepository.findByOrganizationName(organizationName).isEmpty();
    }

}
