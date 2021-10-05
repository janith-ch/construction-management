package app.system.application.backend.model;

import lombok.Data;

@Data
public class User {

	private  int id;
	private String email;
	private String password;
	private String firstName;
	private String lastName;
	private String mobile;
	private Role role;
	private String nic;
	private Boolean isActive;
	private String dateCreated;
	
}
