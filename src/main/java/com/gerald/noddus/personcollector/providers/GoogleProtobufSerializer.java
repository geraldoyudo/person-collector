package com.gerald.noddus.personcollector.providers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.protobuf.ProtobufMapper;
import com.fasterxml.jackson.dataformat.protobuf.schema.ProtobufSchema;
import com.fasterxml.jackson.dataformat.protobuf.schema.ProtobufSchemaLoader;
import java.io.IOException;

public class GoogleProtobufSerializer implements DataSerializer {

    public static final String SCHEMA = "message JSONmsg {\n"
            + "  required string name = 1;\n"
            + "  required int64 id = 2;\n"
            + "}";

    private ProtobufSchema schema;
    private ProtobufMapper protobufMapper = new ProtobufMapper();

    public GoogleProtobufSerializer() {
        try {
            schema = ProtobufSchemaLoader.std.parse(SCHEMA);
        }catch (IOException ex){
            throw new SerializerException("Could not create serializer", ex);
        }
    }

    @Override
    public byte[] serialize(Object object) {
        try {
            return protobufMapper.writer(schema).writeValueAsBytes(object);
        }catch (JsonProcessingException ex){
            throw new SerializerException("Could not serialize object", ex);
        }
    }
}
