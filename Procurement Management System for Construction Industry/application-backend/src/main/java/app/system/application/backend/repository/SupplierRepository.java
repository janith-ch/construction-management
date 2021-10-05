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

import app.system.application.backend.constant.SupplierConstant;
import app.system.application.backend.model.Supplier;
import app.system.application.backend.model.dto.SiteDto;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@Component
public class SupplierRepository {
	
	@Autowired
	SupplierConstant supplierConstant;
	
	@Autowired 
	JdbcTemplate jdbcTemplate;

	public int save(Supplier supplier) {
		KeyHolder keyHolder = new GeneratedKeyHolder();

		int result = jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(supplierConstant.getINSERT_SQL(), new String[] { "id" });                                                                  

				ps.setString(1,supplier.getName());
				ps.setString(2,supplier.getEmail());
				ps.setString(3,supplier.getContactNumber());
				ps.setString(4,supplier.getAddress());
				ps.setBoolean(5,supplier.isActive());

				return ps;
			}
		},keyHolder);


		return keyHolder.getKey().intValue();
	}

	public int update(Supplier supplier, int id) {
		
		try {

			return  jdbcTemplate.update(supplierConstant.getUPDATE_SQL(),
					supplier.getName(),
					supplier.getEmail(),
					supplier.getContactNumber(),
					supplier.getAddress(),
					supplier.isActive(),id);

		}catch (Exception e){
			log.info("update supplier error "+e);
			return 0;

		}
	}

	public List<Supplier> findAll() {
		return jdbcTemplate.query(supplierConstant.getFIND_ALL(),
				(rs, rowNum) ->
		new Supplier(
				rs.getInt("id"),
				rs.getString("name"),
				rs.getString("email"),
				rs.getString("contact_number"),
				rs.getString("address"),
				rs.getBoolean("is_active")
				
				)
				);
	}
	@SuppressWarnings("deprecation")
	public Optional<Supplier> findById (int id) {
		return jdbcTemplate.queryForObject(supplierConstant.getFIND_BY_ID(),
				new Object[]{id},
				(rs, rowNum) ->
		Optional.of(new Supplier(
				rs.getInt("id"),
				rs.getString("name"),
				rs.getString("email"),
				rs.getString("contact_number"),
				rs.getString("address"),
				rs.getBoolean("is_active")
				)
				));

	}

	public int delete(int id) {
		try {
			return jdbcTemplate.update(supplierConstant.getDELETE(),
					id);
		}catch (Exception e) {
			log.info("catch execption in supplier delete " + e);
			return 0;
		}
	}
	
	
	
	
	

}

