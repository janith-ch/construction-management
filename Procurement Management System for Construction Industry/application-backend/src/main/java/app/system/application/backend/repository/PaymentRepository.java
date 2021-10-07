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
import app.system.application.backend.constant.PaymentConstant;
import app.system.application.backend.model.dto.PaymentDto;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class PaymentRepository {
	
	@Autowired
	PaymentConstant paymentConstant;
	
	
	@Autowired
	@Qualifier("system-jdbc-template")
	JdbcTemplate jdbcTemplate;


	public int save(PaymentDto paymentDto) {
		
		KeyHolder keyHolder = new GeneratedKeyHolder();

		int result = jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(paymentConstant.getINSERT_SQL(), new String[] { "id" });                                                                  

				ps.setInt(1,paymentDto.getInvoiceId());
				ps.setDouble(2,paymentDto.getAmount());
				ps.setString(3,paymentDto.getPaymentMethod());
				ps.setInt(4,paymentDto.getUserId());
				ps.setString(5,paymentDto.getPaymentDate());

				return ps;
			}
		},keyHolder);


		return keyHolder.getKey().intValue();
			
		
	}


	public int update(PaymentDto paymentDto, int id) {
		try {

			return  jdbcTemplate.update(paymentConstant.getUPDATE_SQL(),
					paymentDto.getInvoiceId(),
					paymentDto.getAmount(),
					paymentDto.getPaymentMethod(),
					paymentDto.getUserId(),
					paymentDto.getPaymentDate(),id);

		}catch (Exception e){
			log.info("update payment error "+e);
			return 0;

		}
	}


	public List<PaymentDto> findAll() {
		return jdbcTemplate.query(paymentConstant.getFIND_ALL(),
				(rs, rowNum) ->
		new PaymentDto(
				rs.getInt("id"),
				rs.getInt("invoice_number"),
				rs.getDouble("amount"),
				rs.getString("payment_method"),
				rs.getInt("accountant_id"),
				rs.getString("payment_date")
				
				)
				);
	}

	@SuppressWarnings("deprecation")
	public Optional<PaymentDto> findById(int id) {
		return jdbcTemplate.queryForObject(paymentConstant.getFIND_BY_ID(),
				new Object[]{id},
				(rs, rowNum) ->
		Optional.of(new PaymentDto(
				rs.getInt("id"),
				rs.getInt("invoice_number"),
				rs.getDouble("amount"),
				rs.getString("payment_method"),
				rs.getInt("accountant_id"),
				rs.getString("payment_date")
				)
				));

	}


	public int delete(int id) {
		try {
			return jdbcTemplate.update(paymentConstant.getDELETE(),
					id);
		}catch (Exception e) {
			log.info("catch execption in payment delete " + e);
			return 0;
		}
	}
	
	
	
	

}
