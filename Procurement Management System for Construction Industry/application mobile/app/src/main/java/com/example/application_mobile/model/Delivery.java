package com.example.application_mobile.model;

import lombok.Data;

@Data
public class Delivery {
    private int id;
    private int orderId;
    private String deliveryStatus;
    private String driverName;
    private String vehicleNo;
    private String contactNumber;
    private String estimatedDeliveryDateTime;
    private String note;
}
