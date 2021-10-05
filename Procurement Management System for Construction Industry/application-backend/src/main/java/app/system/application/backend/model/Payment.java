package app.system.application.backend.model;

import lombok.Data;

@Data
public class Payment {
	
	private int id;
	private Invoice invoice;
	private double amount;
	private String paymentMethod;
	private User user;
	private String paymentDate;

}
