package com.tec.model;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class QuestionMaster {

	private Long id;
	private TypeMaster typeMaster = null;
	private StyleMaster styleMaster = null;
	private ResourceMaster resourceMaster = null;
	private String description;
	private String direction;
	private String questionText;
	private int minWords;
	private int maxWords;
	private Time duration;
	private String status;
	private String questionCode;

	private List<AnswerMaster> answers = new ArrayList<AnswerMaster>();

	public QuestionMaster() {
		super();
		// TODO Auto-generated constructor stub
	}

	public QuestionMaster(Long id) {
		super();
		this.id = id;
	}

	public QuestionMaster(Long id, TypeMaster typeMaster, StyleMaster styleMaster, ResourceMaster resourceMaster,
			String description, String direction, String questionText, int minWords, int maxWords, Time duration,
			String status) {
		super();
		this.id = id;
		this.typeMaster = typeMaster;
		this.styleMaster = styleMaster;
		this.resourceMaster = resourceMaster;
		this.description = description;
		this.direction = direction;
		this.questionText = questionText;
		this.minWords = minWords;
		this.maxWords = maxWords;
		this.duration = duration;
		this.status = status;
	}

	public QuestionMaster(Long id, TypeMaster typeMaster, StyleMaster styleMaster, ResourceMaster resourceMaster,
			String description, String direction, String questionText, int minWords, int maxWords, Time duration,
			String status, String questionCode) {
		super();
		this.id = id;
		this.typeMaster = typeMaster;
		this.styleMaster = styleMaster;
		this.resourceMaster = resourceMaster;
		this.description = description;
		this.direction = direction;
		this.questionText = questionText;
		this.minWords = minWords;
		this.maxWords = maxWords;
		this.duration = duration;
		this.status = status;
		this.questionCode = questionCode;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public TypeMaster getTypeMaster() {
		return typeMaster;
	}

	public void setTypeMaster(TypeMaster typeMaster) {
		this.typeMaster = typeMaster;
	}

	public StyleMaster getStyleMaster() {
		return styleMaster;
	}

	public void setStyleMaster(StyleMaster styleMaster) {
		this.styleMaster = styleMaster;
	}

	public ResourceMaster getResourceMaster() {
		return resourceMaster;
	}

	public void setResourceMaster(ResourceMaster resourceMaster) {
		this.resourceMaster = resourceMaster;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public String getQuestionText() {
		return questionText;
	}

	public void setQuestionText(String questionText) {
		this.questionText = questionText;
	}

	public int getMinWords() {
		return minWords;
	}

	public void setMinWords(int minWords) {
		this.minWords = minWords;
	}

	public int getMaxWords() {
		return maxWords;
	}

	public void setMaxWords(int maxWords) {
		this.maxWords = maxWords;
	}

	public Time getDuration() {
		return duration;
	}

	public void setDuration(Time duration) {
		this.duration = duration;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<AnswerMaster> getAnswers() {
		return answers;
	}

	public void setAnswers(List<AnswerMaster> answers) {
		this.answers = answers;
	}

	public String getQuestionCode() {
		return questionCode;
	}

	public void setQuestionCode(String questionCode) {
		this.questionCode = questionCode;
	}

}
