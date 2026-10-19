package org.example.spring_boot_demo;

import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Profile("prod")
@ConditionalOnExpression("'${app.data.format:json}' == 'json' && '${app.data.enabled:true}' == 'true'")
public class JsonDataFileReader implements DataFileReader {

    private final DataProperties properties;
    private final ResourceLoader resourceLoader;
    private final ObjectMapper objectMapper;

    public JsonDataFileReader(
            DataProperties properties,
            ResourceLoader resourceLoader,
            ObjectMapper objectMapper
    ) {
        this.properties = properties;
        this.resourceLoader = resourceLoader;
        this.objectMapper = objectMapper;
    }

    @Override
    public List<Person> read() {
        try {
            Resource resource = resourceLoader.getResource(properties.getFile());

            try (var inputStream = resource.getInputStream()) {
                return objectMapper.readValue(inputStream, new TypeReference<List<Person>>() {});
            }
        } catch (Exception e) {
            throw new IllegalStateException("Nu am putut citi fisierul JSON: " + properties.getFile(), e);
        }
    }
}