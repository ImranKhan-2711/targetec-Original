package com.tec.service;

import java.util.List;

import com.tec.model.QuestionMaster;
import com.tec.model.StudentTest;
import com.tec.model.StudentTestAnswer;
import com.tec.model.StudentTestDetail;

public interface TestStudentService {

	public StudentTest insert(StudentTest studentTest);
	
	public List<QuestionMaster> listTestQuestions(Long testId);
	
	public StudentTestDetail insertTestDetail(StudentTestDetail studentTestDetail);
	
	public Boolean insertStudentAnswers(List<StudentTestAnswer> answers);
	
	public List<StudentTestAnswer> getStudentAnswers(Long testDetailId, Long questionId);
	
	public StudentTestDetail processDragAndCorrect(StudentTestDetail studentTestDetail,
			Long questionId,Long qstyleId);
	public StudentTestDetail updateTestDetail(StudentTestDetail studentTestDetail);
	
	public List<QuestionMaster> listTestQuestionsByDetailId(Long testDetailId);
	
	public int getStudentTestWithAccount(Long acctId);
	
	public int getStudentTestDetailWithAccount(Long acctId);
	
	public int getStudentTestAnswerWithQuestionId(Long id);
	
public int getStudentTestAnsCount(String id);
	
	public int getStudentTestDetailCount(String id);
	
	public int getStudentTestCount(String id);
}
