package com.tec.template;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

public class PaginationHelper<E> {
	public Page<E> fetchPage(JdbcTemplate jt, String sqlCountRows,
			String sqlFetchRows, Object[] args, final Page<E> page,
			final ParameterizedRowMapper<E> rowMapper) {
		int rowCount = jt.queryForInt(sqlCountRows, args);

		page.configurePage(rowCount);
		if (page.getPageNumber() > page.getPagesAvailable()) {
			page.setPageNumber(page.getPagesAvailable());
			page.setInterval(page.getPageNumber());
		}
		final int startRow = (page.getPageNumber() - 1) * page.getPageSize();
		jt.query(sqlFetchRows, args, new ResultSetExtractor() {
			public Object extractData(ResultSet rs) throws SQLException,
					DataAccessException {
				List pageItems = page.getPageItems();
				int currentRow = 0;
				while ((rs.next())
						&& (currentRow < startRow + page.getPageSize())) {
					if (currentRow >= startRow) {
						pageItems.add(rowMapper.mapRow(rs, currentRow));
					}
					currentRow++;
				}
				return page;
			}
		});
		return page;
	}
}
