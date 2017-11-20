package com.tec.service;

import java.util.List;

import com.tec.model.ResponseBean;
import com.tec.model.StudentTestDetail;
import com.tec.model.TestMaster;
import com.tec.template.Page;

public interface TestService {

	public Page<TestMaster> listTest(Page<TestMaster> page, String testName, String description, String typeName,
			String categoryName);

	public TestMaster getTest(Long id);

	public TestMaster insert(TestMaster testMaster);

	public TestMaster update(TestMaster testMaster);

	public Page<TestMaster> listTestAssignToStudent(String userName, Page<TestMaster> page, String testName,
			String description, String typeName, String categoryName);

	public Page<StudentTestDetail> listTestDetail(String testTakenDate, String testName, String totalQuestion,
			String correct, String incorrect, String score, String percentage, Page<StudentTestDetail> page,
			String userName);

	public List<StudentTestDetail> listTestDetailByTestId(Long testId, String userName);

	public StudentTestDetail listTestDetailByTestDetailId(Long testDetailId);

	public int getTestCount(String testName);

	public ResponseBean delete(Long id);
}
