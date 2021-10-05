package app.system.application.backend.constant;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class MaterialConstant {
	
	final String INSERT_SQL = "insert into material (" +
	           
            "name," +
            "unit_cost," +
            "unit_type," +
            "supplier_id," +
            "description," +
            "stock)" +
            "values(?,?,?,?,?,?)";

	
	final String UPDATE_SQL = " update material set "
			+ "name = ?,"
			+ "unit_cost = ?,"
			+ "unit_type = ?,"
			+ "supplier_id = ?,"
			+ "description = ?, "
			+ "stock = ? "
			+ "where id = ?";
	
	
	final String FIND_ALL = "select * from material";
	
	final String FIND_BY_ID = "select * from material where id = ?";
	
	final String DELETE = "delete from material where id = ?";
	
	final String GET_UNIT_PRICE = "select unit_cost,stock from material where id = ? ";
	
	final String UPDATE_STOCK = "update material set stock = ? where id = ?";
}
