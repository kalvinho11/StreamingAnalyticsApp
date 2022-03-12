package com.example.streaminganalytics.domain;

import lombok.Data;

import java.util.List;

/**
 * The DataStream object.
 */
@Data
public class DataStream {

    private String id;

    private String feed;

    private List<Datapoint> datapoints;
}
