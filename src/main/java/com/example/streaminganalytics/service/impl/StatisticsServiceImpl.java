package com.example.streaminganalytics.service.impl;

import com.example.streaminganalytics.domain.DataInput;
import com.example.streaminganalytics.domain.DataStream;
import com.example.streaminganalytics.domain.StatisticInfo;
import com.example.streaminganalytics.service.StatisticsService;
import org.apache.commons.math3.stat.StatUtils;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * The statistics service implementation.
 */
@Service
public class StatisticsServiceImpl implements StatisticsService {

    @Override
    public void doCalculations(final DataInput input) {

        final Map<String, List<DataStream>> mapStream = input.getDatastreams().stream()
                .collect(Collectors.groupingBy(DataStream::getId));

        Map<String, StatisticInfo> mapStatistics = new HashMap<>();

        for (Map.Entry<String, List<DataStream>> entry : mapStream.entrySet()) {
            final DescriptiveStatistics stats = new DescriptiveStatistics();
            entry.getValue().get(0).getDatapoints().stream().forEach(datapoint -> stats.addValue(datapoint.getValue()));

            mapStatistics.put(entry.getKey(), StatisticInfo.builder().mean(stats.getMean())
                    .median(stats.getPercentile(50)).mode(StatUtils.mode(stats.getValues()))
                    .standardDesviation(stats.getStandardDeviation()).quartiles(getQuartiles(stats))
                    .max(stats.getMax()).min(stats.getMin()).build());

        }

        for (Map.Entry<String, StatisticInfo> entry : mapStatistics.entrySet()) {
            System.out.println("For " + entry.getKey() + " statistics are: " + entry.getValue());
        }

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
