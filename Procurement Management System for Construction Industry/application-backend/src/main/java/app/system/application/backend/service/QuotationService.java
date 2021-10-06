package app.system.application.backend.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.system.application.backend.model.dto.OrderDto;
import app.system.application.backend.model.dto.QuotationDto;
import app.system.application.backend.repository.OrderRepository;
import app.system.application.backend.repository.QuotationRepository;
import app.system.application.backend.utill.Utill;

@Service
public class QuotationService implements QuotationInterface {
	
	@Autowired
	QuotationRepository quotationRepository;
	
	@Autowired
	OrderRepository orderRepository; 
	

	@Override
	public int save(QuotationDto quotationDto) {
		
		String timeStamp = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		
		double quanitity = quotationDto.getQuanitity();
		double unitPrice = quotationDto.getUnitCost();
		
		double amount = Utill.calculateCost(unitPrice, quanitity);
		
		quotationDto.setAmount(amount);
	 
		quotationDto.setDate(timeStamp);
		
		quotationDto.setIsApproved(2);
		
		int orderId = quotationDto.getOrderId();
		
	    int quotationId = quotationRepository.save(quotationDto);
	    
	    if(quotationId >= 1) {
	    	
	    	OrderDto order = orderRepository.findById(orderId).get();
			
			order.setQuotationStatus(2);
			
			int updateResult = orderRepository.update(order,orderId);
			
			return updateResult;
			
			
	    	
	    }else {
	    	
	    	return 0;
	    }
		
		 
			
	}

	@Override
	public int update(QuotationDto quotationDto, int id) {
		return quotationRepository.update(quotationDto,id);
	}

	@Override
	public List<QuotationDto> findAll() {
		return quotationRepository.findAll();
	}

	@Override
	public QuotationDto findById(int id) {
		return quotationRepository.findById(id).get();
	}

	@Override
	public int delete(int id) {
		return quotationRepository.delete(id);
	}
	
	
	@Override
	public List<OrderDto> getQuotationOrderList() {
		
		ArrayList<OrderDto> quotationOrderList = new ArrayList<OrderDto>();
		
		List<OrderDto> allList = orderRepository.findAll();
		
		
		for (OrderDto dto: allList) {
			
		//	double amount = dto.getTotalCost();
			
			int isApprove = dto.getIsApprove();
			
			double orderTotal = dto.getTotalCost();
			
			int quotationStatus = dto.getQuotationStatus();
			
			if(isApprove == 1 && orderTotal >= 100000 && quotationStatus == 2 ) {
				
				quotationOrderList.add(dto);
				
				
			}
			
			
		}
		
		return quotationOrderList;
	}

	@Override
	public int updateQuotationStatus(int id,int status) {

		int updateResult = quotationRepository.updateStatus(id,status);
		
		if(updateResult >= 1) {
			
			QuotationDto quotationDto = quotationRepository.findById(id).get();
			
			int orderId = quotationDto.getOrderId();
			
			OrderDto orderDto = orderRepository.findById(orderId).get();
			
			orderDto.setQuotationStatus(3);
			
			int updateOrderResult = orderRepository.update(orderDto, orderId);
			
			
			if(updateOrderResult >= 1) {
				return 1;
			
			
			}else {
				
				return 0;
			}
			
			
			
			
		}else {
			
			return 0;
		}
		
		
	
	
	
	}		
	
	
	
	

}
