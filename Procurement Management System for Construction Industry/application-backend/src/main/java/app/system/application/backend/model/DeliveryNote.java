package app.system.application.backend.model;

import lombok.Data;

@Data
public class DeliveryNote {
	
	private int id;
	private Order order;
	private String deliveryStatus;
	private String driverName;
	private String vehicleNo;
	private String contactNumber;
	private String estimatedDeliveryDateTime;
	private String note;

}
