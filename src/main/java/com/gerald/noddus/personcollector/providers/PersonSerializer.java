package com.gerald.noddus.personcollector.providers;

import com.gerald.noddus.personcollector.models.Person;

public interface PersonSerializer {

    byte[] serialize(Person person);
}
