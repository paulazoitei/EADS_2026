package org.example.spring_boot_demo;

import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;

@Component
public class DataFileInfoContributor implements InfoContributor {

    private final DataProperties properties;
    private final DataFileReader reader;

    public DataFileInfoContributor(DataProperties properties, DataFileReader reader) {
        this.properties = properties;
        this.reader = reader;
    }

    @Override
    public void contribute(Info.Builder builder) {
        var records = reader.read();

        builder.withDetail("dataFile", properties.getFile())
                .withDetail("dataFormat", properties.getFormat())
                .withDetail("recordCount", records.size());
    }
}