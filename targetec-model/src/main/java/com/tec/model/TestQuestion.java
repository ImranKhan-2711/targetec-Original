package com.tec.model;

public class TestQuestion {

	private Long id;
	private TestMaster testMaster = null;
	private QuestionMaster questionMaster = null;

	public TestQuestion() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TestQuestion(Long id, TestMaster testMaster, QuestionMaster questionMaster) {
		super();
		this.id = id;
		this.testMaster = testMaster;
		this.questionMaster = questionMaster;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public TestMaster getTestMaster() {
		return testMaster;
	}

	public void setTestMaster(TestMaster testMaster) {
		this.testMaster = testMaster;
	}

	public QuestionMaster getQuestionMaster() {
		return questionMaster;
	}

	public void setQuestionMaster(QuestionMaster questionMaster) {
		this.questionMaster = questionMaster;
	}

}
