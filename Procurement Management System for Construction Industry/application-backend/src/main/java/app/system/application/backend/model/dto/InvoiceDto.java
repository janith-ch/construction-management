package app.system.application.backend.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceDto {
	
	private int id;
	private int siteId;
	private String siteName;//view
	private String materialName;//view
    private String supplierName;//view
    private String unitType ;
    private String address; 
	private int materialId;
	private double quantity;
	private double total;
	private int isApproved;
	private int orderId;

	
}
