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

import app.system.application.backend.constant.AmountEnum;
import app.system.application.backend.constant.StatusEnum;
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
	private QuotationRepository quotationRepository;

	@Autowired
	private OrderRepository orderRepository; 


	@Autowired
	private UserRepository userRepository; 

	@Autowired
	private MaterialRepository materialRepository; 

	@Autowired
	private JavaMailSender javaMailSender;



	@Value("${email.username}")
	private static String sendFromEmail;





	@Override
	public int save(QuotationDto quotationDto) {
		
		//get current values
		String timeStamp = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

		double quanitity = quotationDto.getQuanitity();
		double unitPrice = quotationDto.getUnitCost();

		double amount = Utill.calculateCost(unitPrice, quanitity);

		quotationDto.setAmount(amount);

		quotationDto.setDate(timeStamp);
		//set status as pending
		quotationDto.setIsApproved(StatusEnum.PENDING.getStatus());

		int orderId = quotationDto.getOrderId();
		// save quotations
		int quotationId = quotationRepository.save(quotationDto);

		if(quotationId >= StatusEnum.APPROVED.getStatus()) {

			OrderDto order = orderRepository.findById(orderId).get();

			order.setQuotationStatus(StatusEnum.PENDING.getStatus());

			int updateResult = orderRepository.update(order,orderId);
			
			log.info("update result " + updateResult);

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
		
		//get list of orders
		List<OrderDto> allList = orderRepository.findAll();

  
		for (OrderDto dto: allList) {

			int isApprove = dto.getIsApprove();

			double orderTotal = dto.getTotalCost();

			int quotationStatus = dto.getQuotationStatus();
			//check conditions
			if(isApprove == StatusEnum.APPROVED.getStatus()  && orderTotal >= AmountEnum.ORDERLIMIT.getLimit()) {

				if(quotationStatus == StatusEnum.APPROVEDQUOATATION.getStatus() || StatusEnum.PENDING.getStatus() == 2  ) {

					quotationOrderList.add(dto);


				}


			}



		}

		return quotationOrderList;
	}

	@Override
	public int updateQuotationStatus(int id,int status) {
		//update status
		int updateResult = quotationRepository.updateStatus(id,status);

		//check the result
		if(updateResult >= StatusEnum.APPROVED.getStatus()) {
			
			//get quotation by quotation Id
			QuotationDto quotationDto = quotationRepository.findById(id).get();

			int orderId = quotationDto.getOrderId();

			log.info("order Id" + orderId);

			OrderDto orderDto = orderRepository.findById(orderId).get();

			int materialId = orderDto.getMaterialId();

			log.info("material Id" + materialId);

			MaterialDto materialDto = materialRepository.findById(materialId).get();

			UserDto userDto = userRepository.findById(materialDto.getSupplierId()).get();



			if(status == StatusEnum.APPROVED.getStatus() ) {


				orderDto.setQuotationStatus(1);

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

    //confirmation email send method
	public void sendConfirmationEmail(UserDto userDto,QuotationDto quotationDto) {

		String email = userDto.getEmail();

		log.info("send to email :" + email);

		String name = userDto.getLastName();

		int orderId = quotationDto.getOrderId();




		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setTo(email);
		msg.setFrom(sendFromEmail);
		//email body and subject assign
		msg.setSubject("QUOTATION APPROVAL");
		msg.setText( "Dear " + name + System.lineSeparator()+  System.lineSeparator()+  "We are pleased to inform you that your Quotation, ORDER ID :-"+orderId+ " has approved for delivery."
				+ " Please make sure to proceed the delivery to the site." 
				+ System.lineSeparator()+  System.lineSeparator()+"Thank you !");
		javaMailSender.send(msg);

	}





}
