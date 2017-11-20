package com.tec.service;

import com.tec.model.MessageDetail;
import com.tec.template.Page;

public interface MessageService {

	public Page<MessageDetail> listMessages(Page<MessageDetail> page, String subject, String message, String userName);
	
	public int getMessageWithAccount(Long acctId);
	
	public int getMessageHeadWithAccount(Long acctId);

}
