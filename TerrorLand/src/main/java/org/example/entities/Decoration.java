package org.example.entities;

import org.example.enums.Material;

public class Decoration {
    private final int element_id;
    private final Material material;


    public Decoration (int element_id, Material material){
        this.element_id = element_id;
        this.material = material;
    }

    public int getElementId(){
        return this.element_id;
    }
    public Material getMaterial(){
        return this.material;
    }
}