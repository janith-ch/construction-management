package app.system.application.backend.service;

import java.util.List;

import app.system.application.backend.model.dto.PaymentDto;

public interface PaymentInterface {
	
	int save(PaymentDto paymentDto);

	int update(PaymentDto paymentDto,int id);

	List<PaymentDto> findAll();

	PaymentDto findById(int id);

	int delete(int id);

}
