package app.system.application.backend.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import app.system.application.backend.model.dto.QuotationDto;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ExtendWith(SpringExtension.class)
@WebMvcTest(QuotationControllerTest.class)
public class QuotationControllerTest {
	
	
	@Autowired
	private MockMvc mvc;
	
	
	@Test
	public void saveQuatation() throws Exception{
		
		
	    QuotationDto quotationDto = new QuotationDto();

		quotationDto.setUnitCost(1100);
		quotationDto.setQuanitity(300);
		quotationDto.setDate("2021-08-07");
		quotationDto.setValidLastDate("2021-08-08");
		quotationDto.setOrderId(200345);
		quotationDto.setIsApproved(1);
	
	RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/v1/quotations", quotationDto);
	MvcResult result = mvc.perform(requestBuilder).andReturn();
	assertNotEquals(0, result.getResponse());
	
	
	}
	
	
	@Test
	public void getAllQuation() throws Exception{
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/v1/quotations");
		MvcResult result = mvc.perform(requestBuilder).andReturn();
		assertEquals(0, result.getResponse().getContentLength());
		
		
	}

}
