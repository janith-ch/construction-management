package app.system.application.backend.repository;
import app.system.application.backend.constant.OrderConstant;
import app.system.application.backend.model.dto.OrderDto;
import lombok.extern.slf4j.Slf4j;
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
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Repository
@Transactional
public class OrderRepository {

    @Autowired
    OrderConstant orderConstant;

    @Autowired
    private JdbcTemplate jdbcTemplate;

	public int save(OrderDto orderDto) {
		try {
		KeyHolder keyHolder = new GeneratedKeyHolder();

		int result = jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(orderConstant.getINSERT_SQL(), new String[] { "id" });                                                                  

				ps.setInt(1,orderDto.getMaterialId());
				ps.setDouble(2,orderDto.getQuantity());
				ps.setDouble(3,orderDto.getTotalCost());
				ps.setString(4,orderDto.getOrderDate());
				ps.setString(5,orderDto.getDeliveryDate());
				ps.setInt(6,orderDto.getIsApprove());
				ps.setInt(7,orderDto.getSiteId());
				ps.setInt(8,orderDto.getQuotationStatus());

				return ps;
			}
		},keyHolder);
		


		return keyHolder.getKey().intValue();
		
		}catch (Exception e) {
			log.info(e.getLocalizedMessage());
			return 0;
		}
	}

	public int update(OrderDto orderDto,int id) {
		try {

			return  jdbcTemplate.update(orderConstant.getUPDATE_SQL(),
					orderDto.getMaterialId(),
					orderDto.getQuantity(),
					orderDto.getTotalCost(),
					orderDto.getOrderDate(),
					orderDto.getDeliveryDate(),
					orderDto.getIsApprove(),
					orderDto.getSiteId(),
					orderDto.getQuotationStatus(),id);

		}catch (Exception e){
			log.info("update order error "+e);
			return 0;

		}
	}

	public List<OrderDto> findAll() {
		try {
		return jdbcTemplate.query(orderConstant.getFIND_ALL(),
				(rs, rowNum) ->
		new OrderDto(
				rs.getInt("order_id"),
				rs.getInt("material_id"),
				rs.getString("material_name"),
				rs.getInt("supplier_id"),
				rs.getDouble("quantity"),
				rs.getString("unit_type"),
				rs.getDouble("total_cost"),
				rs.getString("ordered_date"),
				rs.getString("delivery_date"),
				rs.getInt("is_approve_status"),
				rs.getInt("site_id"),
				rs.getString("site_name"),
				rs.getString("location"),
				rs.getString("site_phone_nummber"),
				rs.getInt("site_manager_id"),
				rs.getString("first_name"),
				rs.getString("last_name"),
				rs.getInt("quotation_status"),
				rs.getString("delivery_status")
				
				)
				);
		
		}catch (Exception e) {
			log.info("find all " + e);
			return null;
		}
	
	}
	
	
	public List<OrderDto> findAllDelivering(String deliveryStatus) {
		try {
		return jdbcTemplate.query(orderConstant.getFIND_BY_DELIVERY_STATUS(),
				(rs, rowNum) ->
		new OrderDto(
				rs.getInt("order_id"),
				rs.getInt("material_id"),
				rs.getString("material_name"),
				rs.getInt("supplier_id"),
				rs.getDouble("quantity"),
				rs.getString("unit_type"),
				rs.getDouble("total_cost"),
				rs.getString("ordered_date"),
				rs.getString("delivery_date"),
				rs.getInt("is_approve_status"),
				rs.getInt("site_id"),
				rs.getString("site_name"),
				rs.getString("location"),
				rs.getString("site_phone_nummber"),
				rs.getInt("site_manager_id"),
				rs.getString("first_name"),
				rs.getString("last_name"),
				rs.getInt("quotation_status"),
				rs.getString("delivery_status")
				
				),deliveryStatus
				);
		
		}catch (Exception e) {
			log.info("find all " + e);
			return null;
		}
	
	}

	@SuppressWarnings("deprecation")
	public Optional<OrderDto>  findById(int id) {
		try {
		return jdbcTemplate.queryForObject(orderConstant.getFIND_BY_ID(),
				new Object[]{id},
				(rs, rowNum) ->
		Optional.of(new OrderDto(
				rs.getInt("order_id"),
				rs.getInt("material_id"),
				rs.getString("material_name"),
				rs.getInt("supplier_id"),
				rs.getDouble("quantity"),
				rs.getString("unit_type"),
				rs.getDouble("total_cost"),
				rs.getString("ordered_date"),
				rs.getString("delivery_date"),
				rs.getInt("is_approve_status"),
				rs.getInt("site_id"),
				rs.getString("site_name"),
				rs.getString("location"),
				rs.getString("site_phone_nummber"),
				rs.getInt("site_manager_id"),
				rs.getString("first_name"),
				rs.getString("last_name"),
				rs.getInt("quotation_status"),
				rs.getString("delivery_status")
				)
				));
		
		}catch (Exception e) {
			log.info("find by id " + e);
			return null;
		}
	}
	
	
	public int delete(int id) {
		try {
			return jdbcTemplate.update(orderConstant.getDELETE(),
					id);
		}catch (Exception e) {
			log.info("catch execption in order delete " + e);
			return 0;
		}
	}

	
	
	public int updateDeliveryStatus(int id,String status) {
		try {
		return jdbcTemplate.update(orderConstant.getDELIVERY_STATUS_UPDATE(),
					status,id);
		}catch (Exception e) {
			log.info("catch execption in order status " + e);
			return 0;
			
		}
	}

	public int approveOrderStatus(int status, int orderId) {
		try {
			
			return jdbcTemplate.update(orderConstant.getAPPROVE_ORDER(),status,orderId);
			
		}catch (Exception e) {
				log.info("catch execption in order approve " + e);
				return 0;
				
			}
	}


     
}
