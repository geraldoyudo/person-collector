package com.gerald.noddus.personcollector.providers;

public interface RolloverCapable {

    void rollOver(RolloverLabel rolloverLabel) throws RolloverException;
}
