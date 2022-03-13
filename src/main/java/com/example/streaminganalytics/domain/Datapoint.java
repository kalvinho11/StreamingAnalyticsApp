package com.example.streaminganalytics.domain;

import lombok.Data;

import java.util.List;

/**
 * The DataPoint object.
 */
@Data
public class Datapoint {

    private Long from;

    private Long at;

    private Integer value;

    private List<String> tags;
}
