package app.system.application.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import app.system.application.backend.model.Supplier;
import app.system.application.backend.response.CommonResponse;
import app.system.application.backend.service.SupplierService;

@RestController
@CrossOrigin
@RequestMapping("api")
public class SupplierController {
	
	@Autowired
	private SupplierService supplierService;

	@PostMapping("/v1/suppliers")
	public ResponseEntity<?> addSupplier(@RequestBody Supplier supplier){

		try {

			int result = supplierService.save(supplier);

			if(result >= 1 ) {

				return  ResponseEntity.ok(new CommonResponse<Integer>(true,null,result));

			}else {
				return  ResponseEntity.ok(new CommonResponse<Integer>(false,"Cannot add Supplier",result));
			}
		}
		catch(Exception e) {
			return  ResponseEntity.ok(new CommonResponse<Supplier>(false,e.getMessage(),supplier));


		}
	}


	@PutMapping("/v1/suppliers/{id}")
	public ResponseEntity<?> updateSupplierById(@RequestBody Supplier supplier,@PathVariable int id) {

		int result = supplierService.update(supplier,id);

		if(result == 1) {

			return ResponseEntity.ok(new CommonResponse<Integer>(true,null,result));

		}else {

			return ResponseEntity.ok(new CommonResponse<Integer>(false,"Supplier Not Found",result));

		}


	}

	@GetMapping("/v1/suppliers/{id}")
	public ResponseEntity<?> getSupplierById(@PathVariable int id) {

		try {

			Supplier result = supplierService.findById(id);


			return  ResponseEntity.ok(new CommonResponse<Supplier>(true,null,result));

		}catch(Exception e) {

			return  ResponseEntity.ok(new CommonResponse<Supplier>(true,"Supplier not found",null));
		}


	}


	@GetMapping("/v1/suppliers")
	public  ResponseEntity<?> getSupplierList(){

		List<Supplier> result = supplierService.findAll();

		return ResponseEntity.ok(new CommonResponse<List<Supplier>>(true,null,result));

	}


	@DeleteMapping("/v1/suppliers/{id}")
	public ResponseEntity<?> deleteSupplierById(@PathVariable int id) {

		int result = supplierService.delete(id);

		if(result == 1) {

			return ResponseEntity.ok(new CommonResponse<Integer>(true,null,result));

		}else {

			return ResponseEntity.ok(new CommonResponse<Integer>(false,"Cannot Delete Supplier",result));

		}

	}
	

}
