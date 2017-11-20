package com.tec.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.inject.Inject;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.stereotype.Repository;

import com.tec.dao.AnswerMasterDao;
import com.tec.dao.NeuroGenericDAO;
import com.tec.model.AnswerMaster;
import com.tec.model.StudentTestAnswer;
import com.tec.template.Page;
import com.tec.template.PaginationHelper;

@Repository("answerMasterDao")
public class AnswerMasterDaoImpl extends NeuroGenericDAO implements AnswerMasterDao {

	public AnswerMasterDaoImpl() {

	}

	@Inject
	public AnswerMasterDaoImpl(JdbcTemplate jdbcTemplate) {
		super(jdbcTemplate);
	}

	public Long insert(AnswerMaster answerMaster) {
		String insertSQL = "INSERT INTO answer_master (question_id,seq_no,answer,correct_y_n) VALUES (?,?,?,?)";
		jdbcTemplate.update(insertSQL, new Object[] { answerMaster.getQuestionMaster().getId(), answerMaster.getSeqNo(),
				answerMaster.getAnswer(), answerMaster.getCorrectYN() });
		return jdbcTemplate.queryForLong("select max(id) from answer_master");
	}

	public AnswerMaster update(AnswerMaster answerMaster) {
		String updateSQL = "UPDATE answer_master SET seq_no=?,answer=?,correct_y_n=?,description=? where id=?";
		jdbcTemplate.update(updateSQL, new Object[] { answerMaster.getSeqNo(), answerMaster.getAnswer(),
				answerMaster.getCorrectYN(), answerMaster.getDescription(), answerMaster.getId() });
		return answerMaster;

	}

	public Page<AnswerMaster> findAnswers(Page<AnswerMaster> page, Long questionId) {
		PaginationHelper<AnswerMaster> ph = new PaginationHelper<AnswerMaster>();

		String selSQL = "SELECT am.id, am.seq_no, am.answer, am.correct_y_n,am.description FROM answer_master am WHERE 1=1 ";

		if (questionId != null && !questionId.equals("")) {
			selSQL += " and am.question_id = '" + questionId + "'";
		}
		String countSQL = "select count(*) from (" + selSQL + ") c";
		return ph.fetchPage(jdbcTemplate, countSQL, selSQL, new String[] {}, page, new AnswerMasterMapper());
	}

	public Page<StudentTestAnswer> findStudentTestAnswers(Page<StudentTestAnswer> page, Long questionId, Long testId,
			Long testDetailId) {
		PaginationHelper<StudentTestAnswer> ph = new PaginationHelper<StudentTestAnswer>();

		String selSQL = "SELECT sta.id, sta.answer_seq_no, sta.text_answer, sta.correct_y_n FROM student_test_answer sta WHERE 1=1 ";

		if (questionId != null && !questionId.equals("")) {
			selSQL += " and sta.question_id = '" + questionId + "'";
		}
		if (testId != null && !testId.equals("")) {
			selSQL += " and sta.test_id = '" + testId + "'";
		}
		if (testDetailId != null && !testDetailId.equals("")) {
			selSQL += " and sta.test_detail_id = '" + testDetailId + "'";
		}
		String countSQL = "select count(*) from (" + selSQL + ") c";
		return ph.fetchPage(jdbcTemplate, countSQL, selSQL, new String[] {}, page, new StudentTestAnswerMapper());
	}

	public AnswerMaster findAnswer(Long answerId) {
		PaginationHelper<AnswerMaster> ph = new PaginationHelper<AnswerMaster>();
		Page<AnswerMaster> page = new Page<AnswerMaster>("");
		String selSQL = "SELECT am.id, am.seq_no, am.answer, am.correct_y_n,am.description FROM answer_master am WHERE 1=1 and  am.id = '"
				+ answerId + "'";

		String countSQL = "select count(*) from (" + selSQL + ") c";
		return ph.fetchPage(jdbcTemplate, countSQL, selSQL, new String[] {}, page, new AnswerMasterMapper())
				.getPageItems().get(0);
	}

	private static final class AnswerMasterMapper implements ParameterizedRowMapper<AnswerMaster> {

		public AnswerMaster mapRow(ResultSet rs, int rowNum) throws SQLException {

			return new AnswerMaster(rs.getLong(1), null, rs.getString(2), rs.getString(3), rs.getString(4),
					rs.getString(5));
		}
	}

	private static final class StudentTestAnswerMapper implements ParameterizedRowMapper<StudentTestAnswer> {
		public StudentTestAnswer mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new StudentTestAnswer(rs.getLong(1), rs.getInt(2), rs.getString(3), rs.getString(4));
		}
	}
}
