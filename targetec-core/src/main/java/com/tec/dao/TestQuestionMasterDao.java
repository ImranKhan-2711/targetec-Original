package com.tec.dao;

import java.util.List;

import com.tec.model.TestQuestion;

public interface TestQuestionMasterDao {

	public void delete(Long testMasterId);

	public TestQuestion insert(TestQuestion testQuestion);
	
	public List<TestQuestion> findList(Long testId);
	
	public int getTestQuestionByQuestionId(Long questionId);
}
