package app.system.application.backend.service;

import java.util.List;

import app.system.application.backend.model.dto.MaterialDto;


public interface MaterialInterface {
	
	int save(MaterialDto materialDto);

	int update(MaterialDto materialDto,int id);

	List<MaterialDto> findAll();

	MaterialDto findById(int id);

	int delete(int id);
	
	int updateMaterialStock(double quantity,int materialId);

}
