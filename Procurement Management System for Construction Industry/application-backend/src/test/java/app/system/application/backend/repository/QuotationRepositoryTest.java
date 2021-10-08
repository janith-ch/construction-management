package app.system.application.backend.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import app.system.application.backend.model.dto.QuotationDto;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
public class QuotationRepositoryTest {

	@Autowired
	private QuotationRepository quotationRepository;


	@Test
	@Order(1)
	public void testQuotationCreate() {

		QuotationDto quotationDto = new QuotationDto();

		quotationDto.setUnitCost(1005);
		quotationDto.setQuanitity(77);
		quotationDto.setDate("2021-08-05");
		quotationDto.setValidLastDate("2021-08-05");
		quotationDto.setOrderId(1);
		quotationDto.setIsApproved(1);
		assertNotNull(quotationRepository.save(quotationDto));
	}	


	@Test
	@Order(4)
	public void testReadAllQuotation () {

		List<QuotationDto> list = quotationRepository.findAll();
		assertThat(list).size().isGreaterThan(0);


	}


	@Test
	@Order(2)
	public void testUpdateQuotation () {

		QuotationDto quotationDto = new QuotationDto();

		quotationDto.setUnitCost(1005);
		quotationDto.setQuanitity(77);
		quotationDto.setDate("2021-08-05");
		quotationDto.setValidLastDate("2021-08-05");
		quotationDto.setOrderId(1);
		quotationDto.setIsApproved(1);

		int id = quotationRepository.save(quotationDto);


		quotationDto.setUnitCost(100);
		quotationDto.setQuanitity(77);
		quotationDto.setDate("2021-08-05");
		quotationDto.setValidLastDate("2021-08-05");
		quotationDto.setOrderId(1);
		quotationDto.setIsApproved(2);

		int respone = quotationRepository.update(quotationDto, id);
		assertEquals(1,respone);


	}



	@Test
	@Order(3)
	public void testGetSingleQuotation () {

		QuotationDto quotationDto = new QuotationDto();

		quotationDto.setUnitCost(1005);
		quotationDto.setQuanitity(77);
		quotationDto.setDate("2021-08-05");
		quotationDto.setValidLastDate("2021-08-05");
		quotationDto.setOrderId(1);
		quotationDto.setIsApproved(1);

		int id = quotationRepository.save(quotationDto);

		log.info("id " + id);

		QuotationDto quotationDto2 = quotationRepository.findById(id).get();
		assertEquals(1,quotationDto2.getOrderId());


	}



}
