package com.gerald.noddus.personcollector.providers;

import static com.gerald.noddus.personcollector.utils.PopulatorUtils.createPerson;
import static org.hamcrest.CoreMatchers.equalTo;

import com.gerald.noddus.entities.PersonEntity;
import com.gerald.noddus.entities.PersonEntity.JSONmsg;
import com.gerald.noddus.personcollector.models.Person;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;

public class ProtobufPersonSerializerTest {
    private final ProtobufPersonSerializer serializer = new ProtobufPersonSerializer();

    @Rule
    public final ErrorCollector collector = new ErrorCollector();

    @Test
    public void givenPersonShouldSerializeProperly() throws Exception {
        Person person = createPerson(100L, "Gerald");
        byte[] serializedPerson = serializer.serialize(person);
        Person readPerson = readPerson(serializedPerson);
        collector.checkThat(readPerson.getId(), equalTo(person.getId()));
        collector.checkThat(readPerson.getName(), equalTo(person.getName()));
    }

    private Person readPerson(byte[] serializedPerson) throws IOException{
        JSONmsg personEntity = PersonEntity.JSONmsg.parseDelimitedFrom(new ByteArrayInputStream(serializedPerson));
        return createPerson(personEntity.getId(), personEntity.getName());
    }
}