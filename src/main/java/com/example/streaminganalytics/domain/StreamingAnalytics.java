package com.example.streaminganalytics.domain;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Data
@Builder
@Document(collection = "StreamingAnalytics")
public class StreamingAnalytics {

    private String dataStreamingId;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date createdAt;

    private double mean;

    private double median;

    private double[] mode;

    private double standardDesviation;

    private List<Double> quartiles;

    private double max;

    private double min;
}
