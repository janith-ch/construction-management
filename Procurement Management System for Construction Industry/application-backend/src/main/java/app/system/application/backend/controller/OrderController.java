package app.system.application.backend.controller;

import app.system.application.backend.model.dto.OrderDto;
import app.system.application.backend.response.CommonResponse;
import app.system.application.backend.service.OrderService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("api")
public class OrderController {

    @Autowired
    OrderService orderService;

    @PostMapping("/v1/orders")
	public ResponseEntity<?> addOrder(@RequestBody OrderDto orderDto){

		try {

			int result = orderService.createOrder(orderDto);

			if(result >= 1 ) {

				return  ResponseEntity.ok(new CommonResponse<Integer>(true,null,result));

			}else {
				return  ResponseEntity.ok(new CommonResponse<Integer>(false,"Cannot add orders ",result));
			}
		}
		catch(Exception e) {
			return  ResponseEntity.ok(new CommonResponse<String>(false,e.getMessage(),"Insert error order"));


		}
	}


	@PutMapping("/v1/orders/{id}")
	public ResponseEntity<?> updateOrderById(@RequestBody OrderDto orderDto,@PathVariable int id) {

		int result = orderService.update(orderDto,id);

		if(result == 1) {

			return ResponseEntity.ok(new CommonResponse<Integer>(true,null,result));

		}else {

			return ResponseEntity.ok(new CommonResponse<Integer>(false,"orders Not Found",result));

		}


	}

	@GetMapping("/v1/orders/{id}")
	public ResponseEntity<?> getOrderById(@PathVariable int id) {

		try {

			OrderDto result = orderService.findById(id);


			return  ResponseEntity.ok(new CommonResponse<OrderDto>(true,null,result));

		}catch(Exception e) {

			return  ResponseEntity.ok(new CommonResponse<OrderDto>(true,"orders not found",null));
		}


	}


	@GetMapping("/v1/orders")
	public  ResponseEntity<?> getOrderList(){

		List<OrderDto> result = orderService.findAll();

		return ResponseEntity.ok(new CommonResponse<List<OrderDto>>(true,null,result));

	}
	
	
	@GetMapping("/v1/orders/delivering")
	public  ResponseEntity<?> getOrderListdelivering(){

		List<OrderDto> result = orderService.findAllDeliveryOrders();

		return ResponseEntity.ok(new CommonResponse<List<OrderDto>>(true,null,result));

	}
	
	@PutMapping("/v1/orders/status")
	public  ResponseEntity<?> approveOrderStatus(@RequestParam("id") int id, @RequestParam("status") int status){
		
		System.out.println(status);

		int result = orderService.approveOrderStatus(id,status);
		
		if(result >= 1) {
			
			return ResponseEntity.ok(new CommonResponse<Integer>(true,null,result));
		}else {
			
			return ResponseEntity.ok(new CommonResponse<Integer>(false,null,result));	
		}


	}


	@DeleteMapping("/v1/orders/{id}")
	public ResponseEntity<?> deleteOrderById(@PathVariable int id) {

		int result = orderService.delete(id);

		if(result == 1) {

			return ResponseEntity.ok(new CommonResponse<Integer>(true,null,result));

		}else {

			return ResponseEntity.ok(new CommonResponse<Integer>(false,"Cannot Delete orders",result));

		}

	}
	

}
