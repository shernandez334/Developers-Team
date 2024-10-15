package org.example.enums;

public enum FileProps {
    PROVIDER ("db.provider"),
    URL ("db.url"),
    USER ("db.user"),
    PASSWORD ("db.password");

    private final String value;

    FileProps(String value){
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
