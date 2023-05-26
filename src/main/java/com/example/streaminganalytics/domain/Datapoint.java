package com.example.streaminganalytics.domain;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * The DataPoint object.
 */
@Data
@Builder
public class Datapoint {

    private Long from;

    private Long at;

    private Integer value;

    private List<String> tags;
}
