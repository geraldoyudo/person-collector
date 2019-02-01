package com.gerald.noddus.personcollector.providers;

public class RolloverException extends RuntimeException {

    public RolloverException() {
    }

    public RolloverException(String s) {
        super(s);
    }

    public RolloverException(String s, Throwable throwable) {
        super(s, throwable);
    }
}
