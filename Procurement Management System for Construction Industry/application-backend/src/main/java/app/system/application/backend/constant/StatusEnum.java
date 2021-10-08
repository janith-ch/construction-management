package app.system.application.backend.constant;

public enum StatusEnum {
	
	
	DISPATCHED("DISPATCHED"),
	DELIVERING("DELIVERING"),
	DELIVERED("DELIVERED"),
	PENDING("PENDING");
	
	
	
	private String status;
	
	
	private StatusEnum(String status) {
		this.status = status;
	}
	
	
	public String getStatus() {
		
		return status;
	}
	


}
