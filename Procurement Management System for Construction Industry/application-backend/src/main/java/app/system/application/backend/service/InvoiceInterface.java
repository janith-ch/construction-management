package app.system.application.backend.service;

import java.util.List;

import app.system.application.backend.model.dto.InvoiceDto;

public interface InvoiceInterface {
	
	
    int save(InvoiceDto invoiceDto);
    
    int update(InvoiceDto invoiceDto,int id);

	List<InvoiceDto> findAll();

	InvoiceDto findById(int id);

	int delete(int id);
	

}
