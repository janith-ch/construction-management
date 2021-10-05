package app.system.application.backend.model;

import lombok.Data;

@Data
public class Site {
	
	private int id;
	private String name;
	private String address;
	private int isActive;
	private String contactNumber;
	private User user;
	

}
