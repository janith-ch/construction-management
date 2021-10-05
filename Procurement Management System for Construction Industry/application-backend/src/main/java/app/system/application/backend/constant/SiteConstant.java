package app.system.application.backend.constant;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class SiteConstant {
	
	final String INSERT_SQL = "insert into site (" +
	           
            "name," +
            "address," +
            "contact_number," +
            "site_manager_id," +
            "is_active)" +
            "values(?,?,?,?,?)";
	
	final String UPDATE_SQL = " update site set "
			+ "name = ?,"
			+ "address = ?,"
			+ "contact_number = ?,"
			+ "site_manager_id = ?,"
			+ "is_active = ? "
			+ "where id = ?";
	
	final String FIND_ALL = "select * from site";
		
	final String FIND_BY_ID = "select * from site where id = ?";
	
	final String DELETE = "delete from site where id = ?";
	

}
