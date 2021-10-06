package app.system.application.backend.constant;

public enum DeliveryEnum {
	
	DISPATCHED("DISPATCHED"),
	DELIVERING("DELIVERING"),
	DELIVERED("DELIVERED"),
	PENDING("PENDING");
	
	
	
	private String status;
	
	
	private DeliveryEnum(String status) {
		this.status = status;
	}
	
	
	public String getStatus() {
		
		return status;
	}
	

}
