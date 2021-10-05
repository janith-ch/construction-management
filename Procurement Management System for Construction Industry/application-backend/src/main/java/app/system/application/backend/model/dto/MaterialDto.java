package app.system.application.backend.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MaterialDto {

	private int Id;
	private String name;
	private double unitCost;
	private String unitType;
	private int supplierId;
	private String description;
	private double stock;


}
