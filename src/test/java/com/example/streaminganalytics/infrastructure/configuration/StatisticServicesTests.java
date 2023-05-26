package com.example.streaminganalytics.infrastructure.configuration;

import com.example.streaminganalytics.application.service.StatisticsService;
import com.example.streaminganalytics.domain.DataInput;
import com.example.streaminganalytics.domain.DataStream;
import com.example.streaminganalytics.domain.Datapoint;
import com.example.streaminganalytics.domain.StreamingAnalytics;
import org.apache.commons.math3.stat.StatUtils;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class StatisticServicesTests {

    @Autowired
    private StatisticsService statisticsService;

    @Test
    void shouldCalculateStatistic() {
        DataInput receivedDataInput = generateDataInput();
        List<StreamingAnalytics> expectedStreamingAnalytics = generateExpectedAnalytics();

        List<StreamingAnalytics> obtainedStreamingAnalytics = statisticsService.calculateAndSaveAnalytics(
                receivedDataInput);

        assertThat(obtainedStreamingAnalytics).isNotNull();
        assertThat(obtainedStreamingAnalytics.get(0)).usingRecursiveComparison().ignoringFields("createdAt")
                .isEqualTo(expectedStreamingAnalytics.get(0));
        assertThat(obtainedStreamingAnalytics.get(1)).usingRecursiveComparison().ignoringFields("createdAt")
                .isEqualTo(expectedStreamingAnalytics.get(1));

    }


    private DataInput generateDataInput() {
        DataStream dataStream1 = DataStream.builder().id("example_id_001").feed("feed1").datapoints(
                generateDataPointsForDataStream1()).build();
        DataStream dataStream2 = DataStream.builder().id("example_id_002").feed("feed2").datapoints(
                generateDataPointsForDataStream2()).build();

        return DataInput.builder().version("1.0.0").device("organization_1_device_001").datastreams(Arrays.asList(
                dataStream1, dataStream2)).build();
    }

    private List<Datapoint> generateDataPointsForDataStream1() {

        final Datapoint datapoint1 = Datapoint.builder().at(1431602523123L).value(60).build();
        final Datapoint datapoint2 = Datapoint.builder().at(1431602523134L).value(12).build();
        final Datapoint datapoint3 = Datapoint.builder().at(1431602523150L).value(40).build();

        return Arrays.asList(datapoint1, datapoint2, datapoint3);
    }

    private List<Datapoint> generateDataPointsForDataStream2() {
        final Datapoint datapoint1 = Datapoint.builder().at(1431602523123L).value(30).build();
        final Datapoint datapoint2 = Datapoint.builder().at(1431602523134L).value(232).build();
        final Datapoint datapoint3 = Datapoint.builder().at(1431602523150L).value(10).build();

        return Arrays.asList(datapoint1, datapoint2, datapoint3);
    }


    private List<StreamingAnalytics> generateExpectedAnalytics() {

        StreamingAnalytics streamingAnalytics1 = StreamingAnalytics.builder().dataStreamingId("example_id_001").max(60)
                .min(12).median(40).mode(calculateMode(Arrays.asList(60, 12, 40))).quartiles(calculateQuartiles(Arrays
                        .asList(60, 12, 40))).mean(37.333333333333336).standardDeviation(24.110855093366833).build();

        StreamingAnalytics streamingAnalytics2 = StreamingAnalytics.builder().dataStreamingId("example_id_002").max(232)
                .min(10).median(30).mode(calculateMode(Arrays.asList(30, 232, 10))).quartiles(calculateQuartiles(Arrays
                        .asList(30, 232, 10))).mean(90.66666666666666).standardDeviation(122.80608019692401).build();

        return Arrays.asList(streamingAnalytics1, streamingAnalytics2);
    }

    private List<Double> calculateQuartiles(List<Integer> datapointValues) {
        DescriptiveStatistics descriptiveStatistics = new DescriptiveStatistics();
        datapointValues.forEach(descriptiveStatistics::addValue);

        return Arrays.asList(descriptiveStatistics.getPercentile(25), descriptiveStatistics.getPercentile(50),
                descriptiveStatistics.getPercentile(75));
    }

    private double[] calculateMode(List<Integer> datapointValues) {
        DescriptiveStatistics descriptiveStatistics = new DescriptiveStatistics();
        datapointValues.forEach(descriptiveStatistics::addValue);

        return StatUtils.mode(descriptiveStatistics.getValues());
    }
}
