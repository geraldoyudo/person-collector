package com.gerald.noddus.personcollector.providers;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import com.fasterxml.jackson.dataformat.protobuf.ProtobufMapper;
import com.fasterxml.jackson.dataformat.protobuf.schema.ProtobufSchema;
import com.fasterxml.jackson.dataformat.protobuf.schema.ProtobufSchemaLoader;
import com.gerald.noddus.personcollector.models.Person;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;

public class GoogleProtobufSerializerTest {

    private GoogleProtobufSerializer serializer = new GoogleProtobufSerializer();
    private ProtobufMapper protobufMapper = new ProtobufMapper();

    @Rule
    public final ErrorCollector collector = new ErrorCollector();

    @Test
    public void givenPersonObjectWhenSerializeShouldReturnCorrectString() throws Exception {
        Person person = new Person();
        person.setId(1234);
        person.setName("Gerald");
        byte[] serializedPerson = serializer.serialize(person);
        ProtobufSchema schema = ProtobufSchemaLoader.std.parse(GoogleProtobufSerializer.SCHEMA);
        Person readPerson = protobufMapper.readerFor(Person.class).with(schema).readValue(serializedPerson);
        collector.checkThat(readPerson.getId(), equalTo(person.getId()));
        collector.checkThat(readPerson.getName(), equalTo(person.getName()));
    }
}