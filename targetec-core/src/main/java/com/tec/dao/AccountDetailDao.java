package com.tec.dao;

import com.tec.model.Account;
import com.tec.model.AccountDetail;
import com.tec.template.Page;

public interface AccountDetailDao {
	public Page<AccountDetail> findStudents(Page<AccountDetail> page, String firstName, String lastName,
			String mobileNumber);

	public Page<AccountDetail> findTrainers(Page<AccountDetail> page, String firstName, String lastName,
			String mobileNumber);
	
	public AccountDetail findAccountDetailById(Long id);
	
	public AccountDetail insert(AccountDetail accountDetail);
	
	public AccountDetail update(AccountDetail accountDetail);
	
	public Account getAccount(Long id);
	
	public Page<AccountDetail> findStudentsfindStudentsWithoutTestId(Page<AccountDetail> page, String firstName, String lastName,
			String mobileNumber,Long testId);
	
	public AccountDetail getAccountByUserName(String userName);
}
