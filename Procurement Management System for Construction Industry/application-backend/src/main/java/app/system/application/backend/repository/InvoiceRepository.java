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

import app.system.application.backend.constant.InvoiceConstant;
import app.system.application.backend.model.dto.InvoiceDto;
import app.system.application.backend.model.dto.QuotationDto;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class InvoiceRepository {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private InvoiceConstant invoiceConstant;

	public int insert(InvoiceDto invoiceDto) {
		try {
			KeyHolder keyHolder = new GeneratedKeyHolder();

			int result = jdbcTemplate.update(new PreparedStatementCreator() {
				public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
					PreparedStatement ps = connection.prepareStatement(invoiceConstant.getINSERT_SQL(), new String[] { "id" });                                                                  

					ps.setInt(1,invoiceDto.getSiteId());
					ps.setDouble(2,invoiceDto.getMaterialId());
					ps.setDouble(3,invoiceDto.getQuantity());
					ps.setDouble(4,invoiceDto.getTotal());
					ps.setInt(5,invoiceDto.getIsApproved());
					ps.setInt(6,invoiceDto.getOrderId());


					return ps;
				}
			},keyHolder);
			
			log.info(null);


			return keyHolder.getKey().intValue();
			
			}catch (Exception e) {
				log.info(e.getLocalizedMessage());
				return 0;
			}
	}

	public int update(InvoiceDto invoiceDto, int id) {
		try {

			return  jdbcTemplate.update(invoiceConstant.getUPDATE_SQL(),
					invoiceDto.getSiteId(),
					invoiceDto.getMaterialId(),
					invoiceDto.getQuantity(),
					invoiceDto.getTotal(),
					invoiceDto.getIsApproved(),
					invoiceDto.getOrderId(),id);

		}catch (Exception e){
			log.info("update invoice error "+e);
			return 0;

		}
	}

	public List<InvoiceDto> findAll() {
		return jdbcTemplate.query(invoiceConstant.getFIND_ALL(),
				(rs, rowNum) ->
		new InvoiceDto(
				rs.getInt("id"),
				rs.getInt("site_code"),
				rs.getString("site_name"),
				rs.getString("matrial_name"),
				rs.getString("supplier_name"),
				rs.getInt("material_id"),
				rs.getDouble("qunatity"),
				rs.getDouble("total"),
				rs.getInt("is_approved_site_manager"),
				rs.getInt("order_id")
				
				)
				);
	}
	@SuppressWarnings("deprecation")
	public Optional<InvoiceDto> findById(int id) {
		return jdbcTemplate.queryForObject(invoiceConstant.getFIND_BY_ID(),
				new Object[]{id},
				(rs, rowNum) ->
		Optional.of(new InvoiceDto(
				rs.getInt("id"),
				rs.getInt("site_code"),
				rs.getString("site_name"),
				rs.getString("matrial_name"),
				rs.getString("supplier_name"),
				rs.getInt("material_id"),
				rs.getDouble("qunatity"),
				rs.getDouble("total"),
				rs.getInt("is_approved_site_manager"),
				rs.getInt("order_id")
				)
				));
	}
	
	public int delete(int id) {
		try {
			return jdbcTemplate.update(invoiceConstant.getDELETE(),
					id);
		}catch (Exception e) {
			log.info("catch execption in invoice delete " + e);
			return 0;
		}
	}
	

}
