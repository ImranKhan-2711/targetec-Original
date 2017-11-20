package com.tec.dao.impl;

import java.util.Date;

import javax.inject.Inject;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.tec.dao.NeuroGenericDAO;
import com.tec.dao.StudentTestDetailDao;
import com.tec.model.StudentTestDetail;
@Repository("studentTestDetailDao")
public class StudentTestDetailDaoImpl extends NeuroGenericDAO implements StudentTestDetailDao {

	public StudentTestDetailDaoImpl() {

	}

	@Inject
	public StudentTestDetailDaoImpl(JdbcTemplate jdbcTemplate) {
		super(jdbcTemplate);
	}

	public StudentTestDetail insert(StudentTestDetail studentTestDetail) {
		String insertSQL="INSERT INTO student_test_detail (test_id,account_id,performed_on,attempted,corrected,uncorrected) VALUES (?,?,?,?,?,?)";
		jdbcTemplate.update(insertSQL,new Object[]{studentTestDetail.getTestMaster().getId(),studentTestDetail.getAccount().getId(),new Date(),0,0,0});
		studentTestDetail.setId(jdbcTemplate.queryForObject("select max(id) from student_test_detail", Long.class));
		return studentTestDetail;
	}

	public StudentTestDetail update(StudentTestDetail studentTestDetail) {
		String updateSQL="update student_test_detail set attempted=?, corrected=?, uncorrected=? where id=? ";
		jdbcTemplate.update(updateSQL, new Object[]{studentTestDetail.getAttempted(),studentTestDetail.getCorrected(),studentTestDetail.getUncorrected(),studentTestDetail.getId()});
		return studentTestDetail;
	}

	public int getStudentTestWithAccount(Long acctId) {
		return jdbcTemplate.queryForInt("SELECT count(*) FROM  student_test  WHERE  account_id='" + acctId + "'");
	}

	public int getStudentTestDetailWithAccount(Long acctId) {
		return jdbcTemplate.queryForInt("SELECT count(*) FROM  student_test_detail  WHERE  account_id='" + acctId + "'");
	}

}
