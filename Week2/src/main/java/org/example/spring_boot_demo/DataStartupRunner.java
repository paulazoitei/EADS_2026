package org.example.spring_boot_demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
public class DataStartupRunner implements CommandLineRunner {

    private final DataFileReader reader;
    private final DataProperties properties;

    public DataStartupRunner(DataFileReader reader, DataProperties properties) {
        this.reader = reader;
        this.properties = properties;
    }

    @Override
    public void run(String... args) {
        var people = reader.read();

        System.out.println("Fisier: " + properties.getFile());
        System.out.println("Format: " + properties.getFormat());
        System.out.println("Numar inregistrari: " + people.size());

        people.stream().limit(3).forEach(person ->
                System.out.println(person.name() + " — " + person.city()));
    }
}