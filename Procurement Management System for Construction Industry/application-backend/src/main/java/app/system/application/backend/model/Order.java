package app.system.application.backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Order {

    @Id
    private int id;
    private Material material;
    private double quantity;
    private double totalCost;
    private String orderDate;
    private String deliveryDate;
    private int isApprove;
    private Site site; 
    private int quotationStatus;


    public Double calculateTotalCost(int quantity, double unitCost) {
        return quantity * unitCost;
    }
}
