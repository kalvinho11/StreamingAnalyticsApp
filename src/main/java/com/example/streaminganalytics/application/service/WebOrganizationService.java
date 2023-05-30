package com.example.streaminganalytics.application.service;

import com.example.streaminganalytics.domain.Organization;

public interface WebOrganizationService {

    Organization addOrganization(Organization organization);

    void getOrganization();
}
