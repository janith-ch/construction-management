package com.example.application_mobile.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    private int id;
    private int materialId;
    private String materialName;//view
    private double quantity;
    private String quanitiyType;//view
    private double totalCost;
    private String orderDate;
    private String deliveryDate;
    private int isApprove;
    private int siteId;
    private String siteName;//view
    private String address;//view
    private String siteNumber;//view
    private int siteMangerId;//view
    private String siteMangerFirstName;//view
    private String siteManagerLastName;//view
    private int quotationStatus;
    private String departmentStatus;


}
