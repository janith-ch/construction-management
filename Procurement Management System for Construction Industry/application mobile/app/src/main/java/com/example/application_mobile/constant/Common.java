package com.example.application_mobile.constant;

import lombok.Data;

@Data
public class Common {

    private  String URL = "http://192.168.1.5:8088/api/v1/";
    private  String QUOTATION_LIST_ENDPOINT = "quotations/orderList";
    private  String CREATE_ORDER_ENDPOINT = "orders";
    private  String ORDER_LIST_ENDPOINT = "orders";
    private  String CREATE_QUOTATION_ENDPOINT= "quotations";
    private  String CREATE_INVOICE_ENDPOINT = "invoices";
    private  String RECEIVE_ODER_ENDPOINT = "delivery-notes/received/orders";
    private  String CREATE_DELIVERY_ENDPOINT = "delivery-notes";
    private  String DELIVERY_LIST_ENDPOINT = "delivery-notes";
    private  String INVOICE_LIST_ENDPOINT = "invoices";
    private  String ORDER_REF_DELIVERING = "orders/delivering";
    private  String SITE_LIST_ENDPOINT = "sites";
    private  String MATERIAL_LIST_ENDPOINT = "materials";
    private  String JSON_PREFIX = "dataBundle";
    private  String APPROVED = "APPROVED";
    private  String PENDING = "PENDING";
    private  String REJECTED = "REJECTED";
    private  String IS_SUCCESS = "isSuccess";
    private  String RS = "RS.";
    private  String POINTS = ".00";
    private  String OR = "OR00";
    private  String INV = "INV00";
    private  int ONE = 1;
    private  int TWO = 2;
    private  int ZERO = 0;

}
