package com.example.streaminganalytics.infrastructure.controller;

import com.example.streaminganalytics.application.service.WebOrganizationService;
import com.example.streaminganalytics.domain.Organization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/organizations")
public class StatisticsController {

    @Autowired
    private WebOrganizationService webOrganizationService;

    @PostMapping
    public ResponseEntity<Object> addOrganization(@RequestBody Organization organization) {
        Organization savedOrganization;
        try {
            savedOrganization = webOrganizationService.addOrganization(organization);
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getReason());
        }
        return ResponseEntity.ok(savedOrganization);
    }
}
