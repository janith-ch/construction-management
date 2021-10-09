package app.system.application.backend.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.system.application.backend.constant.DeliveryEnum;
import app.system.application.backend.constant.StatusEnum;
import app.system.application.backend.model.dto.DeliveryNoteDto;
import app.system.application.backend.model.dto.OrderDto;
import app.system.application.backend.repository.DeliveryNoteRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class DeliveryNoteService implements DeliveryNoteInterface {
	
	@Autowired
	private DeliveryNoteRepository deliveryNoteRepository;
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private MaterialService materialService;

	@Autowired
	private QuotationService quotationService;

	@Override
	public int save(DeliveryNoteDto deliveryNoteDto) {
		
		//set delivering value
		deliveryNoteDto.setDeliveryStatus(DeliveryEnum.DELIVERING.getStatus());
		
		int id = deliveryNoteRepository.save(deliveryNoteDto); 
		//check save successful
		if(id >= 1) {
			
			int orderId = deliveryNoteDto.getOrderId();
			String orderStatus1 = DeliveryEnum.DELIVERING.getStatus();
				
			OrderDto orderDto = orderService.findById(orderId);
			
			double quantity = orderDto.getQuantity();
			
			int materialId = orderDto.getMaterialId();
			//update stock of material
			int updateRespone = materialService.updateMaterialStock(quantity, materialId);
			log.info("Quantity"+ quantity);
			
			if(updateRespone != StatusEnum.REJECT.getStatus()) {
				
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
	

	@Override
	public List<OrderDto> deliveryOrders() {
		
		ArrayList<OrderDto> deliveryOrderDtos = new ArrayList<OrderDto>();
		
		List<OrderDto> allList = orderService.receiveOrders();
		

		//iterate using foreach 
		for(OrderDto order : allList) {

			int isApproved = order.getIsApprove();
			String deliveryStatus = order.getDeliveryStatus();
			int quotationStatus = order.getQuotationStatus();
			

			if( isApproved == StatusEnum.APPROVED.getStatus()  && deliveryStatus.contentEquals(DeliveryEnum.PENDING.getStatus()) ) {

				if( quotationStatus == StatusEnum.APPROVED.getStatus()) {
					
			
					deliveryOrderDtos.add(order);
					
				}
				
				
			}
				
		}
		
		return deliveryOrderDtos;
		
	}
	
	
	

}
