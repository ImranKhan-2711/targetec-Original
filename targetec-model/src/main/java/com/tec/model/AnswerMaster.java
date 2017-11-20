package com.tec.model;

import java.util.ArrayList;
import java.util.List;

public class AnswerMaster {

	private Long id;
	private QuestionMaster questionMaster;
	private String seqNo;
	private String answer;
	private String correctYN;
	private String description;
	private List<AnswerOptions> options = new ArrayList<AnswerOptions>();

	public AnswerMaster() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AnswerMaster(Long id, QuestionMaster questionMaster, String seqNo, String answer, String correctYN,
			String description) {
		super();
		this.id = id;
		this.questionMaster = questionMaster;
		this.seqNo = seqNo;
		this.answer = answer;
		this.correctYN = correctYN;
		this.description = description;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public QuestionMaster getQuestionMaster() {
		return questionMaster;
	}

	public void setQuestionMaster(QuestionMaster questionMaster) {
		this.questionMaster = questionMaster;
	}

	public String getSeqNo() {
		return seqNo;
	}

	public void setSeqNo(String seqNo) {
		this.seqNo = seqNo;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public String getCorrectYN() {
		return correctYN;
	}

	public void setCorrectYN(String correctYN) {
		this.correctYN = correctYN;
	}

	public List<AnswerOptions> getOptions() {
		return options;
	}

	public void setOptions(List<AnswerOptions> options) {
		this.options = options;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}