package com.example.streaminganalytics.infrastructure.configuration;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.streaminganalytics.domain.Datapoint;
import com.example.streaminganalytics.domain.StreamingAnalytics;
import com.example.streaminganalytics.domain.repository.StreamingAnalyticsRepository;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@DataMongoTest(excludeAutoConfiguration = EmbeddedMongoAutoConfiguration.class)
public class StreamingAnalyticsRepositoryTests {

    @Autowired
    private StreamingAnalyticsRepository streamingAnalyticsRepository;

    @AfterEach
    void cleanUpDatabase() {
        streamingAnalyticsRepository.deleteAll();
    }

    @Test
    void saveStreamingAnalytics() {
        final List<Datapoint> datapoints = getListOfDatapoints();
        final DescriptiveStatistics stats = new DescriptiveStatistics();

        datapoints.forEach(datapoint -> stats.addValue(datapoint.getValue()));

        final StreamingAnalytics streamingAnalyticsToSave = StreamingAnalytics.builder().dataStreamingId(
                UUID.randomUUID().toString()).min(10).max(45).mode(stats.getValues()).median(stats.getPercentile(50))
                .quartiles(getQuartiles(stats)).standardDeviation(stats.getStandardDeviation()).build();

        final StreamingAnalytics savedStreamingAnalytics = streamingAnalyticsRepository.save(streamingAnalyticsToSave);

        assertThat(savedStreamingAnalytics).isNotNull();
        assertThat(savedStreamingAnalytics).isEqualTo(streamingAnalyticsToSave);
    }

    private List<Datapoint> getListOfDatapoints() {

        final Datapoint datapoint1 = Datapoint.builder().at(1431602523123L).value(60).build();
        final Datapoint datapoint2 = Datapoint.builder().at(1431602523134L).value(12).build();
        final Datapoint datapoint3 = Datapoint.builder().at(1431602523150L).value(40).build();

        return Arrays.asList(datapoint1, datapoint2, datapoint3);
    }

    private List<Double> getQuartiles(final DescriptiveStatistics stats) {

        final Double quartile1 = stats.getPercentile(25);
        final Double quartile2 = stats.getPercentile(50);
        final Double quartile3 = stats.getPercentile(75);

        return Arrays.asList(quartile1, quartile2, quartile3);
    }
}
