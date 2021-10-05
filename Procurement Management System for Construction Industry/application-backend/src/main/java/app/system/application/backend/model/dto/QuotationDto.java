package app.system.application.backend.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QuotationDto {
	
	private int id;
	private double unitCost;
	private double quanitity;
	private String date;
	private String validLastDate;
	private double amount;
	private int orderId;
	private int isApproved;

}
