package com.tec.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.inject.Inject;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.stereotype.Repository;

import com.tec.dao.NeuroGenericDAO;
import com.tec.dao.TestQuestionMasterDao;
import com.tec.model.QuestionMaster;
import com.tec.model.TestMaster;
import com.tec.model.TestQuestion;
import com.tec.template.Page;
import com.tec.template.PaginationHelper;

@Repository("testQuestionMasterDao")
public class TestQuestionMasterDaoImpl extends NeuroGenericDAO implements TestQuestionMasterDao {

	public TestQuestionMasterDaoImpl() {

	}

	@Inject
	public TestQuestionMasterDaoImpl(JdbcTemplate jdbcTemplate) {
		super(jdbcTemplate);
	}

	public void delete(Long testMasterId) {
		String deleteSQL = "delete from test_question  WHERE test_id=?";
		jdbcTemplate.update(deleteSQL, new Object[] { testMasterId });

	}

	public TestQuestion insert(TestQuestion testQuestion) {
		String insertSQL = "INSERT into test_question (test_id, question_id) VALUES (?,?)";
		jdbcTemplate.update(insertSQL,
				new Object[] { testQuestion.getTestMaster().getId(), testQuestion.getQuestionMaster().getId() });
		testQuestion.setId(jdbcTemplate.queryForLong("select max(id) from test_question"));
		return testQuestion;
	}

	public List<TestQuestion> findList(Long testId) {
		PaginationHelper<TestQuestion> ph = new PaginationHelper<TestQuestion>();
		Page<TestQuestion> page = new Page<TestQuestion>("");
		String selSQL = "SELECT id,test_id,question_id from test_question where test_id='" + testId + "' ";

		String countSQL = "select count(*) from (" + selSQL + ") c";
		return ph.fetchPage(jdbcTemplate, countSQL, selSQL, new String[] {}, page, new TestQuestionMapper())
				.getPageItems();
	}

	private static final class TestQuestionMapper implements ParameterizedRowMapper<TestQuestion> {
		public TestQuestion mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new TestQuestion(rs.getLong(1), new TestMaster(rs.getLong(2)), new QuestionMaster(rs.getLong(3)));
		}
	}

	public int getTestQuestionByQuestionId(Long quesId) {
			return jdbcTemplate.queryForInt("SELECT count(*) FROM  test_question  WHERE  question_id='" + quesId + "'");

	}
}
