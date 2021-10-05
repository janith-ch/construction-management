package app.system.application.backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.system.application.backend.model.dto.PaymentDto;
import app.system.application.backend.repository.PaymentRepository;

@Service
public class PaymentService implements PaymentInterface{
	
	@Autowired
	PaymentRepository paymentRepository;

	@Override
	public int save(PaymentDto paymentDto) {
		return paymentRepository.save(paymentDto);
	}

	@Override
	public int update(PaymentDto paymentDto, int id) {
		return paymentRepository.update(paymentDto,id);
	}

	@Override
	public List<PaymentDto> findAll() {
		return paymentRepository.findAll();
	}

	@Override
	public PaymentDto findById(int id) {
		return paymentRepository.findById(id).get();
	}

	@Override
	public int delete(int id) {
		return paymentRepository.delete(id);
	}

}
