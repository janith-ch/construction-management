package app.system.application.backend.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryNoteDto {
	
	private int id;
	private int orderId;
	private String deliveryStatus;
	private String driverName;
	private String vehicleNo;
	private String contactNumber;
	private String estimatedDeliveryDateTime;
	private String note;


}
