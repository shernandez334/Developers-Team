package org.example.enums;

public enum ConfigurableProperty {
    PROVIDER ("db.provider"),
    URL ("db.url"),
    USER ("db.user"),
    PASSWORD ("db.password"),
    PLAY_TIME("play.time");

    private final String value;

    ConfigurableProperty(String value){
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
