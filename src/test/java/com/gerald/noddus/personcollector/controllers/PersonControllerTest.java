package com.gerald.noddus.personcollector.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.gerald.noddus.personcollector.models.Person;
import com.gerald.noddus.personcollector.services.PersonService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@WebMvcTest(PersonController.class)
public class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonService personService;

    @Test
    public void givenValidInputWhenSavePersonShouldReturnCreatedResponse() throws Exception {
        this.mockMvc.perform(
                post("/").contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"Gerald\", \"id\": 1234}"))
                .andExpect(status().isCreated());
    }

    @Test
    public void givenEmptyPersonNameWhenSavePersonShouldReturnInvalidInput() throws Exception {
        this.mockMvc
                .perform(post("/").contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"\", \"id\": 1234}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Bad Request"))
                .andExpect(jsonPath("$.description").value("Invalid person object"))
                .andExpect(jsonPath("$.causes[0]").value("person name is null or empty"));
    }

    @Test
    public void givenPersonIdLessThanZeroWhenSavePersonShouldReturnInvalidInput() throws Exception {
        this.mockMvc.perform(
                post("/").contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"Gerald\", \"id\": -1}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Bad Request"))
                .andExpect(jsonPath("$.description").value("Invalid person object"))
                .andExpect(jsonPath("$.causes[0]").value("id should be positive"));
    }

    @Test
    public void givenErrorReturnInternalServerError() throws Exception {
        doThrow(new SampleException("file error")).when(personService).savePerson(any(Person.class));
        this.mockMvc.perform(
                post("/").contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"Gerald\", \"id\": 1234}"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.error").value("Unexpected Error"))
                .andExpect(jsonPath("$.description").value("file error"))
                .andExpect(jsonPath("$.causes.length()").value(0));
    }

    public static class SampleException extends RuntimeException {

        public SampleException(String message) {
            super(message);
        }
    }
}