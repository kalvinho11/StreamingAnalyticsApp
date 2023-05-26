package com.example.streaminganalytics.domain.repository;

import com.example.streaminganalytics.domain.StreamingAnalytics;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * The StreamingAnalytics repository.
 */
@Repository
public interface StreamingAnalyticsRepository extends MongoRepository<StreamingAnalytics, Integer> {
}
