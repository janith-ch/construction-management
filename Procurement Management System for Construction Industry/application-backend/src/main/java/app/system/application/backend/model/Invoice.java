package app.system.application.backend.model;

import lombok.Data;

@Data
public class Invoice {
	
	private int id;
	private Site site;
	private Material material;
	private double quantity;
	private double total;
	private String isApproved;
	private Order order;
	

}
