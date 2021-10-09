package app.system.application.backend.service;
import app.system.application.backend.constant.AmountEnum;
import app.system.application.backend.constant.Common;
import app.system.application.backend.constant.DeliveryEnum;
import app.system.application.backend.model.dto.OrderDto;
import app.system.application.backend.model.dto.StockPriceDto;
import app.system.application.backend.repository.MaterialRepository;
import app.system.application.backend.repository.OrderRepository;
import app.system.application.backend.utill.Utill;
import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OrderService implements OrderInt {

    @Autowired
    OrderRepository orderRepository;

	@Autowired
	MaterialRepository materialRepository;

	@Autowired
	Common common;
    
    @Override
	public int createOrder(OrderDto orderDto) {	
    	String timeStamp = new SimpleDateFormat("yyyy-MM-dd").format(new Date()); 
    	
      double limitValue = AmountEnum.ORDERLIMIT.getLimit();
    	
      double quantity = orderDto.getQuantity();
      
      int materialId = orderDto.getMaterialId();
      
      orderDto.setOrderDate(timeStamp);
      
    	
      StockPriceDto stockPriceDto = materialRepository.getUnitPriceById(materialId).get();
      
      double unitCost = stockPriceDto.getUnitPrice();
      
    	
      double totalCost = Utill.calculateCost(unitCost, quantity);
    	 
      orderDto.setTotalCost(totalCost);
      
      if (totalCost >= limitValue) {
    	  
    	  orderDto.setIsApprove(common.getTWO());
    	  orderDto.setQuotationStatus(common.getZERO());
    	  
    	  
      }else {
    	  
    	  orderDto.setIsApprove(common.getONE());
    	  orderDto.setQuotationStatus(common.getONE());
      }
      
     return orderRepository.save(orderDto);
    	
	}

	@Override
	public int update(OrderDto orderDto, int id) {
		  
		  double limitValue = AmountEnum.ORDERLIMIT.getLimit();
    	
	      double quantity = orderDto.getQuantity();
	    	
	      StockPriceDto stockPriceDto = materialRepository.getUnitPriceById(orderDto.getMaterialId()).get();
	      
	      double unitCost = stockPriceDto.getUnitPrice();
	    	
	      double totalCost = Utill.calculateCost(unitCost, quantity);
	    	 
	      orderDto.setTotalCost(totalCost);
	      
	      if (totalCost >= limitValue) {
	    	  
	    	  orderDto.setIsApprove(common.getZERO());
	    	  orderDto.setQuotationStatus(common.getZERO());
	    	  
	      }else {
	    	  
	    	  orderDto.setIsApprove(common.getONE());
	    	  orderDto.setQuotationStatus(common.getONE());
	      }
		 return orderRepository.update(orderDto,id);
	}

	@Override
	public List<OrderDto> findAll() {
		 return orderRepository.findAll();
	}

	@Override
	public OrderDto findById(int id) {
		 return orderRepository.findById(id).get();
	}

	@Override
	public int delete(int id) {
		 return orderRepository.delete(id);
	}

	@Override
	public int updateDeliveryStatus(int orderId,String deliveryStatus) {
		
	   return orderRepository.updateDeliveryStatus(orderId, deliveryStatus);
		
	}

	public int approveOrderStatus(int orderId, int status) {
			
	
		
		return orderRepository.approveOrderStatus(status,orderId);
	}

	@Override
	public List<OrderDto> findAllDeliveryOrders() {
		
		String status = DeliveryEnum.DELIVERING.getStatus();
		
		return orderRepository.findAllDelivering(status);
		
		
	}

	@Override
	public List<OrderDto> receiveOrders() {
		return orderRepository.findAll();
	}


}
