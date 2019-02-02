package com.gerald.noddus.personcollector.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@JsonPropertyOrder({"error", "description", "causes", "timestamp"})
public class ErrorDetails {

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private LocalDateTime timestamp;
    private Collection<String> causes;
    private String error;
    private String description;

    public ErrorDetails(String error, String description) {
        this(error, description, new ArrayList<>());
    }

    public ErrorDetails(String error, String description, Collection<String> causes) {
        this.error = error;
        this.description = description;
        this.causes = causes;
        this.timestamp = LocalDateTime.now();
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public Collection<String> getCauses() {
        return causes;
    }

    public void setCauses(List<String> causes) {
        this.causes = causes;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void addCause(String cause) {
        causes.add(cause);
    }
}
