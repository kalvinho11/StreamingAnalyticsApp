package com.example.streaminganalytics.infrastructure.controller;

import com.example.streaminganalytics.application.service.WebOrganizationService;
import com.example.streaminganalytics.domain.Organization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/organizations")
public class OrganizationController {

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

    @GetMapping
    public ResponseEntity<Object> getOrganization(@RequestParam Long organizationId) {
        Organization organization;
        try {
            organization = webOrganizationService.getOrganization(organizationId);
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getReason());
        }
        return ResponseEntity.ok(organization);
    }

    @DeleteMapping
    public ResponseEntity<Object> deleteOrganization(@RequestParam Long organizationId) {
        try {
            webOrganizationService.deleteOrganization(organizationId);
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getReason());
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
