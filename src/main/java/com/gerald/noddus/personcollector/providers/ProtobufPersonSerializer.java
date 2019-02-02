package com.gerald.noddus.personcollector.providers;

import com.gerald.noddus.entities.PersonEntity;
import com.gerald.noddus.personcollector.models.Person;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import org.springframework.stereotype.Component;

@Component
public class ProtobufPersonSerializer implements PersonSerializer {

    @Override
    public byte[] serialize(Person person) {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            PersonEntity.JSONmsg.newBuilder()
                    .setId(person.getId())
                    .setName(person.getName())
                    .build()
                    .writeDelimitedTo(outputStream);
            return outputStream.toByteArray();
        }catch (IOException ex){
            throw new SerializerException("Could not serialize object", ex);
        }
    }
}
