package com.gerald.noddus.personcollector.models;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import java.time.LocalDateTime;
import java.util.Collections;
import org.junit.Test;

public class ErrorDetailsTest {

    public static final String DESIRED_ERROR_JSON = "{\"error\":\"Validation error\","
            + "\"description\":\"invalid person entry\",\"causes\":[\"name is invalid\"],"
            + "\"timestamp\":\"2019-01-31T14:23:46.085\"}";

    private ObjectMapper objectMapper = new ObjectMapper().registerModule(new ParameterNamesModule())
            .registerModule(new Jdk8Module()).registerModule(new JavaTimeModule());

    @Test
    public void testEventDetailsSerialization() throws Exception {
        ErrorDetails errorDetails = new ErrorDetails("Validation error", "invalid person entry",
                Collections.singleton("name is invalid"));
        errorDetails.setTimestamp(LocalDateTime.parse("2019-01-31T14:23:46.085"));
        assertThat(objectMapper.writeValueAsString(errorDetails), equalTo(DESIRED_ERROR_JSON));
    }
}