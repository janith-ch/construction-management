package app.system.application.backend.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import app.system.application.backend.model.dto.SiteDto;
import app.system.application.backend.repository.SiteRepository;


@Service
public class SiteService implements SiteInterface {
	
	
	@Autowired
	SiteRepository siteRepository;
	

	@Override
	public int save(SiteDto siteDto) {
		return siteRepository.save(siteDto);
	}

	@Override
	public int update(SiteDto siteDto, int id) {
		return siteRepository.update(siteDto,id);
	}

	@Override
	public List<SiteDto> findAll() {
		return siteRepository.findAll();
	}

	@Override
	public SiteDto findById(int id) {
		return siteRepository.findById(id).get();
	}

	@Override
	public int delete(int id) {
		return siteRepository.delete(id);
	}
	
    


}
