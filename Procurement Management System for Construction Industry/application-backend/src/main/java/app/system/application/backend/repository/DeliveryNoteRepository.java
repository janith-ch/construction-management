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
import app.system.application.backend.constant.DeliveryNoteConstant;
import app.system.application.backend.model.dto.DeliveryNoteDto;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class DeliveryNoteRepository {
	
	@Autowired
	@Qualifier("system-jdbc-template")
	JdbcTemplate jdbcTemplate;
	
	
	@Autowired
	DeliveryNoteConstant deliveryNoteConstant;

	public int save(DeliveryNoteDto deliveryNoteDto) {
		
		KeyHolder keyHolder = new GeneratedKeyHolder();

		int result = jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(deliveryNoteConstant.getINSERT_SQL(), new String[] { "id" });                                                                  

				ps.setInt(1,deliveryNoteDto.getOrderId());
				ps.setString(2,deliveryNoteDto.getDeliveryStatus());
				ps.setString(3,deliveryNoteDto.getDriverName());
				ps.setString(4,deliveryNoteDto.getVehicleNo());
				ps.setString(5,deliveryNoteDto.getContactNumber());
				ps.setString(6,deliveryNoteDto.getEstimatedDeliveryDateTime());
				ps.setString(7,deliveryNoteDto.getNote());

				return ps;
			}
		},keyHolder);


		return keyHolder.getKey().intValue();
	}

	public int update(DeliveryNoteDto deliveryNoteDto, int id) {
		try {

			return  jdbcTemplate.update(deliveryNoteConstant.getUPDATE_SQL(),
					deliveryNoteDto.getOrderId(),
					deliveryNoteDto.getDeliveryStatus(),
					deliveryNoteDto.getDriverName(),
					deliveryNoteDto.getVehicleNo(),
					deliveryNoteDto.getContactNumber(),
					deliveryNoteDto.getEstimatedDeliveryDateTime(),
					deliveryNoteDto.getNote(),id);

		}catch (Exception e){
			log.info("update delivery note error "+e);
			return 0;

		}
	}

	public List<DeliveryNoteDto> findAll() {
		return jdbcTemplate.query(deliveryNoteConstant.getFIND_ALL(),
				(rs, rowNum) ->
		new DeliveryNoteDto(
				rs.getInt("id"),
				rs.getInt("order_id"),
				rs.getString("delivery_status"),
				rs.getString("driver_name"),
				rs.getString("vehicle_no"),
				rs.getString("contact_number"),
				rs.getString("estimated_delivery_date_time"),
				rs.getString("note")
				)
				);
	}

	@SuppressWarnings("deprecation")
	public Optional<DeliveryNoteDto> findById(int id) {
		return jdbcTemplate.queryForObject(deliveryNoteConstant.getFIND_BY_ID(),
				new Object[]{id},
				(rs, rowNum) ->
		Optional.of(new DeliveryNoteDto(
				rs.getInt("id"),
				rs.getInt("order_id"),
				rs.getString("delivery_status"),
				rs.getString("driver_name"),
				rs.getString("vehicle_no"),
				rs.getString("contact_number"),
				rs.getString("estimated_delivery_date_time"),
				rs.getString("note")
				)
				));
	}

	public int delete(int id) {
		try {
			return jdbcTemplate.update(deliveryNoteConstant.getDELETE(),
					id);
		}catch (Exception e) {
			log.info("catch execption in delivery note delete " + e);
			return 0;
		}
	}
	
	
	public int deliveryRejected(double newStock, int materialId) {
		try {
			return jdbcTemplate.update(deliveryNoteConstant.getUPDATE_STOCK(),
						newStock,materialId);
			}catch (Exception e) {
				log.info("catch execption in update stock material " + e);
				return 0;
			}
	}
		
	

}
