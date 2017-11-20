package com.tec.model;

import java.sql.Time;

public class TestMaster {

	private Long id;
	private CategoryMaster categoryMaster = null;
	private TypeMaster typeMaster = null;
	private String name;
	private String description;
	private ResourceMaster image;
	private int questionCount;
	private Time duration;
	private String status;

	public TestMaster() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TestMaster(Long id) {
		super();
		this.id = id;
	}

	public TestMaster(Long id, CategoryMaster categoryMaster, TypeMaster typeMaster, String name, String description,
			ResourceMaster image, int questionCount, Time duration, String status) {
		super();
		this.id = id;
		this.categoryMaster = categoryMaster;
		this.typeMaster = typeMaster;
		this.name = name;
		this.description = description;
		this.image = image;
		this.questionCount = questionCount;
		this.duration = duration;
		this.status = status;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public CategoryMaster getCategoryMaster() {
		return categoryMaster;
	}

	public void setCategoryMaster(CategoryMaster categoryMaster) {
		this.categoryMaster = categoryMaster;
	}

	public TypeMaster getTypeMaster() {
		return typeMaster;
	}

	public void setTypeMaster(TypeMaster typeMaster) {
		this.typeMaster = typeMaster;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public ResourceMaster getImage() {
		return image;
	}

	public void setImage(ResourceMaster image) {
		this.image = image;
	}

	public int getQuestionCount() {
		return questionCount;
	}

	public void setQuestionCount(int questionCount) {
		this.questionCount = questionCount;
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

}
