package com.tec.dao;

import com.tec.model.StudentTestDetail;

public interface StudentTestDetailDao {

	public StudentTestDetail insert(StudentTestDetail studentTestDetail);

	public StudentTestDetail update(StudentTestDetail studentTestDetail);

	public int getStudentTestWithAccount(Long acctId);

	public int getStudentTestDetailWithAccount(Long acctId);
}
