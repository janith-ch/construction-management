package com.example.application_mobile.model;

import lombok.Data;

@Data
public class ReceiveOrder {
    private int id;
    private int materialId;
    private String materialName;
    private double quantity;
    private String quanitiyType;
    private double totalCost;
    private String orderDate;
    private String deliveryDate;
    private int isApprove;
    private int siteId;
    private String siteName;
    private String address;
    private String siteNumber;
    private int siteMangerId;
    private String siteMangerFirstName;
    private String siteManagerLastName;
    private int quotationStatus;
    private String departmentStatus;
}
