package com.example.streaminganalytics.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document(collection = "Organizations")
@NoArgsConstructor
@AllArgsConstructor
public class Organization {

    @Transient
    public static final String SEQUENCE_NAME = "organizations_sequence";

    @Id
    private Long organizationId;

    private String organizationName;

    private String organizationCountryISO;

    private Boolean active;
}
