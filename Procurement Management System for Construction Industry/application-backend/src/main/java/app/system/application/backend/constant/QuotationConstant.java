package app.system.application.backend.constant;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class QuotationConstant {
	
	final String INSERT_SQL = "insert into quotations (" +
	           
            "unit_cost," +
            "quantity," +
            "qutation_date," +
            "valid_last_date," +
            "amount," +
            "order_id," +
            "is_approved)" +
            "values(?,?,?,?,?,?,?)";

	
	final String UPDATE_SQL = " update quotations set "
			+ "unit_cost = ?,"
			+ "quantity = ?,"
			+ "qutation_date = ?,"
			+ "valid_last_date = ?,"
			+ "amount = ?, "
			+ "order_id = ?, "
			+ "is_approved = ? "
			+ "where id = ?";
	
	
	final String FIND_ALL = "select * from quotations";
	
	
	final String FIND_BY_ID = "select * from quotations where id = ?";
	
	final String FIND_BY_ORDER_ID = "select * from quotations where order_id = ?";
	
	
	
	final String DELETE = "delete from quotations where id = ?";
	
	final String UPDATE_STATUS = "update quotations set is_approved = ? where id = ?";
	


}
