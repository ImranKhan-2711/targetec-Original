package com.tec.dao;

import javax.inject.Inject;

import org.springframework.jdbc.core.JdbcTemplate;

public class NeuroGenericDAO {

	protected final JdbcTemplate jdbcTemplate;

	public NeuroGenericDAO() {
		this.jdbcTemplate = null;
	}

	@Inject
	public NeuroGenericDAO(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

}
