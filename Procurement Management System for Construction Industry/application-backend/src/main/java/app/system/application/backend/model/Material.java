package app.system.application.backend.model;

import lombok.Data;

@Data
public class Material {

    
    private int Id;
    private String name;
    private double unitCost;
    private String unitType;
    private Supplier supplier;
    private String description;
    private double stock;

}
