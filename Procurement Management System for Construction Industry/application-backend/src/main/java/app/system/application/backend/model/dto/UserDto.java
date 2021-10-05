package app.system.application.backend.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
	
	private  int id;
	private String email;
	private String password;
	private String firstName;
	private String lastName;
	private String mobile;
	private String nic;
	private int roleId;
	private Boolean isActive;
	private String dateCreated;


}

