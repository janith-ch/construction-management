package app.system.application.backend.service;

import java.util.List;

import app.system.application.backend.model.dto.DeliveryNoteDto;
import app.system.application.backend.model.dto.OrderDto;

public interface DeliveryNoteInterface {
	
	
	int save(DeliveryNoteDto deliveryNoteDto);

	int update(DeliveryNoteDto deliveryNoteDto,int id);

	List<DeliveryNoteDto> findAll();

	DeliveryNoteDto findById(int id);

	int delete(int id);
	
	List<OrderDto> deliveryOrders();

}
