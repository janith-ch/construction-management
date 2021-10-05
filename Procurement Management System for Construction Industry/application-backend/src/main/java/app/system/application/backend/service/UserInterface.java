package app.system.application.backend.service;

import java.util.List;

import app.system.application.backend.model.dto.UserDto;

public interface UserInterface {
	
	int save(UserDto userDto);
	
	int update(UserDto userDto,int id);
	
	List<UserDto> findAll();
	
	UserDto findById(int id);
	
	int delete(int id);
	
	List<UserDto> getAllSuppliers();

}
