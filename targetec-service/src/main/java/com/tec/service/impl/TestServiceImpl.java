package com.tec.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tec.dao.TestMasterDao;
import com.tec.model.ResponseBean;
import com.tec.model.StudentTestDetail;
import com.tec.model.TestMaster;
import com.tec.service.TestService;
import com.tec.service.TestStudentService;
import com.tec.template.Page;

@Service("testService")
public class TestServiceImpl implements TestService {

	@Resource(name = "testMasterDao")
	private TestMasterDao testMasterDao;

	@Resource(name = "testStudentService")
	private TestStudentService testStudentService;

	public Page<TestMaster> listTest(Page<TestMaster> page, String testName, String description, String typeName,
			String categoryName) {
		return testMasterDao.listTest(page, testName, description, typeName, categoryName);
	}

	public TestMaster getTest(Long id) {
		return testMasterDao.getTest(id);
	}

	public TestMaster insert(TestMaster testMaster) {
		return testMasterDao.insert(testMaster);
	}

	public TestMaster update(TestMaster testMaster) {
		return testMasterDao.update(testMaster);
	}

	public Page<TestMaster> listTestAssignToStudent(String userName, Page<TestMaster> page, String testName,
			String description, String typeName, String categoryName) {
		return testMasterDao.listTestAssignToStudent(userName, page, testName, description, typeName, categoryName);
	}

	public Page<StudentTestDetail> listTestDetail(String testTakenDate, String testName, String totalQuestion,
			String correct, String incorrect, String score, String percentage, Page<StudentTestDetail> page,
			String userName) {
		return testMasterDao.listTestDetail(testTakenDate, testName, totalQuestion, correct, incorrect, score,
				percentage, page, userName);
	}

	public List<StudentTestDetail> listTestDetailByTestId(Long testId, String userName) {
		return testMasterDao.listTestDetailByTestId(testId, userName);
	}

	public StudentTestDetail listTestDetailByTestDetailId(Long testDetailId) {
		return testMasterDao.listTestDetailByTestDetailId(testDetailId);
	}

	public int getTestCount(String testName) {
		return testMasterDao.getTestCount(testName);
	}

	public ResponseBean delete(Long id) {
		int studentTestAnser = testStudentService.getStudentTestAnsCount(id.toString());
		int studentTestDetail = testStudentService.getStudentTestDetailCount(id.toString());
		int studentTest = testStudentService.getStudentTestCount(id.toString());
		String message = null;
		ResponseBean responseBean = new ResponseBean();
		if (studentTestAnser > 0) {
			message = "Can't delete,Test Assigned to any student";
			responseBean.setValid(false);
			responseBean.setMessage(message);
		} else if (studentTestDetail > 0) {
			message = "Can't delete,Test Assigned to any student";
			responseBean.setValid(false);
			responseBean.setMessage(message);
		} else if (studentTest > 0) {
			message = "Can't delete,Test Assigned to any student";
			responseBean.setValid(false);
			responseBean.setMessage(message);
		} else {
			responseBean.setValid(true);
			testMasterDao.delete(id);
		}
		return responseBean;
	}

}
