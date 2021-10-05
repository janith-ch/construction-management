package app.system.application.backend.service;

import java.util.List;

import app.system.application.backend.model.dto.OrderDto;
import app.system.application.backend.model.dto.QuotationDto;

public interface QuotationInterface {
	
	int save(QuotationDto quotationDto);

	int update(QuotationDto quotationDto,int id);

	List<QuotationDto> findAll();

	QuotationDto findById(int id);

	int delete(int id);

	List<OrderDto> getQuotationOrderList();
	
	int updateQuotationStatus(int id,int status);
}
