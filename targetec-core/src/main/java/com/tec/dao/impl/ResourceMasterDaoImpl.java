package com.tec.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.inject.Inject;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.stereotype.Repository;

import com.tec.dao.NeuroGenericDAO;
import com.tec.dao.ResourceMasterDao;
import com.tec.model.ResourceMaster;
import com.tec.template.Page;
import com.tec.template.PaginationHelper;

@Repository("resourceMasterDao")
public class ResourceMasterDaoImpl extends NeuroGenericDAO implements ResourceMasterDao {

	public ResourceMasterDaoImpl() {

	}

	@Inject
	public ResourceMasterDaoImpl(JdbcTemplate jdbcTemplate) {
		super(jdbcTemplate);
	}

	public Long insert(ResourceMaster resourceMaster) {
		String insertSQL = "INSERT into resource_master (resource_type,resource_ext,resource_path,table_name,column_name,status) VALUES (?,?,?,?,?,?)";
		jdbcTemplate.update(insertSQL,
				new Object[] { resourceMaster.getResourceType(), resourceMaster.getResourceExt(),
						resourceMaster.getResourcePath(), resourceMaster.getTableName(), resourceMaster.getColumnName(),
						"ACTIVE" });
		return jdbcTemplate.queryForLong("select max(id) from resource_master");
	}

	public ResourceMaster getResourceMaster(Long id) {
		PaginationHelper<ResourceMaster> ph = new PaginationHelper<ResourceMaster>();
		Page<ResourceMaster> page = new Page<ResourceMaster>("");
		String selSql = "SELECT id,resource_type,resource_ext,resource_path,table_name,column_name,status FROM resource_master where id=?";
		String countSQL = "select count(*) from (" + selSql + ") c";
		return ph.fetchPage(jdbcTemplate, countSQL, selSql, new Object[] { id }, page, new ResourceMasterMapper())
				.getPageItems().get(0);
	}

	private static final class ResourceMasterMapper implements ParameterizedRowMapper<ResourceMaster> {

		public ResourceMaster mapRow(ResultSet rs, int rowNum) throws SQLException {

			return new ResourceMaster(rs.getLong(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
					rs.getString(6), rs.getString(7));
		}
	}
}
