package app.system.application.backend.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import app.system.application.backend.constant.UserConstant;
import app.system.application.backend.model.dto.UserDto;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class UserRepository {

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Autowired
	UserConstant userConstant;

	public int saveUser(UserDto userDto) {

		KeyHolder keyHolder = new GeneratedKeyHolder();

		int result = jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(userConstant.getINSERT_SQL(), new String[] { "id" });                                                                  

				ps.setString(1,userDto.getFirstName());
				ps.setString(2,userDto.getLastName());
				ps.setString(3,userDto.getNic());
				ps.setString(4,userDto.getMobile());
				ps.setString(5,userDto.getEmail());
				ps.setString(6,userDto.getPassword());
				ps.setInt(7,userDto.getRoleId());
				ps.setBoolean(8,userDto.getIsActive());

				return ps;
			}
		},keyHolder);


		return keyHolder.getKey().intValue();
	}

	public int updateUser(UserDto userDto, int id) {
		try {

			return  jdbcTemplate.update(userConstant.getUPDATE_SQL(),
					userDto.getFirstName(),
					userDto.getLastName(),
					userDto.getNic(),
					userDto.getMobile(),
					userDto.getEmail(),
					userDto.getPassword(),
					userDto.getRoleId(),
					userDto.getIsActive(),id);

		}catch (Exception e){
			log.info("update user error "+e);
			return 0;

		}
	}

	public List<UserDto> findAll() {
		return jdbcTemplate.query(userConstant.getFIND_ALL(),
				(rs, rowNum) ->
		new UserDto(
				rs.getInt("id"),
				rs.getString("email"),
				rs.getString("password"),
				rs.getString("first_name"),
				rs.getString("last_name"),
				rs.getString("mobile"),
				rs.getString("nic"),
				rs.getInt("role_id"),
				rs.getBoolean("status"),
				rs.getString("date_created")
				)
				);
	}

	@SuppressWarnings("deprecation")
	public Optional<UserDto> findById(int id) {
		return jdbcTemplate.queryForObject(userConstant.getFIND_USER_BYID(),
				new Object[]{id},
				(rs, rowNum) ->
		Optional.of(new UserDto(
				rs.getInt("id"),
				rs.getString("email"),
				rs.getString("password"),
				rs.getString("first_name"),
				rs.getString("last_name"),
				rs.getString("mobile"),
				rs.getString("nic"),
				rs.getInt("role_id"),
				rs.getBoolean("status"),
				rs.getString("date_created")
				)
				));

	} 

	public int delete(int id) {
		try {
			return jdbcTemplate.update(userConstant.getDELETE_USER(),
					id);
		}catch (Exception e) {
			log.info("catch execption in user delete " + e);
			return 0;
		}

	}

	public List<UserDto> getAllSuppliers() {
		return jdbcTemplate.query(userConstant.getGET_All_SUPPLIERS(),
				(rs, rowNum) ->
		new UserDto(
				rs.getInt("id"),
				rs.getString("email"),
				rs.getString("password"),
				rs.getString("first_name"),
				rs.getString("last_name"),
				rs.getString("mobile"),
				rs.getString("nic"),
				rs.getInt("role_id"),
				rs.getBoolean("status"),
				rs.getString("date_created")
				)
				);
	}	






}
