package com.tec.service.impl;

import java.util.Random;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tec.dao.AccountDao;
import com.tec.dao.AccountDetailDao;
import com.tec.dao.MessageDao;
import com.tec.model.Account;
import com.tec.model.AccountDetail;
import com.tec.model.EmailTemplate;
import com.tec.model.ResponseBean;
import com.tec.model.RoleMaster;
import com.tec.service.AccountService;
import com.tec.service.TestStudentService;
import com.tec.template.Page;

@Service("accountService")
public class AccountServiceImpl implements AccountService {

	@Resource(name = "accountDetailDao")
	private AccountDetailDao accountDetailDao;

	@Resource(name = "accountDao")
	private AccountDao accountDao;

	@Resource(name = "messageDao")
	private MessageDao messageDao;

	@Resource(name = "testStudentService")
	private TestStudentService testStudentService;

	public Page<AccountDetail> listStudents(Page<AccountDetail> page, String firstName, String lastName,
			String mobileNumber) {
		return accountDetailDao.findStudents(page, firstName, lastName, mobileNumber);
	}

	public Page<AccountDetail> listTrainers(Page<AccountDetail> page, String firstName, String lastName,
			String mobileNumber) {
		return accountDetailDao.findTrainers(page, firstName, lastName, mobileNumber);
	}

	public AccountDetail getStudent(Long id) {
		return accountDetailDao.findAccountDetailById(id);
	}

	public AccountDetail updateStudent(AccountDetail accountDetail) {
		if (accountDetail.getId() != null && !accountDetail.getId().equals("")) {
			accountDetailDao.update(accountDetail);
			return accountDetail;
		} else {
			return accountDetailDao.insert(accountDetail);
		}
	}

	public Account createStudent(Account account) {
		account.setRole(new RoleMaster("STUDENT_ROLE", null, null));
		return accountDao.insert(account);
	}

	public Account createTrainer(Account account) {
		account.setRole(new RoleMaster("TRAINER_ROLE", null, null));
		return accountDao.insert(account);
	}

	public AccountDetail updateTrainer(AccountDetail accountDetail) {
		if (accountDetail.getId() != null && !accountDetail.getId().equals("")) {
			accountDetailDao.update(accountDetail);
			return accountDetail;
		} else {
			return accountDetailDao.insert(accountDetail);
		}
	}

	public AccountDetail getTrainer(Long id) {
		return accountDetailDao.findAccountDetailById(id);
	}

	public Account getAccountById(Long id) {
		return accountDetailDao.getAccount(id);
	}

	public Page<AccountDetail> findStudentsfindStudentsWithoutTestId(Page<AccountDetail> page, String firstName,
			String lastName, String mobileNumber, Long testId) {
		return accountDetailDao.findStudentsfindStudentsWithoutTestId(page, firstName, lastName, mobileNumber, testId);
	}

	public AccountDetail getAccountByUserName(String userName) {
		AccountDetail accountDetail = accountDetailDao.getAccountByUserName(userName);
		return accountDetail;
	}

	public String generatePassword(int length, int noOfCAPSAlpha, int noOfDigits, int noOfSplChars) {
		StringBuilder result = new StringBuilder();
		final String ALPHA_CAPS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		final String ALPHA = "abcdefghijklmnopqrstuvwxyz";
		final String NUM = "0123456789";
		final String SPL_CHARS = "!@#$%^&*_=+-/";

		while (length > 0) {
			Random rand = new Random();
			for (int i = 0; i < noOfCAPSAlpha; i++) {
				result.append(ALPHA_CAPS.charAt(rand.nextInt(ALPHA_CAPS.length())));
				length--;
			}
			for (int i = 0; i < noOfDigits; i++) {
				result.append(NUM.charAt(rand.nextInt(NUM.length())));
				length--;
			}
			for (int i = 0; i < noOfSplChars; i++) {
				result.append(SPL_CHARS.charAt(rand.nextInt(SPL_CHARS.length())));
				length--;
			}
			for (int i = 0; i < 3; i++) {
				result.append(ALPHA.charAt(rand.nextInt(ALPHA.length())));
				length--;
			}
		}
		return result.toString();
	}

	public EmailTemplate getContent(Long id) {
		return accountDao.getContent(id);
	}

	public Account getAccount(String username) {
		return accountDao.getAccount(username);
	}

	public Account update(Account account) {
		return accountDao.update(account);
	}

	public ResponseBean delete(Long id) {
		String message = null;
		ResponseBean responseBean = new ResponseBean();
		int messageDetail = messageDao.getMessageWithAccount(id);
		int messageHead = messageDao.getMessageHeadWithAccount(id);
		int studentTest = testStudentService.getStudentTestWithAccount(id);
		int studentTestDetail = testStudentService.getStudentTestDetailWithAccount(id);
		if (messageDetail > 0) {
			message = "Student is Linked with Message Detail";
			responseBean.setValid(false);
			responseBean.setMessage(message);
		} else if (messageHead > 0) {
			responseBean.setValid(false);
			message = "Student is Linked with Message Head";
			responseBean.setMessage(message);
		} else if (studentTest > 0) {
			responseBean.setValid(false);
			message = "Some Test assigned to this user so can't delete this student";
			responseBean.setMessage(message);
		} else if (studentTestDetail > 0) {
			responseBean.setValid(false);
			message = "Student linked with Student Test Detail so can't delete this student";
			responseBean.setMessage(message);
		} else {
			responseBean.setValid(true);
			accountDao.delete(id);
		}

		return responseBean;
	}

	public int getAccountCount(String username) {
		return accountDao.getAccountCount(username);
	}

}
