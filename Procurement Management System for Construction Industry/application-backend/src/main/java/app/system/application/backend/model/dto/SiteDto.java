package app.system.application.backend.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SiteDto {
	
	private int id;
	private String name;
	private String address;
	private int isActive;
	private String contactNumber;
	private int siteManagerId;
	

}
