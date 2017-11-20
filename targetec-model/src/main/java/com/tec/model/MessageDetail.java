package com.tec.model;

public class MessageDetail {
	private Long id;
	private MessageHead messageHead;
	private Account account = null;
	private String readYN;

	public MessageDetail() {
		super();
		// TODO Auto-generated constructor stub
	}

	public MessageDetail(Long id, MessageHead messageHead, Account account, String readYN) {
		super();
		this.id = id;
		this.messageHead = messageHead;
		this.account = account;
		this.readYN = readYN;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public MessageHead getMessageHead() {
		return messageHead;
	}

	public void setMessageHead(MessageHead messageHead) {
		this.messageHead = messageHead;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public String getReadYN() {
		return readYN;
	}

	public void setReadYN(String readYN) {
		this.readYN = readYN;
	}

}
