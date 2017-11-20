package com.tec.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tec.dao.MessageDao;
import com.tec.model.MessageDetail;
import com.tec.service.MessageService;
import com.tec.template.Page;

@Service("messageService")
public class MessageServiceImpl implements MessageService {
	@Resource(name = "messageDao")
	private MessageDao messageDao;

	public Page<MessageDetail> listMessages(Page<MessageDetail> page, String subject, String message, String userName) {
		return messageDao.listMessages(page, subject, message, userName);
	}

	public int getMessageWithAccount(Long acctId) {
		// TODO Auto-generated method stub
		return messageDao.getMessageWithAccount(acctId);
	}

	public int getMessageHeadWithAccount(Long acctId) {
		// TODO Auto-generated method stub
		return messageDao.getMessageHeadWithAccount(acctId);
	}

}
