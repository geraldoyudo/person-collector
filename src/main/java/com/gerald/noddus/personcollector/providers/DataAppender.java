package com.gerald.noddus.personcollector.providers;

import java.io.IOException;

public interface DataAppender {

    void saveData(byte[] data) throws IOException;
}
