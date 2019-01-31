package com.gerald.noddus.personcollector.providers;

public class SerializerException extends RuntimeException {

    public SerializerException() {
    }

    public SerializerException(String s) {
        super(s);
    }

    public SerializerException(String s, Throwable throwable) {
        super(s, throwable);
    }
}
