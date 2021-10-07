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
import app.system.application.backend.constant.QuotationConstant;
import app.system.application.backend.model.dto.QuotationDto;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class QuotationRepository {
	
	@Autowired
	@Qualifier("system-jdbc-template")
	JdbcTemplate jdbcTemplate;
	
	@Autowired
	QuotationConstant quotationConstant;

	public int save(QuotationDto quotationDto) {
		KeyHolder keyHolder = new GeneratedKeyHolder();

		int result = jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(quotationConstant.getINSERT_SQL(), new String[] { "id" });                                                                  

				ps.setDouble(1,quotationDto.getUnitCost());
				ps.setDouble(2,quotationDto.getQuanitity());
				ps.setString(3,quotationDto.getDate());
				ps.setString(4,quotationDto.getValidLastDate());
				ps.setDouble(5,quotationDto.getAmount());
				ps.setInt(6,quotationDto.getOrderId());
				ps.setDouble(7,quotationDto.getIsApproved());

				return ps;
			}
		},keyHolder);


		return keyHolder.getKey().intValue();
	}

	public int update(QuotationDto quotationDto, int id) {
		try {

			return  jdbcTemplate.update(quotationConstant.getUPDATE_SQL(),
					quotationDto.getUnitCost(),
					quotationDto.getQuanitity(),
					quotationDto.getDate(),
					quotationDto.getValidLastDate(),
					quotationDto.getAmount(),
					quotationDto.getOrderId(),
					quotationDto.getIsApproved(),id);

		}catch (Exception e){
			log.info("update quotation error "+e);
			return 0;

		}
	}

	public List<QuotationDto> findAll() {
		return jdbcTemplate.query(quotationConstant.getFIND_ALL(),
				(rs, rowNum) ->
		new QuotationDto(
				rs.getInt("id"),
				rs.getDouble("unit_cost"),
				rs.getDouble("quantity"),
				rs.getString("qutation_date"),
				rs.getString("valid_last_date"),
				rs.getDouble("amount"),
				rs.getInt("order_id"),
				rs.getInt("is_approved")

				
				)
				);
	}

	@SuppressWarnings("deprecation")
	public Optional<QuotationDto> findById(int id) {
		return jdbcTemplate.queryForObject(quotationConstant.getFIND_BY_ID(),
				new Object[]{id},
				(rs, rowNum) ->
		Optional.of(new QuotationDto(
				rs.getInt("id"),
				rs.getDouble("unit_cost"),
				rs.getDouble("quantity"),
				rs.getString("qutation_date"),
				rs.getString("valid_last_date"),
				rs.getDouble("amount"),
				rs.getInt("order_id"),
				rs.getInt("is_approved")
				)
				));
	}
	
	
	@SuppressWarnings("deprecation")
	public Optional<QuotationDto> findByOrderId(int id) {
		return jdbcTemplate.queryForObject(quotationConstant.getFIND_BY_ORDER_ID(),
				new Object[]{id},
				(rs, rowNum) ->
		Optional.of(new QuotationDto(
				rs.getInt("id"),
				rs.getDouble("unit_cost"),
				rs.getDouble("quantity"),
				rs.getString("qutation_date"),
				rs.getString("valid_last_date"),
				rs.getDouble("amount"),
				rs.getInt("order_id"),
				rs.getInt("is_approved")
				)
				));
	}

	public int delete(int id) {
		try {
			return jdbcTemplate.update(quotationConstant.getDELETE(),
					id);
		}catch (Exception e) {
			log.info("catch execption in Quotation delete " + e);
			return 0;
		}
	}

	public int updateStatus(int id, int status) {
		try {

			return  jdbcTemplate.update(quotationConstant.getUPDATE_STATUS(),status,id);

		}catch (Exception e){
			log.info("update status quotation error "+e);
			return 0;

		}
	}

	
	



}
