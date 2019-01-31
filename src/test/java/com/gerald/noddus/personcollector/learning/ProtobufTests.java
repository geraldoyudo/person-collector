package com.gerald.noddus.personcollector.learning;

import com.fasterxml.jackson.dataformat.protobuf.ProtobufMapper;
import com.fasterxml.jackson.dataformat.protobuf.schema.ProtobufSchema;
import com.fasterxml.jackson.dataformat.protobuf.schema.ProtobufSchemaLoader;
import com.gerald.noddus.personcollector.models.Person;
import org.junit.Test;

public class ProtobufTests {
    private ProtobufMapper protobufMapper = new ProtobufMapper();

    @Test
    public void testSerialization() throws Exception{
        String schemaString = "message JSONmsg {\n" + "  required string name = 1;\n"
                + "  required double id = 2;\n" + "}";
        ProtobufSchema schema = ProtobufSchemaLoader.std.parse(schemaString);
        Person person = new Person();
        person.setName("Gerald");
        person.setId(1234);
        String serialized = protobufMapper.writer(schema).writeValueAsString(person);
        System.out.println(new String(serialized));
    }
}
