package com.tec.dao.impl;

import javax.inject.Inject;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.tec.dao.NeuroGenericDAO;
import com.tec.dao.TestStudentMasterDao;
import com.tec.model.StudentTest;

@Repository("testStudentMasterDao")
public class TestStudentMasterDaoImpl extends NeuroGenericDAO implements TestStudentMasterDao {

	public TestStudentMasterDaoImpl() {

	}

	@Inject
	public TestStudentMasterDaoImpl(JdbcTemplate jdbcTemplate) {
		super(jdbcTemplate);
	}

	public StudentTest insert(StudentTest studentTest) {
		String insertSQL = "INSERT into student_test (account_id,test_id, status) VALUES (?,?,?)";
		jdbcTemplate.update(insertSQL,
				new Object[] { studentTest.getAccount().getId(), studentTest.getTestMaster().getId(),"ACTIVE" });
		studentTest.setId(jdbcTemplate.queryForLong("select max(id) from student_test"));
		return studentTest;
	}
	
	public int getStudentTestAnsCount(String id) {
		String selSql = "SELECT count(*) FROM student_test_answer where test_id=?";
		return jdbcTemplate.queryForInt(selSql, new Object[] { id });
	}
	public int getStudentTestDetailCount(String id) {
		String selSql = "SELECT count(*) FROM student_test_detail where test_id=?";
		return jdbcTemplate.queryForInt(selSql, new Object[] { id });
	}

	public int getStudentTestCount(String id) {
		String selSql = "SELECT count(*) FROM student_test where test_id=?";
		return jdbcTemplate.queryForInt(selSql, new Object[] { id });
	}
}
