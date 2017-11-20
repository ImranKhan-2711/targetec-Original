package com.tec.service;

import com.tec.model.Account;
import com.tec.model.AccountDetail;
import com.tec.model.EmailTemplate;
import com.tec.model.ResponseBean;
import com.tec.template.Page;

public interface AccountService {

	public Page<AccountDetail> listStudents(Page<AccountDetail> page, String firstName, String lastName,
			String mobileNumber);

	public Page<AccountDetail> listTrainers(Page<AccountDetail> page, String firstName, String lastName,
			String mobileNumber);

	public AccountDetail getStudent(Long id);

	public AccountDetail updateStudent(AccountDetail accountDetail);

	public Account createStudent(Account account);

	public Account createTrainer(Account account);
	
	public AccountDetail updateTrainer(AccountDetail accountDetail);
	
	public AccountDetail getTrainer(Long id);
	
	public Account getAccountById(Long id);
	
	public Page<AccountDetail> findStudentsfindStudentsWithoutTestId(Page<AccountDetail> page, String firstName, String lastName,
			String mobileNumber,Long testId);
	
	public AccountDetail getAccountByUserName(String userName);
	
	public String generatePassword(int length, int noOfCAPSAlpha, int noOfDigits, int noOfSplChars);
	
	public EmailTemplate getContent(Long id);
	
	public Account getAccount(String username);
	
	public Account update(Account account); 
	
	public ResponseBean delete(Long id);
	
	public int getAccountCount(String username);

}
