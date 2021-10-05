package app.system.application.backend.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDto {

	
	private int id;
	private int invoiceId;
	private double amount;
	private String paymentMethod;
	private int userId;
	private String paymentDate;
}
