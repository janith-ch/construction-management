package app.system.application.backend.repository;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import app.system.application.backend.model.dto.DeliveryNoteDto;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
public class DeliveryNoteRepositoryTest {
	
	@Autowired
	private DeliveryNoteRepository deliveryNoteRepository;
	
	
	@Test
	@Order(1)
	public void testDeliveryNoteCreate() {
		
		DeliveryNoteDto deliveryNoteDto = new DeliveryNoteDto();
		
		deliveryNoteDto.setOrderId(1);
		deliveryNoteDto.setDeliveryStatus("DELIVERING");
		deliveryNoteDto.setDriverName("Nimal Wijesinghe");
		deliveryNoteDto.setVehicleNo("CBJ-0989");
		deliveryNoteDto.setEstimatedDeliveryDateTime("2021-08-05");
		deliveryNoteDto.setNote("test note");
		
		int id = deliveryNoteRepository.save(deliveryNoteDto);
		
		assertThat(id).isGreaterThan(0);
		
		int result = deliveryNoteRepository.delete(id);
		assertEquals(1, result);
		
	}
		
		@Test
		@Order(4)
		public void testReadDeliveryNote () {
			
			List<DeliveryNoteDto> list = deliveryNoteRepository.findAll();
			assertThat(list).size().isGreaterThan(0);
			
			
		}
		
		
		@Test
		@Order(2)
		public void testUpdateDeliveryNote() {
			
			
			DeliveryNoteDto deliveryNoteDto = new DeliveryNoteDto();
			
			
			deliveryNoteDto.setOrderId(1);
			deliveryNoteDto.setDeliveryStatus("DELIVERING");
			deliveryNoteDto.setDriverName("Nimal Wijesinghe");
			deliveryNoteDto.setVehicleNo("CBJ-0989");
			deliveryNoteDto.setEstimatedDeliveryDateTime("2021-08-05");
			deliveryNoteDto.setNote("test note");
			
		  int	id = deliveryNoteRepository.save(deliveryNoteDto);

			
		
			deliveryNoteDto.setOrderId(1);
			deliveryNoteDto.setDeliveryStatus("DELIVERED");
			deliveryNoteDto.setDriverName("Kamal wijesinghe");
			deliveryNoteDto.setVehicleNo("CBJ-0989");
			deliveryNoteDto.setEstimatedDeliveryDateTime("2021-08-05");
			deliveryNoteDto.setNote("test note 3");
			
			int respone = deliveryNoteRepository.update(deliveryNoteDto, id);
			assertEquals(1,respone);
			
			
			int result = deliveryNoteRepository.delete(id);
			assertEquals(1, result);
			
		
		}
		
				
		@Test
		@Order(3)
		public void testGetSingleDeliveryNote () {
			
			DeliveryNoteDto deliveryNoteDto = new DeliveryNoteDto();
			
			deliveryNoteDto.setOrderId(1);
			deliveryNoteDto.setDeliveryStatus("DELIVERING");
			deliveryNoteDto.setDriverName("Nimal Wijesinghe");
			deliveryNoteDto.setVehicleNo("CBJ-0989");
			deliveryNoteDto.setEstimatedDeliveryDateTime("2021-08-05");
			deliveryNoteDto.setNote("test note");
			
			int id = deliveryNoteRepository.save(deliveryNoteDto);
			
			
			DeliveryNoteDto deliveryNoteDtos = deliveryNoteRepository.findById(id).get();
			assertEquals("CBJ-0989",deliveryNoteDtos.getVehicleNo());
			
			int result = deliveryNoteRepository.delete(id);
			assertEquals(1, result);
			
		}
		
		

	
	

}
