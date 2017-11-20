package com.tec.model;

public class AnswerOptions {

	private Long id;
	private AnswerMaster answerMaster = null;
	private String ansOption;

	public AnswerOptions() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AnswerOptions(Long id, AnswerMaster answerMaster, String ansOption) {
		super();
		this.id = id;
		this.answerMaster = answerMaster;
		this.ansOption = ansOption;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public AnswerMaster getAnswerMaster() {
		return answerMaster;
	}

	public void setAnswerMaster(AnswerMaster answerMaster) {
		this.answerMaster = answerMaster;
	}

	public String getAnsOption() {
		return ansOption;
	}

	public void setAnsOption(String ansOption) {
		this.ansOption = ansOption;
	}

}
