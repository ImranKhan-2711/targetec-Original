package com.tec.dao;

import com.tec.model.AnswerMaster;
import com.tec.model.StudentTestAnswer;
import com.tec.template.Page;

public interface AnswerMasterDao {

	public Long insert(AnswerMaster answerMaster);

	public AnswerMaster update(AnswerMaster answerMaster);

	public Page<AnswerMaster> findAnswers(Page<AnswerMaster> page, Long questionId);

	public AnswerMaster findAnswer(Long answerId);

	public Page<StudentTestAnswer> findStudentTestAnswers(Page<StudentTestAnswer> page, Long questionId, Long testId,
			Long testDetailId);
}
