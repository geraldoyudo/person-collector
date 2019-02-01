package com.gerald.noddus.personcollector.services;

import com.gerald.noddus.personcollector.models.Person;
import com.gerald.noddus.personcollector.providers.DataAppender;
import com.gerald.noddus.personcollector.providers.PersonSerializer;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonServiceImpl implements PersonService {

    private DataAppender appender;
    private PersonSerializer serializer;

    @Autowired
    public void setSerializer(PersonSerializer serializer) {
        this.serializer = serializer;
    }

    @Autowired
    public void setAppender(DataAppender appender) {
        this.appender = appender;
    }

    @Override
    public void savePerson(Person person) {
        try {
            appender.saveData(serializer.serialize(person));
        }catch (IOException ex){
            throw new PersonServiceException("Could not save person object", ex);
        }
    }
}
