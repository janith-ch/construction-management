package app.system.application.backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.system.application.backend.model.dto.MaterialDto;
import app.system.application.backend.repository.MaterialRepository;

@Service
public class MaterialService implements MaterialInterface {
	
	
	@Autowired
	MaterialRepository materialRepository;

	@Override
	public int save(MaterialDto materialDto) {
		return materialRepository.save(materialDto);
	}

	@Override
	public int update(MaterialDto materialDto, int id) {
		return materialRepository.update(materialDto,id);
	}

	@Override
	public List<MaterialDto> findAll() {
		return materialRepository.findAll();
	}

	@Override
	public MaterialDto findById(int id) {
		return materialRepository.findById(id).get();
	}

	@Override
	public int delete(int id) {
		return materialRepository.delete(id);
	}

	@Override
	public int updateMaterialStock(double quantity,int materialId) {
		
		MaterialDto materialDto = findById(materialId);
		
		double stock = materialDto.getStock();
		
		double newStock = stock - quantity;
		
		return materialRepository.updateStock(newStock,materialId);
	}

}

