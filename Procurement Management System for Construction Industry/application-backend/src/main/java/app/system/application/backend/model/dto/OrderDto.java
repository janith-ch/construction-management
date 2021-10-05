package app.system.application.backend.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {
	
	private int id;
    private int materialId;
    private String materialName;//view
    private int supplierName;//view
    private double quantity;
    private String quanitiyType;//view
    private double totalCost;
    private String orderDate;
    private String deliveryDate;
    private int isApprove;
    private int siteId;
    private String siteName;//view
    private String address;//view
    private String siteNumber;//view
    private int siteMangerId;//view
    private String siteMangerFirstName;//view
    private String siteManagerLastName;//view
    private int quotationStatus;
    private String deliveryStatus;
   
	 
    
    
    public OrderDto(int id, int materialId, double quantity, double totalCost, String orderDate, String deliveryDate,
			int isApprove, int siteId, int quotationStatus, String deliveryStatus) {
		super();
		this.id = id;
		this.materialId = materialId;
		this.quantity = quantity;
		this.totalCost = totalCost;
		this.orderDate = orderDate;
		this.deliveryDate = deliveryDate;
		this.isApprove = isApprove;
		this.siteId = siteId;
		this.quotationStatus = quotationStatus;
		this.deliveryStatus = deliveryStatus;
	}
    
    
    

}



