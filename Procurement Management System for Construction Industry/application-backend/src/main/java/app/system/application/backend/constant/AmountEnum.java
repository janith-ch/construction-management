package app.system.application.backend.constant;



public enum AmountEnum {
	
	ORDERLIMIT(100000);
	
	double limit;
	
	
	AmountEnum(double limit) {
		this.limit = limit;
	}


	public double getLimit() {
		
		return limit;
	}

}
