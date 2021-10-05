package app.system.application.backend.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Supplier {
	
	private int id;
	private String name;
	private String email;
	private String contactNumber;
	private String address;
	private boolean isActive;

}