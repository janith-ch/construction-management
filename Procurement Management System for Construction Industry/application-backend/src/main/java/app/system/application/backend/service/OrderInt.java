package app.system.application.backend.service;
import java.util.List;

import app.system.application.backend.model.dto.OrderDto;

public interface OrderInt {

    int createOrder(OrderDto orderDto);
    
    int update(OrderDto orderDto,int id);

	List<OrderDto> findAll();

	OrderDto findById(int id);

	int delete(int id);
	
	int updateDeliveryStatus(int orderId,String status);
	
	int approveOrderStatus(int orderId,int status);
	
	List<OrderDto> findAllDeliveryOrders();

	List<OrderDto> receiveOrders();
	
	

}
