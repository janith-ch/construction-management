package app.system.application.backend.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import app.system.application.backend.model.dto.MaterialDto;
import app.system.application.backend.response.CommonResponse;
import app.system.application.backend.service.MaterialService;


@RestController
@CrossOrigin
@RequestMapping("api")
public class MaterialController {
	
	@Autowired
	private MaterialService materialService;

	@PostMapping("/v1/materials")
	public ResponseEntity<?> addMaterial(@RequestBody MaterialDto materialDto){

		try {

			int result = materialService.save(materialDto);

			if(result >= 1 ) {

				return  ResponseEntity.ok(new CommonResponse<Integer>(true,null,result));

			}else {
				return  ResponseEntity.ok(new CommonResponse<Integer>(false,"Cannot Material site",result));
			}
		}
		catch(Exception e) {
			return  ResponseEntity.ok(new CommonResponse<MaterialDto>(false,e.getMessage(),materialDto));


		}
	}


	@PutMapping("/v1/materials/{id}")
	public ResponseEntity<?> updateMaterialById(@RequestBody MaterialDto materialDto,@PathVariable int id) {

		int result = materialService.update(materialDto,id);

		if(result == 1) {

			return ResponseEntity.ok(new CommonResponse<Integer>(true,null,result));

		}else {

			return ResponseEntity.ok(new CommonResponse<Integer>(false,"Material Not Found",result));

		}


	}

	@GetMapping("/v1/materials/{id}")
	public ResponseEntity<?> getMaterialById(@PathVariable int id) {

		try {

			MaterialDto result = materialService.findById(id);


			return  ResponseEntity.ok(new CommonResponse<MaterialDto>(true,null,result));

		}catch(Exception e) {

			return  ResponseEntity.ok(new CommonResponse<MaterialDto>(true,"Material not found",null));
		}


	}


	@GetMapping("/v1/materials")
	public  ResponseEntity<?> getMaterialList(){

		List<MaterialDto> result = materialService.findAll();

		return ResponseEntity.ok(new CommonResponse<List<MaterialDto>>(true,null,result));

	}


	@DeleteMapping("/v1/materials/{id}")
	public ResponseEntity<?> deleteMaterialById(@PathVariable int id) {

		int result = materialService.delete(id);

		if(result == 1) {

			return ResponseEntity.ok(new CommonResponse<Integer>(true,null,result));

		}else {

			return ResponseEntity.ok(new CommonResponse<Integer>(false,"Cannot Delete Material",result));

		}

	}

}

