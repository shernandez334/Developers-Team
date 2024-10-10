package org.example.enums;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class Properties {

    public static String getProperty(String property){
        Path path = Path.of(DefaultProperties.PROPERTIES_FILE_PATH.getValue());

        try (InputStream input = Files.newInputStream(path)){
            java.util.Properties properties = new java.util.Properties();
            properties.load(input);
            return properties.getProperty(property);
        } catch (IOException e) {
            System.out.println("Fatal error: couldn't read the properties.properties file.");
            throw new RuntimeException(e);
        }
    }

}
