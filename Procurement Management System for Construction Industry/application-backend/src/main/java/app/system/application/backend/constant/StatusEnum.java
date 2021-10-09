package app.system.application.backend.constant;

public enum StatusEnum {
	
	
	APPROVED(1),
	PENDING(2),
	REJECT(0),
	APPROVEDQUOATATION(3);
	
	
	
	private int status;
	
	
	private StatusEnum(int status) {
		this.status = status;
	}
	
	
	public int getStatus() {
		
		return status;
	}
	


}
