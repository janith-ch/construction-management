package app.system.application.backend.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import app.system.application.backend.constant.MaterialConstant;
import app.system.application.backend.model.dto.MaterialDto;
import app.system.application.backend.model.dto.StockPriceDto;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@Component
public class MaterialRepository {
	
	@Autowired
	MaterialConstant materialConstant;
	
	@Autowired
	@Qualifier("system-jdbc-template")
	JdbcTemplate jdbcTemplate;


	public int save(MaterialDto materialDto) {
		KeyHolder keyHolder = new GeneratedKeyHolder();

		int result = jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(materialConstant.getINSERT_SQL(), new String[] { "id" });                                                                  

				ps.setString(1,materialDto.getName());
				ps.setDouble(2,materialDto.getUnitCost());
				ps.setString(3,materialDto.getUnitType());
				ps.setInt(4,materialDto.getSupplierId());
				ps.setString(5,materialDto.getDescription());
				ps.setDouble(6,materialDto.getStock());

				return ps;
			}
		},keyHolder);


		return keyHolder.getKey().intValue();
	}


	public int update(MaterialDto materialDto, int id) {
		try {

			return  jdbcTemplate.update(materialConstant.getUPDATE_SQL(),
					materialDto.getName(),
					materialDto.getUnitCost(),
					materialDto.getUnitType(),
					materialDto.getSupplierId(),
					materialDto.getDescription(),
					materialDto.getStock(),id);

		}catch (Exception e){
			log.info("update material error "+e);
			return 0;

		}
	}


	public List<MaterialDto> findAll() {
		return jdbcTemplate.query(materialConstant.getFIND_ALL(),
				(rs, rowNum) ->
		new MaterialDto(
				rs.getInt("id"),
				rs.getString("name"),
				rs.getDouble("unit_cost"),
				rs.getString("unit_type"),
				rs.getInt("supplier_id"),
				rs.getString("description"),
				rs.getDouble("stock")
				
				)
				);

	}
	
	
	@SuppressWarnings("deprecation")
	public Optional<MaterialDto> findById(int id) {
		return jdbcTemplate.queryForObject(materialConstant.getFIND_BY_ID(),
				new Object[]{id},
				(rs, rowNum) ->
		Optional.of(new MaterialDto(
				rs.getInt("id"),
				rs.getString("name"),
				rs.getDouble("unit_cost"),
				rs.getString("unit_type"),
				rs.getInt("supplier_id"),
				rs.getString("description"),
				rs.getDouble("stock")
				)
				));

	}


	public int delete(int id) {
		try {
			return jdbcTemplate.update(materialConstant.getDELETE(),
					id);
		}catch (Exception e) {
			log.info("catch execption in material delete " + e);
			return 0;
		}
	}

	@SuppressWarnings("deprecation")
	public Optional<StockPriceDto> getUnitPriceById(int id) {
		
			return jdbcTemplate.queryForObject(materialConstant.getGET_UNIT_PRICE(),
					new Object[]{id},
					(rs, rowNum) ->
			Optional.of(new StockPriceDto(
					rs.getDouble("unit_cost"),
					rs.getDouble("stock")
					)
					));

		}


	public int updateStock(double newStock, int materialId) {
		try {
			return jdbcTemplate.update(materialConstant.getUPDATE_STOCK(),
						newStock,materialId);
			}catch (Exception e) {
				log.info("catch execption in update stock material " + e);
				return 0;
			}
	}
		
		
		
	
}