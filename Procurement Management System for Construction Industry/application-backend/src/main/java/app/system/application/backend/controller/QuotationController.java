package app.system.application.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;
import app.system.application.backend.model.dto.OrderDto;
import app.system.application.backend.model.dto.QuotationDto;
import app.system.application.backend.response.CommonResponse;
import app.system.application.backend.service.QuotationService;

@CrossOrigin
@RestController
@RequestMapping("api")
public class QuotationController {
	
	
	@Autowired
	private QuotationService quotationService;

	@PostMapping("/v1/quotations")
	public ResponseEntity<?> addQuotation(@RequestBody QuotationDto quotationDto){

		try {

			int result = quotationService.save(quotationDto);

			if(result >= 1 ) {

				return  ResponseEntity.ok(new CommonResponse<Integer>(true,null,result));

			}else {
				return  ResponseEntity.ok(new CommonResponse<Integer>(false,"Cannot add Quotation",result));
			}
		}
		catch(Exception e) {
			return  ResponseEntity.ok(new CommonResponse<QuotationDto>(false,e.getMessage(),quotationDto));


		}
	}


	@PutMapping("/v1/quotations/{id}")
	public ResponseEntity<?> updateQuotationById(@RequestBody QuotationDto quotationDto,@PathVariable int id) {

		int result = quotationService.update(quotationDto,id);

		if(result == 1) {

			return ResponseEntity.ok(new CommonResponse<Integer>(true,null,result));

		}else {

			return ResponseEntity.ok(new CommonResponse<Integer>(false,"Quotation Not Found",result));

		}


	}

	@GetMapping("/v1/quotations/{id}")
	public ResponseEntity<?> getQuotationById(@PathVariable int id) {

		try {

			QuotationDto result = quotationService.findById(id);


			return  ResponseEntity.ok(new CommonResponse<QuotationDto>(true,null,result));

		}catch(Exception e) {

			return  ResponseEntity.ok(new CommonResponse<QuotationDto>(true,"Quotation not found",null));
		}


	}


	@GetMapping("/v1/quotations")
	public  ResponseEntity<?> getQuotationList(){

		List<QuotationDto> result = quotationService.findAll();

		return ResponseEntity.ok(new CommonResponse<List<QuotationDto>>(true,null,result));

	}
	
	
	@GetMapping("/v1/quotations/orderList")
	public  ResponseEntity<?> getQuotationOrderList(){

		List<OrderDto> result = quotationService.getQuotationOrderList();

		return ResponseEntity.ok(new CommonResponse<List<OrderDto>>(true,null,result));

	}


	@DeleteMapping("/v1/quotations/{id}")
	public ResponseEntity<?> deleteQuotationById(@PathVariable int id) {

		int result = quotationService.delete(id);

		if(result == 1) {

			return ResponseEntity.ok(new CommonResponse<Integer>(true,null,result));

		}else {

			return ResponseEntity.ok(new CommonResponse<Integer>(false,"Cannot Delete Quotation",result));

		}

	}
	
	@PutMapping("/v1/quotations/status")
	public ResponseEntity<?> updateQuotationStatusById(@RequestParam("id") int id, @RequestParam("status") int status) {

		int result = quotationService.updateQuotationStatus(id,status);

		if(result == 1) {

			return ResponseEntity.ok(new CommonResponse<Integer>(true,null,result));

		}else {

			return ResponseEntity.ok(new CommonResponse<Integer>(false,"Quotation Not Found",result));

		}

}
	
}
