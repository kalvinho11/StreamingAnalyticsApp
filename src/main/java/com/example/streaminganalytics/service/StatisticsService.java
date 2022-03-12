package com.example.streaminganalytics.service;

import com.example.streaminganalytics.domain.DataInput;

/**
 * Service that calculates statistics from the data.
 */
public interface StatisticsService {

    public void doCalculations(final DataInput input);
}
