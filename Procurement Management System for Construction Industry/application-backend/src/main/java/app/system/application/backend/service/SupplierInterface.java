package app.system.application.backend.service;

import java.util.List;

import app.system.application.backend.model.Supplier;

public interface SupplierInterface {


	int save(Supplier supplier);

	int update(Supplier supplier,int id);

	List<Supplier> findAll();

	Supplier findById(int id);

	int delete(int id);
}
