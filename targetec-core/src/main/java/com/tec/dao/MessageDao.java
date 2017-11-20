package com.tec.dao;

import com.tec.model.MessageDetail;
import com.tec.template.Page;

public interface MessageDao {

	public Page<MessageDetail> listMessages(Page<MessageDetail> page, String subject, String message, String userNmae);
	
	public int getMessageWithAccount(Long acctId);
	
	public int getMessageHeadWithAccount(Long acctId);
}
