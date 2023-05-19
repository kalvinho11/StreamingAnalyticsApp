package com.example.streaminganalytics.application.service.impl;

import com.example.streaminganalytics.domain.DataInput;
import com.example.streaminganalytics.domain.DataStream;
import com.example.streaminganalytics.domain.StreamingAnalytics;
import com.example.streaminganalytics.domain.repository.StreamingAnalyticsRepository;
import com.example.streaminganalytics.application.service.StatisticsService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.math3.stat.StatUtils;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

/**
 * The statistics service implementation.
 */
@Slf4j
@Service
public class StatisticsServiceImpl implements StatisticsService {

    @Autowired
    private StreamingAnalyticsRepository streamingAnalyticsRepository;

    /**
     * Group the DataStream objects with their data points for each data stream id and then make the calculations.
     *
     * @param input the input from the queue
     */
    @Override
    public void doCalculations(final DataInput input) {

        final Map<String, List<DataStream>> mapStream = input.getDatastreams().stream()
                .collect(Collectors.groupingBy(DataStream::getId));

        List<StreamingAnalytics> analytics = new ArrayList<>();

        for (Map.Entry<String, List<DataStream>> entry : mapStream.entrySet()) {
            final DescriptiveStatistics stats = new DescriptiveStatistics();
            entry.getValue().get(0).getDatapoints().stream().forEach(datapoint -> stats.addValue(datapoint.getValue()));
            analytics.add(StreamingAnalytics.builder().dataStreamingId(entry.getKey())
                    .createdAt(Date.from(Instant.now())).mean(stats.getMean())
                    .median(stats.getPercentile(50)).mode(StatUtils.mode(stats.getValues()))
                    .standardDesviation(stats.getStandardDeviation()).quartiles(getQuartiles(stats))
                    .max(stats.getMax()).min(stats.getMin()).build());
        }
        this.streamingAnalyticsRepository.saveAll(analytics);
        log.debug("Saved in Mongo.");
    }

    /**
     * Calculates the three quartiles of the dataset given.
     *
     * @param stats, the DescriptiveStatistics object.
     * @return the list of the three quartiles.
     */
    private List<Double> getQuartiles(final DescriptiveStatistics stats) {

        final Double quartile1 = stats.getPercentile(25);
        final Double quartile2 = stats.getPercentile(50);
        final Double quartile3 = stats.getPercentile(75);

        return Arrays.asList(quartile1, quartile2, quartile3);
    }
}
