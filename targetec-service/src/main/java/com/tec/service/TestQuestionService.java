package com.tec.service;

import java.util.List;

import com.tec.model.TestQuestion;

public interface TestQuestionService {

	public void delete(Long testMasterId);

	public TestQuestion insert(TestQuestion testQuestion);

	public List<TestQuestion> findList(Long testId);
	
	public int getTestQuestionByQuestionId(Long questionId);
}
