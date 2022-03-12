package com.example.streaminganalytics.domain;

import lombok.Data;

import java.util.List;

/**
 * The input object.
 */
@Data
public class DataInput {

    private String version;

    private String device;

    private String path;

    private String trustedBoot;

    private List<DataStream> datastreams;

}
