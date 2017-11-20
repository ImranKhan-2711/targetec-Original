package com.tec.dao;

import java.util.List;

import com.tec.model.CategoryMaster;
import com.tec.model.QuestionMaster;
import com.tec.model.ResourceMaster;
import com.tec.model.TypeMaster;
import com.tec.template.Page;

public interface QuestionMasterDao {

	public Long insert(QuestionMaster questionMaster);

	public QuestionMaster update(QuestionMaster questionMaster);

	public QuestionMaster findByQuestionMasterId(Long id);

	public Page<QuestionMaster> findQuestions(Page<QuestionMaster> page, Long typeId);

	public List<TypeMaster> getTypeList();

	public List<CategoryMaster> getCategoryList();

	public ResourceMaster findByResourceMasterId(Long id);

	public List<QuestionMaster> findQuestionsByTestId(Long testId);

	public List<QuestionMaster> findQuestionsByDetailId(Long testDetailId);
	
	public void delete(Long quesId);
}
