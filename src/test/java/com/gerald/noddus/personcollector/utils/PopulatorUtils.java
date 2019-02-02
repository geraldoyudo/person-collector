package com.gerald.noddus.personcollector.utils;

import com.gerald.noddus.personcollector.models.Person;

public final class PopulatorUtils {

    private PopulatorUtils(){
        throw new UnsupportedOperationException("cannot instantiate utility class");
    }

    public static Person createPerson(long id, String name){
        Person p = new Person();
        p.setName(name);
        p.setId(id);
        return p;
    }
}
