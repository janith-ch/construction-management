package app.system.application.backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.system.application.backend.constant.AmountEnum;
import app.system.application.backend.constant.DeliveryEnum;
import app.system.application.backend.model.dto.InvoiceDto;
import app.system.application.backend.model.dto.MaterialDto;
import app.system.application.backend.model.dto.OrderDto;
import app.system.application.backend.model.dto.QuotationDto;
import app.system.application.backend.repository.InvoiceRepository;
import app.system.application.backend.repository.MaterialRepository;
import app.system.application.backend.repository.OrderRepository;
import app.system.application.backend.repository.QuotationRepository;
import app.system.application.backend.utill.Utill;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class InvoiceService implements InvoiceInterface {
	
	@Autowired
	private InvoiceRepository invoiceRepository;
	
	@Autowired
	private MaterialRepository materialRepository;
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private QuotationRepository quotationRepository;


	@Override
	public int save(InvoiceDto invoiceDto) {
		
		double quanitity = invoiceDto.getQuantity();
		
		int orderId = invoiceDto.getOrderId();
		
		OrderDto orderDto = orderRepository.findById(orderId).get();
		
		double orderAmount = orderDto.getTotalCost();
		
		int materialId = orderDto.getMaterialId();
		
		if(orderAmount >= AmountEnum.ORDERLIMIT.getLimit()) {
			
			
			QuotationDto quotationDto = quotationRepository.findByOrderId(orderId).get();
			
			int isApproved = quotationDto.getIsApproved();
			
			if( isApproved == 1) {
				
				double unitPriceQutation = quotationDto.getUnitCost();
				
				double amount1 = Utill.calculateCost(unitPriceQutation, quanitity);
				
				
				invoiceDto.setTotal(amount1);

				
			}
			
			orderRepository.updateDeliveryStatus(orderId, DeliveryEnum.DELIVERED.getStatus());	
			return invoiceRepository.insert(invoiceDto) ;
			
			
		}else {

			MaterialDto materialDto = materialRepository.findById(materialId).get();
			
			
			double unitPrice = materialDto.getUnitCost();
			
			
			double amount2 = Utill.calculateCost(unitPrice, quanitity);
			
		
			invoiceDto.setTotal(amount2);
			
			orderRepository.updateDeliveryStatus(orderId, DeliveryEnum.DELIVERED.getStatus());
			
			return invoiceRepository.insert(invoiceDto) ;
			
			
			
		}
	
		
		
	}

	@Override
	public int update(InvoiceDto invoiceDto, int id) {
		return invoiceRepository.update(invoiceDto,id) ;
	}

	@Override
	public List<InvoiceDto> findAll() {
		return invoiceRepository.findAll() ;
	}

	@Override
	public InvoiceDto findById(int id) {
		return invoiceRepository.findById(id).get() ;
	}

	@Override
	public int delete(int id) {
		return invoiceRepository.delete(id) ;
	}
	
	
	

}
