package com.tec.model;

public class Account {

	private Long id;
	private String userName;
	private String password;
	private String status;
	private RoleMaster role = null;

	//added for email module
	private String firstName;
	private String lastName;
	private String email;

	public Account() {
		super();
	}

	public Account(Long id) {
		super();
		this.id = id;
	}

	public Account(Long id, String userName) {
		super();
		this.id = id;
		this.userName = userName;
	}

	public Account(Long id, String userName, String password, String status, RoleMaster role) {
		super();
		this.id = id;
		this.userName = userName;
		this.password = password;
		this.status = status;
		this.role = role;
	}



	public Account(Long id, String userName, String firstName, String lastName, String email) {
		super();
		this.id = id;
		this.userName = userName;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public RoleMaster getRole() {
		return role;
	}

	public void setRole(RoleMaster role) {
		this.role = role;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}



}
