package com.tec.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.inject.Inject;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.stereotype.Repository;

import com.tec.dao.AnswerMasterDao;
import com.tec.dao.AnswerOptionDao;
import com.tec.dao.NeuroGenericDAO;
import com.tec.model.AnswerMaster;
import com.tec.model.AnswerOptions;
import com.tec.model.QuestionMaster;
import com.tec.template.Page;
import com.tec.template.PaginationHelper;

@Repository("answerOptionDao")
public class AnswerOptionDaoImpl extends NeuroGenericDAO implements AnswerOptionDao {

	public AnswerOptionDaoImpl() {

	}

	@Inject
	public AnswerOptionDaoImpl(JdbcTemplate jdbcTemplate) {
		super(jdbcTemplate);
	}

	public Long insert(AnswerOptions answerOptions) {
		String insertSQL = "INSERT INTO answer_options (answer_id,ans_option) VALUES (?,?)";
		jdbcTemplate.update(insertSQL,
				new Object[] { answerOptions.getAnswerMaster().getId(), answerOptions.getAnsOption() });
		return jdbcTemplate.queryForLong("select max(id) from answer_options");
	}

	public AnswerOptions update(AnswerOptions answerOptions) {
		String updateSQL = "UPDATE answer_options SET ans_option=? where id=?";
		jdbcTemplate.update(updateSQL, new Object[] { answerOptions.getAnsOption(), answerOptions.getId() });
		return answerOptions;

	}

	public Page<AnswerOptions> findAnswerOptions(Page<AnswerOptions> page, Long answerId) {
		PaginationHelper<AnswerOptions> ph = new PaginationHelper<AnswerOptions>();

		String selSQL = "SELECT ao.id, ao.ans_option FROM answer_options ao WHERE 1=1 ";

		if (answerId != null && !answerId.equals("")) {
			selSQL += " and ao.answer_id = '" + answerId + "'";
		}
		String countSQL = "select count(*) from (" + selSQL + ") c";
		return ph.fetchPage(jdbcTemplate, countSQL, selSQL, new String[] {}, page, new AnswerOptionsMapper());
	}

	private static final class AnswerOptionsMapper implements ParameterizedRowMapper<AnswerOptions> {

		public AnswerOptions mapRow(ResultSet rs, int rowNum) throws SQLException {

			return new AnswerOptions(rs.getLong(1), null, rs.getString(2));
		}
	}

}
