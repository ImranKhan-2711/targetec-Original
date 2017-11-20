package com.tec.dao;

import com.tec.model.Account;
import com.tec.model.EmailTemplate;

public interface AccountDao {

	public Account insert(Account account);

	public Account update(Account account);
	
	public EmailTemplate getContent(Long id);
	
	public Account getAccount(String username);
	
	public void delete(Long acctId);
	
	public int getAccountCount(String username);
}
