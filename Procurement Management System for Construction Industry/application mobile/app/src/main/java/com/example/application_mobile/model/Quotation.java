package com.example.application_mobile.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Quotation {

    private int id;
    private double unitCost;
    private double quantity;
    private String quantityType;
    private int siteId;
    private String departmentStatus;
    private String siteName;
    private String fromDate;
    private String toDate;
    private String materialName;
    private String siteLocation;
    private String address;
    private int isApproved;
    private int orderId;

}
