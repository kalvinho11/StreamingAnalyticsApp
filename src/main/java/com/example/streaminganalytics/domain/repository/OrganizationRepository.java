package com.example.streaminganalytics.domain.repository;

import com.example.streaminganalytics.domain.Organization;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrganizationRepository extends MongoRepository<Organization, String> {

    @Query("{'organizationName' : ?0}")
    List<Organization> findByOrganizationName(String organizationName);

    @Query("{'active: true'}")
    List<Organization> findAllActive();
}
