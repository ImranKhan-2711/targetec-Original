package com.tec.dao;

import java.util.List;

import com.tec.model.StudentTestAnswer;

public interface StudentTestAnswerDao {
	
	public Boolean insertAnswers(List<StudentTestAnswer> answers);
	
	public List<StudentTestAnswer> listStudentAnswers(Long testDetailId, Long questionId);

	public int getStudentTestAnswerWithQuestionId(Long id);
}
