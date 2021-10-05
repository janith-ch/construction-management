package app.system.application.backend.constant;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class DeliveryNoteConstant {
	
	
	final String INSERT_SQL = "insert into delivery_notes (" +
	           
            "order_id," +
            "delivery_status," +
            "driver_name," +
            "vehicle_no," +
            "contact_number," +
            "estimated_delivery_date_time,"
            + "note)" +
            "values(?,?,?,?,?,?,?)";
	
	final String UPDATE_SQL = " update delivery_notes set "
			+ "order_id = ?,"
			+ "delivery_status = ?,"
			+ "driver_name = ?,"
			+ "vehicle_no = ?,"
			+ "contact_number = ?, "
			+ "estimated_delivery_date_time = ?, "
			+ "note = ? "
			+ " where id = ?";
	
	
	final String FIND_ALL = "select * from delivery_notes";
	
	
	final String FIND_BY_ID = "select * from delivery_notes where id = ?";
	
	
	
	final String DELETE = "delete from delivery_notes where id = ?";
	
	final String DELIVERY_REJECTION = "update delivery_notes set delivery_status = ? where id = ? ";
	
	final String UPDATE_STOCK = "update material set stock = ? where id = ?";
	

}
