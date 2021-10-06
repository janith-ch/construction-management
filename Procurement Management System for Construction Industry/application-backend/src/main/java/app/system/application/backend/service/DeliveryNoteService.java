package app.system.application.backend.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.system.application.backend.constant.DeliveryEnum;
import app.system.application.backend.model.dto.DeliveryNoteDto;
import app.system.application.backend.model.dto.OrderDto;
import app.system.application.backend.repository.DeliveryNoteRepository;

@Service
public class DeliveryNoteService implements DeliveryNoteInterface {
	
	@Autowired
	DeliveryNoteRepository deliveryNoteRepository;
	
	@Autowired
	OrderService orderService;
	
	@Autowired
	MaterialService materialService;

	@Autowired
	QuotationService quotationService;

	@Override
	public int save(DeliveryNoteDto deliveryNoteDto) {
		
		deliveryNoteDto.setDeliveryStatus(DeliveryEnum.DELIVERING.getStatus());
		
		int id = deliveryNoteRepository.save(deliveryNoteDto); 
		
		if(id >= 1) {
			
			int orderId = deliveryNoteDto.getOrderId();
			String orderStatus1 = DeliveryEnum.DELIVERING.getStatus();
				
			OrderDto orderDto = orderService.findById(orderId);
			
			double quantity = orderDto.getQuantity();
			
			int materialId = orderDto.getMaterialId();
			
			int updateRespone = materialService.updateMaterialStock(quantity, materialId);
			
			if(updateRespone != 0) {
				
				orderService.updateDeliveryStatus(orderId, orderStatus1);
			}
				
			return id;
				
		}else
			
			return 0;
							
		
	}

	@Override
	public int update(DeliveryNoteDto deliveryNoteDto, int id) {
		return deliveryNoteRepository.update(deliveryNoteDto,id); 
	}

	@Override
	public List<DeliveryNoteDto> findAll() {
		return deliveryNoteRepository.findAll(); 
	}

	@Override
	public DeliveryNoteDto findById(int id) {
		return deliveryNoteRepository.findById(id).get(); 
	}

	@Override
	public int delete(int id) {
		return deliveryNoteRepository.delete(id); 
	}
	

	//test this
	@Override
	public List<OrderDto> deliveryOrders() {
		
		ArrayList<OrderDto> deliveryOrderDtos = new ArrayList<OrderDto>();
		
		List<OrderDto> allList = orderService.receiveOrders();

		
		for(OrderDto order : allList) {

			int isApproved = order.getIsApprove();
			String deliveryStatus = order.getDeliveryStatus();

			if(isApproved == 1 && deliveryStatus != DeliveryEnum.DISPATCHED.getStatus()) {
//		if(isApproved==1){
				deliveryOrderDtos.add(order);
			}
		}
		
		return deliveryOrderDtos;
		
	}
	
	
	

}
