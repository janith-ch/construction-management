package app.system.application.backend.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import app.system.application.backend.model.dto.DeliveryNoteDto;
import app.system.application.backend.model.dto.OrderDto;
import app.system.application.backend.response.CommonResponse;
import app.system.application.backend.service.DeliveryNoteService;

@RestController
@CrossOrigin
@RequestMapping("api")
public class DeliveryNoteController {

	@Autowired
	private DeliveryNoteService deliveryNoteService;

	@PostMapping("/v1/delivery-notes")
	public ResponseEntity<?> addDeliveryNote(@RequestBody DeliveryNoteDto deliveryNoteDto){

		try {

			int result = deliveryNoteService.save(deliveryNoteDto);

			if(result >= 1 ) {

				return  ResponseEntity.ok(new CommonResponse<Integer>(true,null,result));

			}else {
				return  ResponseEntity.ok(new CommonResponse<Integer>(false,"Cannot add delivery note ",result));
			}
		}
		catch(Exception e) {
			return  ResponseEntity.ok(new CommonResponse<DeliveryNoteDto>(false,e.getMessage(),deliveryNoteDto));


		}
	}


	@PutMapping("/v1/delivery-notes/{id}")
	public ResponseEntity<?> updateDeliveryNoteById(@RequestBody DeliveryNoteDto deliveryNoteDto,@PathVariable int id) {

		int result = deliveryNoteService.update(deliveryNoteDto,id);

		if(result == 1) {

			return ResponseEntity.ok(new CommonResponse<Integer>(true,null,result));

		}else {

			return ResponseEntity.ok(new CommonResponse<Integer>(false,"delivery note Not Found",result));

		}


	}

	@GetMapping("/v1/delivery-notes/{id}")
	public ResponseEntity<?> getDeliveryNoteById(@PathVariable int id) {

		try {

			DeliveryNoteDto result = deliveryNoteService.findById(id);


			return  ResponseEntity.ok(new CommonResponse<DeliveryNoteDto>(true,null,result));

		}catch(Exception e) {

			return  ResponseEntity.ok(new CommonResponse<DeliveryNoteDto>(true,"delivery note not found",null));
		}


	}


	@GetMapping("/v1/delivery-notes")
	public  ResponseEntity<?> getDeliveryNoteList(){

		List<DeliveryNoteDto> result = deliveryNoteService.findAll();

		return ResponseEntity.ok(new CommonResponse<List<DeliveryNoteDto>>(true,null,result));

	}

	

	@GetMapping("/v1/delivery-notes/received/orders")
	public  ResponseEntity<?> getDeliveryOrderList(){

		List<OrderDto> result = deliveryNoteService.deliveryOrders();

		return ResponseEntity.ok(new CommonResponse<List<OrderDto>>(true,null,result));

	}


	@DeleteMapping("/v1/delivery-notes/{id}")
	public ResponseEntity<?> deleteDeliveryNoteById(@PathVariable int id) {

		int result = deliveryNoteService.delete(id);

		if(result == 1) {

			return ResponseEntity.ok(new CommonResponse<Integer>(true,null,result));

		}else {

			return ResponseEntity.ok(new CommonResponse<Integer>(false,"Cannot Delete delivery notes",result));

		}

	}
	
	
}
