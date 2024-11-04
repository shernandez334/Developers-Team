package org.example.entities;

import java.time.LocalDateTime;

public class Reward {

    private int id;

    private String name;

    private String description;

    private String banner;

    private LocalDateTime dateObtained;

    public Reward(int id, String name, String description, String banner, LocalDateTime dateObtained) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.banner = banner;
        this.dateObtained = dateObtained;
    }

    public Reward(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getBanner() {
        return banner;
    }

    public LocalDateTime getDateObtained() {
        return dateObtained;
    }

    @Override
    public boolean equals(Object o){
        return o instanceof Reward && ((Reward) o).getId() == this.id;
    }

    @Override
    public int hashCode(){
        return Integer.hashCode(this.id);
    }

}
