package com.example.streaminganalytics.application.service;

import com.example.streaminganalytics.domain.DataInput;

/**
 * Service that calculates statistics from the data.
 */
public interface StatisticsService {

    void doCalculations(final DataInput input);
}
