package com.example.application_mobile.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Invoice {

    private String id;
    private String pur;
    private String material;
    private String quantity;
    private String quantityType;
    private String createdDate;
    private String site;
    private String location;
    private String totalPrice;
}
