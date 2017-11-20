package com.tec.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.inject.Inject;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.stereotype.Repository;

import com.tec.dao.NeuroGenericDAO;
import com.tec.dao.TestMasterDao;
import com.tec.model.Account;
import com.tec.model.CategoryMaster;
import com.tec.model.ResourceMaster;
import com.tec.model.StudentTestDetail;
import com.tec.model.TestMaster;
import com.tec.model.TypeMaster;
import com.tec.template.Page;
import com.tec.template.PaginationHelper;

@Repository("testMasterDao")
public class TestMasterDaoImpl extends NeuroGenericDAO implements TestMasterDao {

	public TestMasterDaoImpl() {

	}

	@Inject
	public TestMasterDaoImpl(JdbcTemplate jdbcTemplate) {
		super(jdbcTemplate);
	}

	public Page<TestMaster> listTest(Page<TestMaster> page, String testName, String description, String typeName,
			String categoryName) {
		PaginationHelper<TestMaster> ph = new PaginationHelper<TestMaster>();

		String selSQL = "SELECT tm.id, tm.category_id,cm.name category_name, tm.type_id, tpm.name type_name, tm.name, tm.description, tm.image, tm.question_count, tm.duration, tm.status FROM test_master tm,type_master tpm,category_master cm where tpm.id = tm.type_id and cm.id = tm.category_id ";

		if (testName != null && !testName.equals("")) {
			selSQL += " and tm.name like '%" + testName + "%'";
		}
		if (description != null && !description.equals("")) {
			selSQL += " and tm.description like '%" + description + "%'";
		}
		if (typeName != null && !typeName.equals("")) {
			selSQL += " and tpm.name like '%" + typeName + "%'";
		}
		if (categoryName != null && !categoryName.equals("")) {
			selSQL += " and cm.name like '%" + categoryName + "%'";
		}
		System.out.println(jdbcTemplate + "jdbcTemplate\n\n\n");
		String countSQL = "select count(*) from (" + selSQL + ") c";
		return ph.fetchPage(jdbcTemplate, countSQL, selSQL, new String[] {}, page, new TestMasterMapper());
	}

	private static final class TestMasterMapper implements ParameterizedRowMapper<TestMaster> {
		public TestMaster mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new TestMaster(rs.getLong(1), new CategoryMaster(rs.getLong(2), rs.getString(3)),
					new TypeMaster(rs.getLong(4), rs.getString(5)), rs.getString(6), rs.getString(7),
					new ResourceMaster(rs.getLong(8)), rs.getInt(9), rs.getTime(10), rs.getString(11));
		}
	}

	public TestMaster getTest(Long id) {
		PaginationHelper<TestMaster> ph = new PaginationHelper<TestMaster>();
		Page<TestMaster> page = new Page<TestMaster>("");
		String selSQL = "SELECT tm.id, tm.category_id,cm.name category_name, tm.type_id, tpm.name type_name, tm.name, tm.description, tm.image, tm.question_count, tm.duration, tm.status FROM test_master tm,type_master tpm,category_master cm where tpm.id = tm.type_id and cm.id = tm.category_id and tm.id=? ";
		String countSQL = "select count(*) from (" + selSQL + ") c";
		return ph.fetchPage(jdbcTemplate, countSQL, selSQL, new Object[] { id }, page, new TestMasterMapper())
				.getPageItems().get(0);
	}

	public TestMaster insert(TestMaster testMaster) {
		String insertSQL = "INSERT into test_master (category_id, type_id, name, description, image, question_count, duration, status) VALUES (?,?,?,?,?,?,?,?)";
		jdbcTemplate.update(insertSQL,
				new Object[] { testMaster.getCategoryMaster().getId(), testMaster.getTypeMaster().getId(),
						testMaster.getName(), testMaster.getDescription(), testMaster.getImage().getId(),
						testMaster.getQuestionCount(), testMaster.getDuration(), "ACTIVE" });
		testMaster.setId(jdbcTemplate.queryForLong("select max(id) from account"));
		return testMaster;
	}

	public TestMaster update(TestMaster testMaster) {
		String updateSQL = "UPDATE test_master set category_id=?, type_id=?, name=?, description=?, image=?, question_count=?, duration=?, status=? WHERE id=?";
		jdbcTemplate.update(updateSQL,
				new Object[] { testMaster.getCategoryMaster().getId(), testMaster.getTypeMaster().getId(),
						testMaster.getName(), testMaster.getDescription(), testMaster.getImage().getId(),
						testMaster.getQuestionCount(), testMaster.getDuration(), "ACTIVE", testMaster.getId() });
		return testMaster;
	}

	public Page<TestMaster> listTestAssignToStudent(String userName, Page<TestMaster> page, String testName,
			String description, String typeName, String categoryName) {
		PaginationHelper<TestMaster> ph = new PaginationHelper<TestMaster>();
		String selSQL = "SELECT tm.id, tm.category_id,cm.name category_name, tm.type_id, tpm.name type_name, tm.name, tm.description, tm.image, tm.question_count, tm.duration, tm.status FROM test_master tm,type_master tpm,category_master cm where tpm.id = tm.type_id and cm.id = tm.category_id and tm.id in (select stest.test_id from account act,student_test stest where act.id = stest.account_id and act.username='"
				+ userName + "') ";

		if (testName != null && !testName.equals("")) {
			selSQL += " and tm.name like '%" + testName + "%'";
		}
		if (description != null && !description.equals("")) {
			selSQL += " and tm.description like '%" + description + "%'";
		}
		if (typeName != null && !typeName.equals("")) {
			selSQL += " and tpm.name like '%" + typeName + "%'";
		}
		if (categoryName != null && !categoryName.equals("")) {
			selSQL += " and cm.name like '%" + categoryName + "%'";
		}
		System.out.println(jdbcTemplate + "jdbcTemplate\n\n\n");
		String countSQL = "select count(*) from (" + selSQL + ") c";
		return ph.fetchPage(jdbcTemplate, countSQL, selSQL, new String[] {}, page, new TestMasterMapper());
	}

	public Page<StudentTestDetail> listTestDetail(String testTakenDate, String testName, String totalQuestion,
			String correct, String incorrect, String score, String percentage, Page<StudentTestDetail> page,
			String userName) {
		PaginationHelper<StudentTestDetail> ph = new PaginationHelper<StudentTestDetail>();
		String selSQL = "SELECT std.id, tm.id testId,tm.name testName,tm.question_count,std.account_id, std.performed_on, std.consumed_time,"
				+ " std.attempted, std.corrected, std.uncorrected FROM student_test_detail std,test_master tm where tm.id = std.test_id and "
				+ " std.account_id in (select id from account where username = '" + userName + "') and tm.type_id "
				+ " in (select id from type_master where name in ('READING','LISTENING','WRITING') ) ";
		if (testTakenDate != null && !testTakenDate.equals("")) {
			selSQL += " and std.performed_on like '%" + testTakenDate + "%'";
		}
		if (testName != null && !testName.equals("")) {
			selSQL += " and tm.name like '%" + testName + "%'";
		}
		if (totalQuestion != null && !totalQuestion.equals("")) {
			selSQL += " and tm.question_count like '%" + totalQuestion + "%'";
		}
		if (correct != null && !correct.equals("")) {
			selSQL += " and std.corrected like '%" + correct + "%'";
		}
		if (incorrect != null && !incorrect.equals("")) {
			selSQL += " and std.uncorrected like '%" + incorrect + "%'";
		}
		selSQL += "  order by std.performed_on desc ";
		System.out.println(jdbcTemplate + "jdbcTemplate\n\n\n");
		String countSQL = "select count(*) from (" + selSQL + ") c";
		return ph.fetchPage(jdbcTemplate, countSQL, selSQL, new String[] {}, page, new StudentTestDetailMapper());
	}

	private static final class StudentTestDetailMapper implements ParameterizedRowMapper<StudentTestDetail> {
		public StudentTestDetail mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new StudentTestDetail(rs.getLong(1),
					new TestMaster(rs.getLong(2), null, null, rs.getString(3), null, null, rs.getInt(4), null, null),
					new Account(rs.getLong(5)), rs.getDate(6), rs.getTime(7), rs.getInt(8), rs.getInt(9),
					rs.getInt(10));
		}
	}

	public List<StudentTestDetail> listTestDetailByTestId(Long testId, String userName) {
		PaginationHelper<StudentTestDetail> ph = new PaginationHelper<StudentTestDetail>();
		Page<StudentTestDetail> page = new Page<StudentTestDetail>("");
		String selSQL = "SELECT std.id, tm.id testId,tm.name testName,tm.question_count,std.account_id, std.performed_on, std.consumed_time, std.attempted, std.corrected, std.uncorrected FROM student_test_detail std,test_master tm where tm.id = std.test_id and tm.id = '"
				+ testId + "' and std.account_id in (select id from account where username = '" + userName + "') ";
		String countSQL = "select count(*) from (" + selSQL + ") c";
		return ph.fetchPage(jdbcTemplate, countSQL, selSQL, new String[] {}, page, new StudentTestDetailMapper())
				.getPageItems();

	}

	public StudentTestDetail listTestDetailByTestDetailId(Long testDetailId) {
		PaginationHelper<StudentTestDetail> ph = new PaginationHelper<StudentTestDetail>();
		Page<StudentTestDetail> page = new Page<StudentTestDetail>("");
		String selSQL = "SELECT std.id, tm.id testId,tm.name testName,tm.question_count,std.account_id, std.performed_on, std.consumed_time, std.attempted, std.corrected, std.uncorrected FROM student_test_detail std,test_master tm where tm.id = std.test_id and std.id = '"
				+ testDetailId + "' ";
		String countSQL = "select count(*) from (" + selSQL + ") c";
		return ph.fetchPage(jdbcTemplate, countSQL, selSQL, new String[] {}, page, new StudentTestDetailMapper())
				.getPageItems().get(0);
	}

	public int getTestCount(String testName) {
		String selSql = "SELECT count(*) FROM test_master where name=?";
		return jdbcTemplate.queryForInt(selSql, new Object[] { testName });
	}

	public void delete(Long testId) {
		String deleteSQL = "delete from test_master WHERE  id=?";
		jdbcTemplate.update(deleteSQL, new Object[] { testId });

	}

}
