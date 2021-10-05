package app.system.application.backend.service;

import java.util.List;

import app.system.application.backend.model.dto.SiteDto;

public interface SiteInterface {

	int save(SiteDto siteDto);

	int update(SiteDto siteDto,int id);

	List<SiteDto> findAll();

	SiteDto findById(int id);

	int delete(int id);

}
