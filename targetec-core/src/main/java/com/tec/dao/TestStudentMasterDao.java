package com.tec.dao;

import com.tec.model.StudentTest;

public interface TestStudentMasterDao {

	public StudentTest insert(StudentTest studentTest);
	
	public int getStudentTestAnsCount(String id);
	
	public int getStudentTestDetailCount(String id);
	
	public int getStudentTestCount(String id);

}
