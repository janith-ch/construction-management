package app.system.application.backend.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import app.system.application.backend.model.dto.OrderDto;
import app.system.application.backend.model.dto.QuotationDto;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
public class OrderRepositoryTest {

	@Autowired
	private OrderRepository orderRepository;




	@Test
	@Order(1)
	public void testOrderCreate() {

		OrderDto orderDto = new OrderDto();

		orderDto.setSiteId(100);
		orderDto.setMaterialId(7);
		orderDto.setQuantity(100.0);
		orderDto.setTotalCost(900000.0);
		orderDto.setIsApprove(1);
		orderDto.setDeliveryDate("2021-09-07");
		orderDto.setDeliveryStatus("PENDING");

		int id = orderRepository.save(orderDto);
		
		
		assertThat(id).isGreaterThan(0);


		int result = orderRepository.delete(id);
		assertEquals(1, result);
	}
	
	
	
	@Test
	@Order(2)
	public void testUpdateOrder () {

		OrderDto orderDto = new OrderDto();

		orderDto.setSiteId(100);
		orderDto.setMaterialId(7);
		orderDto.setQuantity(100.0);
		orderDto.setTotalCost(900000.0);
		orderDto.setIsApprove(1);
		orderDto.setDeliveryDate("2021-09-07");
		orderDto.setDeliveryStatus("PENDING");

		int id = orderRepository.save(orderDto);
		

		orderDto.setSiteId(100);
		orderDto.setMaterialId(7);
		orderDto.setQuantity(1200.0);
		orderDto.setTotalCost(900000.0);
		orderDto.setIsApprove(1);
		orderDto.setDeliveryDate("2021-09-07");
		orderDto.setDeliveryStatus("PENDING");

		int respone = orderRepository.update(orderDto, id);
		assertEquals(1,respone);

		int result = orderRepository.delete(id);
		assertEquals(1, result);
	}





	@Test
	@Order(4)
	public void testReadAllOrders () {

		List<OrderDto> list = orderRepository.findAll();
		assertThat(list).size().isGreaterThan(0);


	}


	@Test
	@Order(3)
	public void testGetSingleOrderTest () {

		OrderDto orderDto = new OrderDto();

		orderDto.setSiteId(100);
		orderDto.setMaterialId(7);
		orderDto.setQuantity(100.0);
		orderDto.setTotalCost(900000.0);
		orderDto.setIsApprove(1);
		orderDto.setDeliveryDate("2021-09-07");
		orderDto.setDeliveryStatus("PENDING");

		int id = orderRepository.save(orderDto);

		log.info("id " + id);

		OrderDto dto = orderRepository.findById(id).get();
		assertEquals(900000.0,dto.getTotalCost());


	}


}
