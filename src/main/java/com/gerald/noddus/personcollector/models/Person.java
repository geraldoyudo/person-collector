package com.gerald.noddus.personcollector.models;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.PositiveOrZero;

public class Person {

    @NotEmpty(message = "person name is null or empty")
    private String name;

    @PositiveOrZero(message = "id should be positive")
    private long id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
