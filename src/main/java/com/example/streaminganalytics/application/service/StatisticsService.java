package com.example.streaminganalytics.application.service;

import com.example.streaminganalytics.domain.DataInput;
import com.example.streaminganalytics.domain.StreamingAnalytics;

import java.util.List;

/**
 * Service that calculates statistics from the data.
 */
public interface StatisticsService {

    List<StreamingAnalytics> calculateAndSaveAnalytics(final DataInput input);
}
