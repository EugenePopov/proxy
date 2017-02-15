package org.utm.pad.business_layer;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.ValidationMessage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class JsonValidator {

    private JsonSchema getJsonSchemaFromStringContent(String schemaContent) throws Exception {
        JsonSchemaFactory factory = new JsonSchemaFactory();
        return factory.getSchema(schemaContent);
    }

    private JsonNode getJsonNodeFromStringContent(String content) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readTree(content);
    }


    boolean isValid(String json) throws Exception {

        String fileName = "E:\\UTM\\Anul_IV\\PAD\\Labs\\lab6\\schema.json";
        String jsonSchema = "";

        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {

            jsonSchema = stream.collect(Collectors.joining());

        } catch (IOException e) {
            e.printStackTrace();
        }

        JsonSchema schema =getJsonSchemaFromStringContent(jsonSchema);

        Set<ValidationMessage> errors = schema.validate(getJsonNodeFromStringContent(json));

        return errors.isEmpty();
    }
}
