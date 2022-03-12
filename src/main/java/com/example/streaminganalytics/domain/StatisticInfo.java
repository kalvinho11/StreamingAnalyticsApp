package com.example.streaminganalytics.domain;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class StatisticInfo {

    private double mean;

    private double median;

    private double[] mode;

    private double standardDesviation;

    private List<Double> quartiles;

    private double max;

    private double min;
}
