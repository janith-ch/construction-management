package app.system.application.backend.constant;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class UserConstant {
	
	final String INSERT_SQL = "insert into user (" +
           
            "first_name," +
            "last_name," +
            "nic," +
            "mobile," +
            "email," +
            "password," +
            "role_id," +
            "status)" +
            "values(?,?,?,?,?,?,?,?)";
	
	
	final String UPDATE_SQL = " update user set "
			+ "first_name = ?,"
			+ "last_name = ?,"
			+ "nic = ?,"
			+ "mobile = ?,"
			+ "email = ?,"
			+ "password = ?,"
			+ "role_id = ?,"
			+ "status = ? "
			+ "where id = ?";
	
	
	final String FIND_ALL = "select * from user";
	
	
	final String FIND_USER_BYID = "select * from user where id = ?";
	
	
	
	final String DELETE_USER = "delete from user where id = ?";
	
	final String GET_All_SUPPLIERS = "select * from user where role_id = 3";
	

}
