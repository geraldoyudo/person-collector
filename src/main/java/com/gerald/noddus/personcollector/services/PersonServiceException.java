package com.gerald.noddus.personcollector.services;

public class PersonServiceException extends RuntimeException {

    public PersonServiceException() {
    }

    public PersonServiceException(String s) {
        super(s);
    }

    public PersonServiceException(String s, Throwable throwable) {
        super(s, throwable);
    }
}
