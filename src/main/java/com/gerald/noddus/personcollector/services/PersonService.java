package com.gerald.noddus.personcollector.services;

import com.gerald.noddus.personcollector.models.Person;
import org.springframework.stereotype.Service;

@Service
public interface PersonService {

    void savePerson(Person person);
}
