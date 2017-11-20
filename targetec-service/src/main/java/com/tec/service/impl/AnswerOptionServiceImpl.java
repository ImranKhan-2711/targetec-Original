package com.tec.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tec.dao.AnswerOptionDao;
import com.tec.model.AnswerOptions;
import com.tec.service.AnswerOptionService;
import com.tec.template.Page;

/**
 * 
 * @author Mandeep
 *
 */
@Service("answerOptionService")
public class AnswerOptionServiceImpl implements AnswerOptionService {

	@Resource(name = "answerOptionDao")
	private AnswerOptionDao answerOptionDao;

	@Resource(name = "answerOptionService")
	protected AnswerOptionService answerOptionService;

	public AnswerOptions updateAnswerOption(AnswerOptions answeOption) {
		AnswerOptions answerOptionsReturn = new AnswerOptions();
		if (answeOption.getId() != null && !answeOption.getId().equals("")) {
			answerOptionsReturn = answerOptionDao.update(answeOption);
		} else {
			answeOption.setId(answerOptionDao.insert(answeOption));
			answerOptionsReturn = answeOption;
		}
		return answerOptionsReturn;
	}

	public Page<AnswerOptions> findAnswerOptions(Page<AnswerOptions> page, Long answerId) {
		return answerOptionDao.findAnswerOptions(page, answerId);
	}

}
