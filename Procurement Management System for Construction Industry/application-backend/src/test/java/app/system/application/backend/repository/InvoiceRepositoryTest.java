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
import app.system.application.backend.model.dto.InvoiceDto;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
public class InvoiceRepositoryTest {
	
	@Autowired
	private InvoiceRepository invoiceRepository;
	
	
	@Test
	@Order(1)
	public void testInvoiceCreate() {
		
		InvoiceDto invoiceDto = new InvoiceDto();
		
		invoiceDto.setSiteId(1005);
		invoiceDto.setMaterialId(77);
		invoiceDto.setQuantity(100.0);
		invoiceDto.setTotal(100000.0);
		invoiceDto.setIsApproved(1);
		invoiceDto.setOrderId(20045);
		int id = invoiceRepository.insert(invoiceDto);

		assertThat(id).isGreaterThan(0);

		int result = invoiceRepository.delete(id);
		assertEquals(1, result);
	}	
		
		
		@Test
		@Order(4)
		public void testReadAllInvoices () {
			
			List<InvoiceDto> list = invoiceRepository.findAll();
			assertThat(list).size().isGreaterThan(0);
			
			
		}
		
		
		@Test
		@Order(2)
		public void testUpdateInvoice () {
			
			InvoiceDto invoiceDto = new InvoiceDto();
			
			invoiceDto.setSiteId(100);
			invoiceDto.setMaterialId(7);
			invoiceDto.setQuantity(100.0);
			invoiceDto.setTotal(900000.0);
			invoiceDto.setIsApproved(1);
			invoiceDto.setOrderId(200);
			
			int id = invoiceRepository.insert(invoiceDto);
			
		
			invoiceDto.setSiteId(100);
			invoiceDto.setMaterialId(7);
			invoiceDto.setQuantity(100.0);
			invoiceDto.setTotal(800000.0);
			invoiceDto.setIsApproved(1);
			invoiceDto.setOrderId(400);
			
			int respone = invoiceRepository.update(invoiceDto, id);
			assertEquals(1,respone);
			
			int result = invoiceRepository.delete(id);
			assertEquals(1, result);
			
		}
		
		
		
		@Test
		@Order(3)
		public void testGetSingleInvoice () {
			
			InvoiceDto invoiceDto = new InvoiceDto();
			
			invoiceDto.setSiteId(100);
			invoiceDto.setMaterialId(7);
			invoiceDto.setQuantity(100.0);
			invoiceDto.setTotal(900000.0);
			invoiceDto.setIsApproved(1);
			invoiceDto.setOrderId(200);
			
			int id = invoiceRepository.insert(invoiceDto);
		
			
			InvoiceDto invoice = invoiceRepository.findById(id).get();
			assertEquals(200,invoice.getOrderId());
			
			int result = invoiceRepository.delete(id);
			assertEquals(1, result);
		}
		
		
		
	

}
