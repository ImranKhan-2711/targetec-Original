package com.tec.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tec.dao.AnswerMasterDao;
import com.tec.model.AnswerMaster;
import com.tec.model.AnswerOptions;
import com.tec.model.StudentTestAnswer;
import com.tec.service.AnswerOptionService;
import com.tec.service.AnswerService;
import com.tec.template.Page;

/**
 * 
 * @author Mandeep
 *
 */
@Service("answerService")
public class AnswerServiceImpl implements AnswerService {

	@Resource(name = "answerMasterDao")
	private AnswerMasterDao answerMasterDao;

	@Resource(name = "answerOptionService")
	protected AnswerOptionService answerOptionService;

	public AnswerMaster updateAnswer(AnswerMaster answerMaster) {
		AnswerMaster answerMasterReturn = new AnswerMaster();
		if (answerMaster.getId() != null && !answerMaster.getId().equals("")) {
			answerMasterReturn = answerMasterDao.update(answerMaster);
		} else {
			answerMasterReturn = findAnswer(answerMasterDao.insert(answerMaster));
		}
		List<AnswerOptions> answerOptionss = answerMaster.getOptions();
		List<AnswerOptions> answerOptionReturn = new ArrayList<AnswerOptions>();
		for (AnswerOptions answerOptions : answerOptionss) {
			answerOptions.setAnswerMaster(answerMasterReturn);
			answerOptions = answerOptionService.updateAnswerOption(answerOptions);
			answerOptionReturn.add(answerOptions);
		}
		answerMasterReturn.setOptions(answerOptionReturn);
		return answerMasterReturn;
	}

	public Page<AnswerMaster> findAnswers(Page<AnswerMaster> page, Long questionId) {
		return answerMasterDao.findAnswers(page, questionId);
	}

	public AnswerMaster findAnswer(Long answerId) {
		AnswerMaster answerMasterReturn = new AnswerMaster();
		answerMasterReturn = answerMasterDao.findAnswer(answerId);
		return answerMasterReturn;
	}

	public Page<StudentTestAnswer> findStudentTestAnswers(Page<StudentTestAnswer> page, Long questionId, Long testId,
			Long testDetailId) {
		return answerMasterDao.findStudentTestAnswers(page, questionId, testId, testDetailId);
	}

}
