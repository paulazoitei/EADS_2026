package org.example.spring_boot_demo;

import org.springframework.context.annotation.Profile;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Component
@Profile("dev")
@ConditionalOnExpression("'${app.data.format:csv}' == 'csv' && '${app.data.enabled:true}' == 'true'")
public class CsvDataFileReader implements DataFileReader {

    private final DataProperties properties;
    private final ResourceLoader resourceLoader;

    public CsvDataFileReader(DataProperties properties, ResourceLoader resourceLoader) {
        this.properties = properties;
        this.resourceLoader = resourceLoader;
    }

    @Override
    public List<Person> read() {
        try {
            Resource resource = resourceLoader.getResource(properties.getFile());

            try (var reader = new BufferedReader(
                    new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8))) {

                return reader.lines()
                        .skip(1)
                        .map(line -> line.split(",", 2))
                        .filter(parts -> parts.length == 2)
                        .map(parts -> new Person(parts[0].trim(), parts[1].trim()))
                        .toList();
            }
        } catch (Exception e) {
            throw new IllegalStateException("Nu am putut citi fisierul CSV: " + properties.getFile(), e);
        }
    }
}