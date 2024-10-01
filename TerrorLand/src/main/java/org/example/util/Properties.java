package org.example.util;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public enum Properties {

    PROPERTIES_FILE_PATH("dbconfig.properties"),
    DEFAULT_DB_URL("jdbc:mysql://localhost:3306/"),
    DEFAULT_DB_USER("root"),
    DEFAULT_DB_PASSWORD("1234"),
    SQL_SCHEMA_CREATION_FILE("diagrams\\mySQL\\scape-room.sql"),
    DB_NAME("escape_room");

    private final String value;

    Properties(String s) {
            this.value = s;
        }
        public String getValue(){
            return this.value;
        }
        public static void createFileIfMissing(){
        final Path path = Path.of(Properties.PROPERTIES_FILE_PATH.getValue());
        if (!Files.exists(path)) {
            try {
                String defaultInfo = "db.url=" + Properties.DEFAULT_DB_URL.getValue() +
                        "\ndb.user=" + Properties.DEFAULT_DB_USER.getValue() +
                        "\ndb.password=" + Properties.DEFAULT_DB_PASSWORD.getValue();
                Files.createFile(path);
                Files.writeString(path, defaultInfo);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static String getProperty(String property){
        Path path = Path.of(Properties.PROPERTIES_FILE_PATH.getValue());

        try (InputStream input = Files.newInputStream(path)){
            java.util.Properties properties = new java.util.Properties();
            properties.load(input);
            return properties.getProperty(property);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
