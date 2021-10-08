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
import org.springframework.boot.test.mock.mockito.MockBean;

import app.system.application.backend.model.dto.QuotationDto;
import app.system.application.backend.service.QuotationService;
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

		quotationDto.setUnitCost(1100);
		quotationDto.setQuanitity(300);
		quotationDto.setDate("2021-08-05");
		quotationDto.setValidLastDate("2021-08-05");
		quotationDto.setOrderId(200345);
		quotationDto.setIsApproved(1);
		int id = quotationRepository.save(quotationDto);

		assertThat(id).isGreaterThan(0);

		int result = quotationRepository.delete(id);
		assertEquals(1, result);
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

		quotationDto.setUnitCost(1200);
		quotationDto.setQuanitity(200);
		quotationDto.setDate("2021-08-05");
		quotationDto.setValidLastDate("2021-08-05");
		quotationDto.setOrderId(200344);
		quotationDto.setIsApproved(1);

		int id = quotationRepository.save(quotationDto);
		log.info("id " + id);

		quotationDto.setUnitCost(1000);
		quotationDto.setQuanitity(100);
		quotationDto.setDate("2021-08-04");
		quotationDto.setValidLastDate("2021-08-06");
		quotationDto.setOrderId(1);
		quotationDto.setIsApproved(2);

		int respone = quotationRepository.update(quotationDto, id);
		assertEquals(1,respone);

		int result = quotationRepository.delete(id);
		assertEquals(1, result);
	}



	@Test
	@Order(3)
	public void testGetSingleQuotation () {

		QuotationDto quotationDto = new QuotationDto();

		quotationDto.setUnitCost(1500);
		quotationDto.setQuanitity(50);
		quotationDto.setDate("2021-08-05");
		quotationDto.setValidLastDate("2021-08-05");
		quotationDto.setOrderId(14876);
		quotationDto.setIsApproved(1);

		int id = quotationRepository.save(quotationDto);


		QuotationDto quotationDto2 = quotationRepository.findById(id).get();
		assertEquals(14876,quotationDto2.getOrderId());
		assertEquals(50,quotationDto2.getQuanitity());
		assertEquals(1500,quotationDto2.getUnitCost());


		int result = quotationRepository.delete(id);
		assertEquals(1, result);
	}



}
