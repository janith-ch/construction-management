package app.system.application.backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.system.application.backend.model.Supplier;
import app.system.application.backend.repository.SupplierRepository;
@Service
public class SupplierService implements SupplierInterface {
	
	@Autowired
	SupplierRepository supplierRepository;

	@Override
	public int save(Supplier supplier) {
		
		return supplierRepository.save(supplier);
	}

	@Override
	public int update(Supplier supplier, int id) {
		return supplierRepository.update(supplier,id);
	}

	@Override
	public List<Supplier> findAll() {
		return supplierRepository.findAll();
	}

	@Override
	public Supplier findById(int id) {
		return supplierRepository.findById(id).get();
	}

	@Override
	public int delete(int id) {
		return supplierRepository.delete(id);
	}
	
	

}

