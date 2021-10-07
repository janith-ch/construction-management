package app.system.application.backend.controller;

import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import app.system.application.backend.model.dto.InvoiceDto;
import app.system.application.backend.response.CommonResponse;
import app.system.application.backend.service.InvoiceService;
@Slf4j
@RestController
@RequestMapping("api")
public class InvoiceController {
	
	@Autowired
	private InvoiceService invoiceService;

	@PostMapping("/v1/invoices")
	public ResponseEntity<?> addInvoice(@RequestBody InvoiceDto invoiceDto){

		try {

			int result = invoiceService.save(invoiceDto);

			if(result >= 1 ) {

				return  ResponseEntity.ok(new CommonResponse<Integer>(true,null,result));

			}else {
				return  ResponseEntity.ok(new CommonResponse<Integer>(false,"Cannot add Invoices",result));
			}
		}
		catch(Exception e) {
			log.info("error "+ e);
			return  ResponseEntity.ok(new CommonResponse<InvoiceDto>(false,e.getMessage(),invoiceDto));


		}
	}


	@PutMapping("/v1/invoices/{id}")
	public ResponseEntity<?> updateInvoiceById(@RequestBody InvoiceDto invoiceDto,@PathVariable int id) {

		int result = invoiceService.update(invoiceDto,id);

		if(result == 1) {

			return ResponseEntity.ok(new CommonResponse<Integer>(true,null,result));

		}else {

			return ResponseEntity.ok(new CommonResponse<Integer>(false,"Invoices Not Found",result));

		}


	}

	@GetMapping("/v1/invoices/{id}")
	public ResponseEntity<?> getInvoiceById(@PathVariable int id) {

		try {

			InvoiceDto result = invoiceService.findById(id);


			return  ResponseEntity.ok(new CommonResponse<InvoiceDto>(true,null,result));

		}catch(Exception e) {

			return  ResponseEntity.ok(new CommonResponse<InvoiceDto>(true,"Invoices not found",null));
		}


	}


	@GetMapping("/v1/invoices")
	public  ResponseEntity<?> getInvoiceList(){

		List<InvoiceDto> result = invoiceService.findAll();

		return ResponseEntity.ok(new CommonResponse<List<InvoiceDto>>(true,null,result));

	}


	@DeleteMapping("/v1/invoices/{id}")
	public ResponseEntity<?> deleteInvoiceById(@PathVariable int id) {

		int result = invoiceService.delete(id);

		if(result == 1) {

			return ResponseEntity.ok(new CommonResponse<Integer>(true,null,result));

		}else {

			return ResponseEntity.ok(new CommonResponse<Integer>(false,"Cannot Delete Invoices",result));

		}

	}

}
