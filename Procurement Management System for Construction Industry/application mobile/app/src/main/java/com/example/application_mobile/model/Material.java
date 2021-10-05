package com.example.application_mobile.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Material {

    private int Id;
    private String name;
    private double unitCost;
    private String unitType;
    private int supplierId;
    private String description;
    private double stock;

    public String toString(){
        return name;
    }
}
