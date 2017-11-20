package com.tec.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tec.dao.QuestionMasterDao;
import com.tec.dao.TestQuestionMasterDao;
import com.tec.model.AnswerMaster;
import com.tec.model.AnswerOptions;
import com.tec.model.CategoryMaster;
import com.tec.model.QuestionMaster;
import com.tec.model.ResourceMaster;
import com.tec.model.ResponseBean;
import com.tec.model.TestQuestion;
import com.tec.model.TypeMaster;
import com.tec.service.AnswerOptionService;
import com.tec.service.AnswerService;
import com.tec.service.QuestionService;
import com.tec.service.TestQuestionService;
import com.tec.service.TestStudentService;
import com.tec.template.Page;

/**
 * 
 * @author Mandeep
 *
 */
@Service("questionService")
public class QuestionServiceImpl implements QuestionService {

	@Resource(name = "questionMasterDao")
	private QuestionMasterDao questionMasterDao;

	@Resource(name = "answerService")
	protected AnswerService answerService;
	
	@Resource(name = "testQuestionService")
	protected TestQuestionService testQuestionService;
	
	@Resource(name = "testStudentService")
	protected TestStudentService testStudentService;
	
	@Resource(name = "answerOptionService")
	protected AnswerOptionService answerOptionService;
	
	@Resource(name = "testQuestionMasterDao")
	protected TestQuestionMasterDao testQuestionMasterDao;

	public QuestionMaster findQuestionBean(Long id) {
		QuestionMaster questionMaster = new QuestionMaster();
		AnswerMaster answerMaster = new AnswerMaster();
		AnswerOptions answerOptions = new AnswerOptions();
		AnswerOptions answerOptions2 = new AnswerOptions();
		List<AnswerMaster> answerMasters = new ArrayList<AnswerMaster>();
		List<AnswerOptions> answerOptionss = null;
		Page<AnswerMaster> answerMasterPage =null;
		Page<AnswerOptions> answerOptionsPage =null;
		if (id != null) {
			questionMaster = questionMasterDao.findByQuestionMasterId(id);
			ResourceMaster resourceMaster = questionMasterDao.findByResourceMasterId(questionMaster.getResourceMaster().getId());
			questionMaster.setResourceMaster(resourceMaster);
			Page<AnswerMaster> page = new Page<AnswerMaster>(1, 10, -1, "", "asc");
			answerMasterPage = answerService.findAnswers(page, id);
			answerMasters = answerMasterPage.getPageItems();
			for(AnswerMaster ans : answerMasters){
				Page<AnswerOptions> page1 = new Page<AnswerOptions>(1, 10, -1, "", "asc");
				answerOptionsPage = answerOptionService.findAnswerOptions(page1,ans.getId());
				answerOptionss = new ArrayList<AnswerOptions>();
				answerOptionss = answerOptionsPage.getPageItems();
				ans.setOptions(answerOptionss);
			}
		} else {
			answerOptionss = new ArrayList<AnswerOptions>();
			questionMaster = new QuestionMaster(null, null, null, null, null, null, null, 50, 100, null, null);
			answerMaster = new AnswerMaster(null, null, null, null, null,null);
			answerOptions = new AnswerOptions(null, null, null);
			answerOptionss.add(answerOptions);
			answerMaster.setOptions(answerOptionss);
			answerMasters.add(answerMaster);
		}
		questionMaster.setAnswers(answerMasters);
		return questionMaster;
	}

	public QuestionMaster updateQuestion(QuestionMaster questionMaster) {
		QuestionMaster questionMasterReturn = new QuestionMaster();
		if (questionMaster.getId() != null && !questionMaster.getId().equals("")) {
			questionMasterReturn = questionMasterDao.update(questionMaster);
		}else{
			questionMasterReturn = questionMasterDao.findByQuestionMasterId(questionMasterDao.insert(questionMaster));
		}
		
		List<AnswerMaster> answerMasters = questionMaster.getAnswers();
		List<AnswerMaster> answerMastersReturn = new ArrayList<AnswerMaster>();
		for (AnswerMaster answerMaster : answerMasters) {
			if (Integer.parseInt(answerMaster.getSeqNo()) > -1) {
				answerMaster.setQuestionMaster(questionMasterReturn);
				answerMaster = answerService.updateAnswer(answerMaster);
				answerMastersReturn.add(answerMaster);
			}
		}
		questionMasterReturn.setAnswers(answerMastersReturn);
		return questionMasterReturn;
	}

	public Page<QuestionMaster> listQuestions(Page<QuestionMaster> page, Long typeId) {
		return questionMasterDao.findQuestions(page, typeId);
	}

	public List<TypeMaster> typeList() {
		return questionMasterDao.getTypeList();
	}

	public List<CategoryMaster> categoryList() {
		return questionMasterDao.getCategoryList();
	}

	public ResponseBean delete(Long id) {
		String message = null;
		ResponseBean responseBean = new ResponseBean();
		int testQuestion = testQuestionService.getTestQuestionByQuestionId(id);
		int studentTestAnswers = testStudentService.getStudentTestAnswerWithQuestionId(id);
		System.out.println(">>>>>:: "+testQuestion+" : "+studentTestAnswers);
		if(testQuestion > 0){
			message = "Can't delete,Question is added in the test";
			responseBean.setValid(false);
			responseBean.setMessage(message);
		}else if(studentTestAnswers > 0){
			message = "Can't delete,Question is assigned to Student";
			responseBean.setValid(false);
			responseBean.setMessage(message);
		}else{
			responseBean.setValid(true);
			questionMasterDao.delete(id);
		}
		return responseBean;
	}

}