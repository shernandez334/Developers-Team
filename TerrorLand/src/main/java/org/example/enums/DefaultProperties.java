package org.example.enums;

public enum DefaultProperties {

    PROPERTIES_FILE_PATH("properties.properties"),
    DEFAULT_PROVIDER("MySQL"),
    DEFAULT_DB_URL("jdbc:mysql://localhost:3306/"),
    DEFAULT_DB_USER("root"),
    DEFAULT_DB_PASSWORD("1234"),
    SQL_SCHEMA_CREATION_FILE("diagrams\\mySQL\\escape_room.sql"),
    DB_NAME("escape_room");

    private final String value;

    DefaultProperties(String s) {
            this.value = s;
        }

    public String getValue(){
            return this.value;
        }

}
