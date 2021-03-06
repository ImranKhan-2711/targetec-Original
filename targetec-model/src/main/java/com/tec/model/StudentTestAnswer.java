package com.tec.model;

public class StudentTestAnswer {
	private Long id;
	private TestMaster testMaster = null;
	private StudentTestDetail studentTestDetail = null;
	private QuestionMaster questionMaster = null;
	private int ansSeqNo;
	private String textAnswer;
	private Long mediaAnswer;

	private String correctYN;

	public StudentTestAnswer() {
		super();
		// TODO Auto-generated constructor stub
	}

	public StudentTestAnswer(Long id, int ansSeqNo, String textAnswer, String correctYN) {
		super();
		this.id = id;
		this.ansSeqNo = ansSeqNo;
		this.textAnswer = textAnswer;
		this.correctYN = correctYN;
	}

	public StudentTestAnswer(Long id, TestMaster testMaster, StudentTestDetail studentTestDetail,
			QuestionMaster questionMaster, int ansSeqNo, String textAnswer, Long mediaAnswer) {
		super();
		this.id = id;
		this.testMaster = testMaster;
		this.studentTestDetail = studentTestDetail;
		this.questionMaster = questionMaster;
		this.ansSeqNo = ansSeqNo;
		this.textAnswer = textAnswer;
		this.mediaAnswer = mediaAnswer;
	}

	public StudentTestAnswer(Long id, TestMaster testMaster, StudentTestDetail studentTestDetail,
			QuestionMaster questionMaster, int ansSeqNo, String textAnswer, Long mediaAnswer, String correctYN) {
		super();
		this.id = id;
		this.testMaster = testMaster;
		this.studentTestDetail = studentTestDetail;
		this.questionMaster = questionMaster;
		this.ansSeqNo = ansSeqNo;
		this.textAnswer = textAnswer;
		this.mediaAnswer = mediaAnswer;
		this.correctYN = correctYN;
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

	public StudentTestDetail getStudentTestDetail() {
		return studentTestDetail;
	}

	public void setStudentTestDetail(StudentTestDetail studentTestDetail) {
		this.studentTestDetail = studentTestDetail;
	}

	public QuestionMaster getQuestionMaster() {
		return questionMaster;
	}

	public void setQuestionMaster(QuestionMaster questionMaster) {
		this.questionMaster = questionMaster;
	}

	public int getAnsSeqNo() {
		return ansSeqNo;
	}

	public void setAnsSeqNo(int ansSeqNo) {
		this.ansSeqNo = ansSeqNo;
	}

	public String getTextAnswer() {
		return textAnswer;
	}

	public void setTextAnswer(String textAnswer) {
		this.textAnswer = textAnswer;
	}

	public Long getMediaAnswer() {
		return mediaAnswer;
	}

	public void setMediaAnswer(Long mediaAnswer) {
		this.mediaAnswer = mediaAnswer;
	}

	public String getCorrectYN() {
		return correctYN;
	}

	public void setCorrectYN(String correctYN) {
		this.correctYN = correctYN;
	}

}
