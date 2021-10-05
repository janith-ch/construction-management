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
import org.springframework.web.bind.annotation.RestController;
import app.system.application.backend.model.dto.SiteDto;
import app.system.application.backend.response.CommonResponse;
import app.system.application.backend.service.SiteService;


@RestController
@RequestMapping("api")
public class SiteController {
	
	@Autowired
	private SiteService siteService;

	@PostMapping("/v1/sites")
	public ResponseEntity<?> addSite(@RequestBody SiteDto siteDto){

		try {

			int result = siteService.save(siteDto);

			if(result >= 1 ) {

				return  ResponseEntity.ok(new CommonResponse<Integer>(true,null,result));

			}else {
				return  ResponseEntity.ok(new CommonResponse<Integer>(false,"Cannot add site",result));
			}
		}
		catch(Exception e) {
			return  ResponseEntity.ok(new CommonResponse<SiteDto>(false,e.getMessage(),siteDto));


		}
	}


	@PutMapping("/v1/sites/{id}")
	public ResponseEntity<?> updateSitesById(@RequestBody SiteDto siteDto,@PathVariable int id) {

		int result = siteService.update(siteDto,id);

		if(result == 1) {

			return ResponseEntity.ok(new CommonResponse<Integer>(true,null,result));

		}else {

			return ResponseEntity.ok(new CommonResponse<Integer>(false,"site Not Found",result));

		}


	}

	@GetMapping("/v1/sites/{id}")
	public ResponseEntity<?> getSitesById(@PathVariable int id) {

		try {

			SiteDto result = siteService.findById(id);


			return  ResponseEntity.ok(new CommonResponse<SiteDto>(true,null,result));

		}catch(Exception e) {

			return  ResponseEntity.ok(new CommonResponse<SiteDto>(true,"site not found",null));
		}


	}


	@GetMapping("/v1/sites")
	public  ResponseEntity<?> getSitesList(){

		List<SiteDto> result = siteService.findAll();

		return ResponseEntity.ok(new CommonResponse<List<SiteDto>>(true,null,result));

	}


	@DeleteMapping("/v1/sites/{id}")
	public ResponseEntity<?> deleteSitesById(@PathVariable int id) {

		int result = siteService.delete(id);

		if(result == 1) {

			return ResponseEntity.ok(new CommonResponse<Integer>(true,null,result));

		}else {

			return ResponseEntity.ok(new CommonResponse<Integer>(false,"Cannot Delete sites",result));

		}

	}
	

}
