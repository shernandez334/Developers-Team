package org.example.enums;

public enum SystemProperty {

    PROPERTIES_FILE_PATH("properties.properties"),
    DEFAULT_PROVIDER("MySQL"),
    DEFAULT_DB_URL("jdbc:mysql://localhost:3306/"),
    DEFAULT_DB_USER("root"),
    DEFAULT_DB_PASSWORD("1234"),
    DEFAULT_PLAY_TIME("4"),
    SQL_SCHEMA_CREATION_FILE("diagrams\\mySQL\\escape_room.sql"),
    DB_NAME("escape_room"),
    SCRIPT_MODE(ScriptMode.SILENT.name());

    private final String value;

    SystemProperty(String s) {
            this.value = s;
        }

    public String getValue(){
            return this.value;
        }

}
