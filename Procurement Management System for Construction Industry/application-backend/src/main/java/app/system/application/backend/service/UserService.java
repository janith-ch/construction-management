package app.system.application.backend.service;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import app.system.application.backend.model.dto.UserDto;
import app.system.application.backend.repository.UserRepository;

@Service
public class UserService implements UserInterface {

	@Autowired
	public UserRepository userRepository;


	@Override
	public int save(UserDto userDto) {
		
		userDto.setIsActive(true);

		return userRepository.saveUser(userDto);
	}


	@Override
	public int update(UserDto userDto,int id) {
		return userRepository.updateUser(userDto, id) ;
	}


	@Override
	public List<UserDto> findAll() {

		return userRepository.findAll();
	}


	@Override
	public UserDto findById(int id) {
		return userRepository.findById(id).get();
	}


	@Override
	public int delete(int id) {
		return userRepository.delete(id);
	}


	@Override
	public List<UserDto> getAllSuppliers() {
		return userRepository.getAllSuppliers();
	}

}
