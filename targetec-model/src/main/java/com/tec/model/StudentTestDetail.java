package com.tec.model;

import java.sql.Time;
import java.util.Date;

public class StudentTestDetail {
	private Long id;
	private TestMaster testMaster = null;
	private Account account = null;
	private Date performedOn;
	private Time consumedTime;
	private int attempted;
	private int corrected;
	private int uncorrected;

	public StudentTestDetail() {
		super();
		// TODO Auto-generated constructor stub
	}

	public StudentTestDetail(Long id, TestMaster testMaster, Account account, Date performedOn, Time consumedTime,
			int attempted, int corrected, int uncorrected) {
		super();
		this.id = id;
		this.testMaster = testMaster;
		this.account = account;
		this.performedOn = performedOn;
		this.consumedTime = consumedTime;
		this.attempted = attempted;
		this.corrected = corrected;
		this.uncorrected = uncorrected;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public TestMaster getTestMaster() {
		return testMaster;
	}

	public void setTestMaster(TestMaster testMaster) {
		this.testMaster = testMaster;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public Date getPerformedOn() {
		return performedOn;
	}

	public void setPerformedOn(Date performedOn) {
		this.performedOn = performedOn;
	}

	public Time getConsumedTime() {
		return consumedTime;
	}

	public void setConsumedTime(Time consumedTime) {
		this.consumedTime = consumedTime;
	}

	public int getAttempted() {
		return attempted;
	}

	public void setAttempted(int attempted) {
		this.attempted = attempted;
	}

	public int getCorrected() {
		return corrected;
	}

	public void setCorrected(int corrected) {
		this.corrected = corrected;
	}

	public int getUncorrected() {
		return uncorrected;
	}

	public void setUncorrected(int uncorrected) {
		this.uncorrected = uncorrected;
	}

}
