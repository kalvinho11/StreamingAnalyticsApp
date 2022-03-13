package com.example.streaminganalytics.repository;

import com.example.streaminganalytics.domain.StreamingAnalytics;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * The StreamingAnalytics repository.
 */
public interface StreamingAnalyticsRepository extends MongoRepository<StreamingAnalytics, Integer> {
}
