package app.system.application.backend.model;

import lombok.Data;

@Data
public class Quotation {
	
	private int id;
	private double unitCost;
	private double quanitity;
	private String date;
	private String validLastDate;
	private double amount;
	private Order order;

}
