package com.tec.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.inject.Inject;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.stereotype.Repository;

import com.tec.dao.NeuroGenericDAO;
import com.tec.dao.StudentTestAnswerDao;
import com.tec.model.ResourceMaster;
import com.tec.model.StudentTestAnswer;
import com.tec.template.Page;
import com.tec.template.PaginationHelper;

@Repository("studentTestAnswerDao")
public class StudentTestAnswerDaoImpl extends NeuroGenericDAO implements StudentTestAnswerDao {

	public StudentTestAnswerDaoImpl() {

	}

	@Inject
	public StudentTestAnswerDaoImpl(JdbcTemplate jdbcTemplate) {
		super(jdbcTemplate);
	}

	public Boolean insertAnswers(List<StudentTestAnswer> answers) {
		String insertSQL = "INSERT INTO student_test_answer (test_id, test_detail_id, question_id, answer_seq_no, text_answer, media_answer,correct_y_n) VALUES (?,?, ?, ?, ?, ?,?)";

		for (StudentTestAnswer answer : answers) {
			jdbcTemplate.update(insertSQL, new Object[] { answer.getStudentTestDetail().getTestMaster().getId(),
					answer.getStudentTestDetail().getId(), answer.getQuestionMaster().getId(), answer.getAnsSeqNo(),
					answer.getTextAnswer(), answer.getMediaAnswer(), answer.getCorrectYN() });
		}
		return true;
	}

	public List<StudentTestAnswer> listStudentAnswers(Long testDetailId, Long questionId) {

		PaginationHelper<StudentTestAnswer> ph = new PaginationHelper<StudentTestAnswer>();
		Page<StudentTestAnswer> page = new Page<StudentTestAnswer>("answer_seq_no");

		String selSQL = "SELECT answer_seq_no, text_answer, media_answer,correct_y_n FROM student_test_answer WHERE test_detail_id=? and question_id=? order by answer_seq_no";
		String countSQL = "SELECT count(*) from (" + selSQL + ") t";
		return ph.fetchPage(jdbcTemplate, countSQL, selSQL, new Object[] { testDetailId, questionId }, page,
				new StudentAnswerMapper()).getPageItems();
	}

	private static final class StudentAnswerMapper implements ParameterizedRowMapper<StudentTestAnswer> {

		public StudentTestAnswer mapRow(ResultSet rs, int rowNum) throws SQLException {

			return new StudentTestAnswer(null, null, null, null, rs.getInt(1), rs.getString(2), rs.getLong(3),
					rs.getString(4));
		}
	}

	public int getStudentTestAnswerWithQuestionId(Long id) {
		return jdbcTemplate.queryForInt("SELECT count(*) FROM  student_test_answer  WHERE  question_id='" + id + "'");
	}

}
