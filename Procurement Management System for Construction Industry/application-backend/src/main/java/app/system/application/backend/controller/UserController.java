package app.system.application.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import app.system.application.backend.model.dto.UserDto;
import app.system.application.backend.response.CommonResponse;
import app.system.application.backend.service.UserService;

@CrossOrigin
@RestController
@RequestMapping("api")
public class UserController {
	
	@Autowired
	private UserService userService;

	@PostMapping("/v1/users")
	public ResponseEntity<?> addUsers(@RequestBody UserDto userDto){

		try {

			int result = userService.save(userDto);

			if(result >= 1 ) {

				return  ResponseEntity.ok(new CommonResponse<Integer>(true,null,result));

			}else {
				return  ResponseEntity.ok(new CommonResponse<Integer>(false,"Cannot add user",result));
			}
		}
		catch(Exception e) {
			return  ResponseEntity.ok(new CommonResponse<UserDto>(false,e.getMessage(),userDto));


		}
	}


	@PutMapping("/v1/users/{id}")
	public ResponseEntity<?> updateUserById(@RequestBody UserDto userDto,@PathVariable int id) {

		int result = userService.update(userDto,id);

		if(result == 1) {

			return ResponseEntity.ok(new CommonResponse<Integer>(true,null,result));

		}else {

			return ResponseEntity.ok(new CommonResponse<Integer>(false,"User Not Found",result));

		}


	}

	@GetMapping("/v1/users/{id}")
	public ResponseEntity<?> getUsersById(@PathVariable int id) {

		try {

			UserDto result = userService.findById(id);


			return  ResponseEntity.ok(new CommonResponse<UserDto>(true,null,result));

		}catch(Exception e) {

			return  ResponseEntity.ok(new CommonResponse<UserDto>(true,"user not found",null));
		}


	}


	@GetMapping("/v1/users")
	public  ResponseEntity<?> getUsersList(){

		List<UserDto> result = userService.findAll();

		return ResponseEntity.ok(new CommonResponse<List<UserDto>>(true,null,result));

	}

	// get All suppliers 
	@GetMapping("/v1/users/supplier")
	public  ResponseEntity<?> getSupplierList(){

		List<UserDto> result = userService.getAllSuppliers();

		return ResponseEntity.ok(new CommonResponse<List<UserDto>>(true,null,result));

	}


	@DeleteMapping("/v1/user/{id}")
	public ResponseEntity<?> deleteUserById(@PathVariable int id) {

		int result = userService.delete(id);

		if(result == 1) {

			return ResponseEntity.ok(new CommonResponse<Integer>(true,null,result));

		}else {

			return ResponseEntity.ok(new CommonResponse<Integer>(false,"Cannot Delete user",result));

		}

	}
	
	
}
