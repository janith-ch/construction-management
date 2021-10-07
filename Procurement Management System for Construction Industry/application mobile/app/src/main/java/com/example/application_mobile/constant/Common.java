package com.example.application_mobile.constant;

import lombok.Data;

@Data
public class Common {

    private  String URL = "http://192.168.1.5:8088/api/v1/";
    private  String QUOTATION_LIST_ENDPOINT = "quotations/orderList";
    private  String CREATE_ORDER_ENDPOINT = "orders";
    private  String CREATE_QUOTATION_ENDPOINT= "quotations";
    private  String RECEIVE_ODER_ENDPOINT = "delivery-notes/received/orders";
    private  String CREATE_DELIVERY_ENDPOINT = "delivery-notes";
    private  String DELIVERY_LIST_ENDPOINT = "delivery-notes";
    private  String INVOICE_LIST_ENDPOINT = "invoices";
    private  String ORDER_REF_DELIVERING = "orders/delivering";

}
