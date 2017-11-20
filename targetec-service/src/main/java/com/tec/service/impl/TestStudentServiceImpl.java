package com.tec.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tec.dao.AnswerMasterDao;
import com.tec.dao.QuestionMasterDao;
import com.tec.dao.StudentTestAnswerDao;
import com.tec.dao.StudentTestDetailDao;
import com.tec.dao.TestStudentMasterDao;
import com.tec.model.AnswerMaster;
import com.tec.model.QuestionMaster;
import com.tec.model.StudentTest;
import com.tec.model.StudentTestAnswer;
import com.tec.model.StudentTestDetail;
import com.tec.service.QuestionService;
import com.tec.service.TestStudentService;
import com.tec.template.Page;
import com.tec.tools.IConstants.QuestionStyle;

@Service("testStudentService")
public class TestStudentServiceImpl implements TestStudentService {

	@Resource(name = "questionMasterDao")
	private QuestionMasterDao questionMasterDao;

	@Resource(name = "testStudentMasterDao")
	private TestStudentMasterDao testStudentMasterDao;

	@Resource(name = "studentTestDetailDao")
	private StudentTestDetailDao studentTestDetailDao;

	@Resource(name = "studentTestAnswerDao")
	private StudentTestAnswerDao studentTestAnswerDao;

	@Resource(name = "answerMasterDao")
	private AnswerMasterDao answerMasterDao;

	@Resource(name = "questionService")
	private QuestionService questionService;

	public StudentTest insert(StudentTest studentTest) {
		return testStudentMasterDao.insert(studentTest);
	}

	public List<QuestionMaster> listTestQuestions(Long testId) {
		return questionMasterDao.findQuestionsByTestId(testId);
	}

	public List<QuestionMaster> listTestQuestionsByDetailId(Long testDetailId) {
		return questionMasterDao.findQuestionsByDetailId(testDetailId);
	}

	public StudentTestDetail insertTestDetail(StudentTestDetail studentTestDetail) {
		return studentTestDetailDao.insert(studentTestDetail);
	}

	public Boolean insertStudentAnswers(List<StudentTestAnswer> answers) {
		return studentTestAnswerDao.insertAnswers(answers);
	}

	public List<StudentTestAnswer> getStudentAnswers(Long testDetailId, Long questionId) {
		return studentTestAnswerDao.listStudentAnswers(testDetailId, questionId);
	}

	public StudentTestDetail processDragAndCorrect(StudentTestDetail studentTestDetail, Long questionId,
			Long qStyleId) {
		Page<AnswerMaster> page = new Page<AnswerMaster>("");
		List<AnswerMaster> correctAnswers = answerMasterDao.findAnswers(page, questionId).getPageItems();
		List<StudentTestAnswer> studentAnswers = getStudentAnswers(studentTestDetail.getId(), questionId);
		if (studentAnswers.size() == 0) {
			studentTestDetail.setAttempted(studentTestDetail.getAttempted() + 1);
			studentTestDetail.setUncorrected(studentTestDetail.getUncorrected() + 1);
			return studentTestDetail;
		}
		if (qStyleId.toString().equals(QuestionStyle.DIFFERTRANSC.toString())) {
			int count = 0;
			if (correctAnswers.size() == studentAnswers.size()) {
				for (AnswerMaster am : correctAnswers) {
					for (int j = 0; j < studentAnswers.size(); j++) {
						if (am.getAnswer().equals(studentAnswers.get(j).getTextAnswer())) {
							count++;
						}
					}
				}
				System.out.println("size : " + correctAnswers.size() + " : " + count);
				if (correctAnswers.size() != count) {
					studentTestDetail.setAttempted(studentTestDetail.getAttempted() + 1);
					studentTestDetail.setUncorrected(studentTestDetail.getUncorrected() + 1);
					return studentTestDetail;
				}
			} else {
				studentTestDetail.setAttempted(studentTestDetail.getAttempted() + 1);
				studentTestDetail.setUncorrected(studentTestDetail.getUncorrected() + 1);
				return studentTestDetail;
			}
		} else if (qStyleId.toString().equals(QuestionStyle.SINGLETEXT.toString())) {
			for (AnswerMaster am : correctAnswers) {
				if (!am.getAnswer().equals(studentAnswers.get(0).getTextAnswer())) {
					studentTestDetail.setAttempted(studentTestDetail.getAttempted() + 1);
					studentTestDetail.setUncorrected(studentTestDetail.getUncorrected() + 1);
					return studentTestDetail;
				}
			}
			studentTestDetail.setAttempted(studentTestDetail.getAttempted() + 1);
			return studentTestDetail;
		} else if (qStyleId.toString().equals(QuestionStyle.WRITING_STYLE.toString())) {
			studentTestDetail.setAttempted(studentTestDetail.getAttempted() + 1);
			return studentTestDetail;
		} else if (qStyleId.toString().equals(QuestionStyle.MULTICHOICE.toString())
				|| qStyleId.toString().equals(QuestionStyle.MULTICHOICE_L.toString())) {
			int i = 0;
			for (AnswerMaster am : correctAnswers) {
				if (!am.getCorrectYN().equals(studentAnswers.get(i).getCorrectYN())
						|| !am.getAnswer().equals(studentAnswers.get(i).getTextAnswer())) {
					studentTestDetail.setAttempted(studentTestDetail.getAttempted() + 1);
					studentTestDetail.setUncorrected(studentTestDetail.getUncorrected() + 1);
					return studentTestDetail;
				}
				++i;
			}

		} else if (qStyleId.toString().equals(QuestionStyle.DRAGTOCORRECT.toString())) {
			QuestionMaster questionMaster = questionService.findQuestionBean(questionId);
			int len = questionMaster.getQuestionText().split("<b>", -1).length - 1;
			for (int i = 0; i < len; i++) {
				if (!correctAnswers.get(i).getSeqNo().equals(Integer.toString(studentAnswers.get(i).getAnsSeqNo()))
						|| (!correctAnswers.get(i).getAnswer().equals(studentAnswers.get(i).getTextAnswer()))) {
					studentTestDetail.setAttempted(studentTestDetail.getAttempted() + 1);
					studentTestDetail.setUncorrected(studentTestDetail.getUncorrected() + 1);
					return studentTestDetail;
				}
			}

		} else {
			int i = 0;
			if (correctAnswers.size() <= 0) {
				studentTestDetail.setAttempted(studentTestDetail.getAttempted() + 1);
				studentTestDetail.setUncorrected(studentTestDetail.getUncorrected() + 1);
				return studentTestDetail;
			}
			for (AnswerMaster am : correctAnswers) {
				if (Integer.parseInt(am.getSeqNo()) != studentAnswers.get(i).getAnsSeqNo()
						|| !am.getAnswer().equals(studentAnswers.get(i).getTextAnswer())) {
					studentTestDetail.setAttempted(studentTestDetail.getAttempted() + 1);
					studentTestDetail.setUncorrected(studentTestDetail.getUncorrected() + 1);
					return studentTestDetail;
				}
				++i;
			}
		}

		studentTestDetail.setAttempted(studentTestDetail.getAttempted() + 1);
		studentTestDetail.setCorrected(studentTestDetail.getCorrected() + 1);
		return studentTestDetail;
	}

	public StudentTestDetail updateTestDetail(StudentTestDetail studentTestDetail) {
		return studentTestDetailDao.update(studentTestDetail);
	}

	public int getStudentTestWithAccount(Long acctId) {
		return studentTestDetailDao.getStudentTestWithAccount(acctId);
	}

	public int getStudentTestDetailWithAccount(Long acctId) {
		return studentTestDetailDao.getStudentTestDetailWithAccount(acctId);
	}

	public int getStudentTestAnswerWithQuestionId(Long id) {
		return studentTestAnswerDao.getStudentTestAnswerWithQuestionId(id);
	}

	public int getStudentTestAnsCount(String id) {
		return testStudentMasterDao.getStudentTestAnsCount(id);
	}

	public int getStudentTestDetailCount(String id) {
		return testStudentMasterDao.getStudentTestDetailCount(id);
	}

	public int getStudentTestCount(String id) {
		return testStudentMasterDao.getStudentTestCount(id);
	}

}
