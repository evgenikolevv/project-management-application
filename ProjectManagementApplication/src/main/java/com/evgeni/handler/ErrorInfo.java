package com.evgeni.handler;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

@Data
public class ErrorInfo {

    @JsonProperty
    private final String message;

    @JsonProperty
    private final Date timestamp;

    @JsonProperty("uri")
    private final String uriRequested;

    public ErrorInfo(Exception exception, String uriRequested) {
        this.message = exception.toString();
        this.uriRequested = uriRequested;
        this.timestamp = new Date();
    }
}
