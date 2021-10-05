package app.system.application.backend.constant;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class PaymentConstant {
	
	final String INSERT_SQL = "insert into payment (" +
	           
            "invoice_number," +
            "amount," +
            "payment_method," +
            "accountant_id," +
            "payment_date)" +
            "values(?,?,?,?,?)";
	

	
	final String UPDATE_SQL = " update payment set "
			+ "invoice_number = ?,"
			+ "amount = ?,"
			+ "payment_method = ?,"
			+ "accountant_id = ?,"
			+ "payment_date = ? "
			+ "where id = ?";
	
	
	final String FIND_ALL = "select * from payment";
	
	
	final String FIND_BY_ID = "select * from payment where id = ?";
	
	
	
	final String DELETE = "delete from payment where id = ?";


	

}
