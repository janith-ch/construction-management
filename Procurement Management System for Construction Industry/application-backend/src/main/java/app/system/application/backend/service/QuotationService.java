package app.system.application.backend.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import app.system.application.backend.model.dto.MaterialDto;
import app.system.application.backend.model.dto.OrderDto;
import app.system.application.backend.model.dto.QuotationDto;
import app.system.application.backend.model.dto.UserDto;
import app.system.application.backend.repository.MaterialRepository;
import app.system.application.backend.repository.OrderRepository;
import app.system.application.backend.repository.QuotationRepository;
import app.system.application.backend.repository.UserRepository;
import app.system.application.backend.utill.Utill;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class QuotationService implements QuotationInterface {

	@Autowired
	QuotationRepository quotationRepository;

	@Autowired
	OrderRepository orderRepository; 


	@Autowired
	UserRepository userRepository; 

	@Autowired
	MaterialRepository materialRepository; 

	@Autowired
	private JavaMailSender javaMailSender;



	@Value("${email.username}")
	private static String sendFromEmail;





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

			if(isApprove == 1 && orderTotal >= 100000 && quotationStatus == 3 ) {

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

			log.info("order Id" + orderId);

			OrderDto orderDto = orderRepository.findById(orderId).get();

			int materialId = orderDto.getMaterialId();

			log.info("material Id" + materialId);

			MaterialDto materialDto = materialRepository.findById(materialId).get();

			UserDto userDto = userRepository.findById(materialDto.getSupplierId()).get();



			if(status == 1 ) {


				orderDto.setQuotationStatus(3);

				int updateOrderResult = orderRepository.update(orderDto, orderId);

				sendConfirmationEmail(userDto, quotationDto);

				return updateOrderResult;
			}else {

				orderDto.setQuotationStatus(0);

				int updateOrderResult = orderRepository.update(orderDto, orderId);


				return updateOrderResult;


			}




		}

		return 0;





	}


	public  void sendConfirmationEmail(UserDto userDto,QuotationDto quotationDto) {

		String email = userDto.getEmail();

		log.info("user email" + email);

		String name = userDto.getLastName();

		int orderId = quotationDto.getOrderId();




		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setTo(email);
		msg.setFrom(sendFromEmail);

		msg.setSubject("QUOTATION APPROVAL");
		msg.setText( "Dear " + name + System.lineSeparator()+  System.lineSeparator()+  "We are pleased to inform you that your Quotation, ORDER ID :-"+orderId+ " has approved for delivery."
				+ " Please make sure to proceed the delivery to the site." 
				+ System.lineSeparator()+  System.lineSeparator()+"Thank you !");
		javaMailSender.send(msg);

	}





}
