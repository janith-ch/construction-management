package app.system.application.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import app.system.application.backend.model.dto.PaymentDto;
import app.system.application.backend.response.CommonResponse;
import app.system.application.backend.service.PaymentService;

@RestController
@CrossOrigin
@RequestMapping("api")
public class PaymentController {
	
	@Autowired
	private PaymentService paymentService;

	@PostMapping("/v1/payments")
	public ResponseEntity<?> addPayment(@RequestBody PaymentDto paymentDto){

		try {

			int result = paymentService.save(paymentDto);

			if(result >= 1 ) {

				return  ResponseEntity.ok(new CommonResponse<Integer>(true,null,result));

			}else {
				return  ResponseEntity.ok(new CommonResponse<Integer>(false,"Cannot add payment",result));
			}
		}
		catch(Exception e) {
			return  ResponseEntity.ok(new CommonResponse<PaymentDto>(false,e.getMessage(),paymentDto));


		}
	}


	@PutMapping("/v1/payments/{id}")
	public ResponseEntity<?> updatePaymentById(@RequestBody PaymentDto paymentDto,@PathVariable int id) {

		int result = paymentService.update(paymentDto,id);

		if(result == 1) {

			return ResponseEntity.ok(new CommonResponse<Integer>(true,null,result));

		}else {

			return ResponseEntity.ok(new CommonResponse<Integer>(false,"payment Not Found",result));

		}


	}

	@GetMapping("/v1/payments/{id}")
	public ResponseEntity<?> getPaymentById(@PathVariable int id) {

		try {

			PaymentDto result = paymentService.findById(id);


			return  ResponseEntity.ok(new CommonResponse<PaymentDto>(true,null,result));

		}catch(Exception e) {

			return  ResponseEntity.ok(new CommonResponse<PaymentDto>(true,"payment not found",null));
		}


	}


	@GetMapping("/v1/payments")
	public  ResponseEntity<?> getPaymentList(){

		List<PaymentDto> result = paymentService.findAll();

		return ResponseEntity.ok(new CommonResponse<List<PaymentDto>>(true,null,result));

	}


	@DeleteMapping("/v1/payments/{id}")
	public ResponseEntity<?> deletePaymentById(@PathVariable int id) {

		int result = paymentService.delete(id);

		if(result == 1) {

			return ResponseEntity.ok(new CommonResponse<Integer>(true,null,result));

		}else {

			return ResponseEntity.ok(new CommonResponse<Integer>(false,"Cannot Delete payment",result));

		}

	}
	

}
