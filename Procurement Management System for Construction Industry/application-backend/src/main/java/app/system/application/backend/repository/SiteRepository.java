package app.system.application.backend.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import app.system.application.backend.constant.SiteConstant;
import app.system.application.backend.model.dto.SiteDto;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class SiteRepository {
	
	@Autowired
	SiteConstant siteConstant;
	
	@Autowired
	@Qualifier("system-jdbc-template")
	JdbcTemplate jdbcTemplate;

	public int save(SiteDto siteDto) {
		
		KeyHolder keyHolder = new GeneratedKeyHolder();

		int result = jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(siteConstant.getINSERT_SQL(), new String[] { "id" });                                                                  

				ps.setString(1,siteDto.getName());
				ps.setString(2,siteDto.getAddress());
				ps.setString(3,siteDto.getContactNumber());
				ps.setInt(4,siteDto.getSiteManagerId());
				ps.setInt(5,siteDto.getIsActive());

				return ps;
			}
		},keyHolder);


		return keyHolder.getKey().intValue();
	}

	public int update(SiteDto siteDto, int id) {
		try {

			return  jdbcTemplate.update(siteConstant.getUPDATE_SQL(),
					siteDto.getName(),
					siteDto.getAddress(),
					siteDto.getContactNumber(),
					siteDto.getSiteManagerId(),
					siteDto.getIsActive(),id);

		}catch (Exception e){
			log.info("update site error "+e);
			return 0;

		}
	}

	public List<SiteDto> findAll() {
		return jdbcTemplate.query(siteConstant.getFIND_ALL(),
				(rs, rowNum) ->
		new SiteDto(
				rs.getInt("id"),
				rs.getString("name"),
				rs.getString("address"),
				rs.getInt("is_active"),
				rs.getString("contact_number"),
				rs.getInt("site_manager_id")
				
				)
				);
	}

	@SuppressWarnings("deprecation")
	public Optional<SiteDto> findById(int id) {
		return jdbcTemplate.queryForObject(siteConstant.getFIND_BY_ID(),
				new Object[]{id},
				(rs, rowNum) ->
		Optional.of(new SiteDto(
				rs.getInt("id"),
				rs.getString("name"),
				rs.getString("address"),
				rs.getInt("is_active"),
				rs.getString("contact_number"),
				rs.getInt("site_manager_id")
				)
				));

	}

	public int delete(int id) {
		try {
			return jdbcTemplate.update(siteConstant.getDELETE(),
					id);
		}catch (Exception e) {
			log.info("catch execption in site delete " + e);
			return 0;
		}
	}

	
	

}
