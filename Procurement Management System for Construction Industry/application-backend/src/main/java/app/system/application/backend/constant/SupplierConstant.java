package app.system.application.backend.constant;

import org.springframework.stereotype.Component;

import lombok.Data;
@Data
@Component
public class SupplierConstant {
	
	final String INSERT_SQL = "insert into suppliers (" +
	           
            "name," +
            "email," +
            "contact_number," +
            "address," +
            "is_active)" +
            "values(?,?,?,?,?)";

	
	final String UPDATE_SQL = " update suppliers set "
			+ "name = ?,"
			+ "email = ?,"
			+ "contact_number = ?,"
			+ "address = ?,"
			+ "is_active = ? "
			+ "where id = ?";
	
	
	final String FIND_ALL = "select * from suppliers";
	
	
	final String FIND_BY_ID = "select * from suppliers where id = ?";
	
	
	
	final String DELETE = "delete from suppliers where id = ?";

}

