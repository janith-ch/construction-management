package com.example.application_mobile.model;

import lombok.Data;

@Data
public class Delivery {
    private int id;
    private int orderId;
    private String deliveryStatus;
    private String refId;
    private String driverName;
    private String vehicleNo;
    private String contactNumber;
    private String estimatedDeliveryDateTime;
    private int materialId;
    private int siteId;
    private String note;

    public String toString(){
        return refId;
    }
}
