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
		
		String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()); 
		
		double quanitity = quotationDto.getQuanitity();
		double unitPrice = quotationDto.getUnitCost();
		
		double amount = Utill.calculateCost(unitPrice, quanitity);
		
		quotationDto.setAmount(amount);
	 
		quotationDto.setDate(timeStamp);
		
		quotationDto.setIsApproved(2);
		
		return quotationRepository.save(quotationDto);
			
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
			
			double amount = dto.getTotalCost();
			
			int isApprove = dto.getIsApprove();
			
			if(amount >= 100000 && isApprove == 2 ) {
				
				quotationOrderList.add(dto);
				
				
			}
			
			
		}
		
		return quotationOrderList;
	}

	@Override
	public int updateQuotationStatus(int id,int status) {
	
		return quotationRepository.updateStatus(id,status);
	}		
	
	

}
