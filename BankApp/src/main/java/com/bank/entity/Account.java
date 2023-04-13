package com.bank.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

//entity represent fields at DB side while model represent the fields bind at UI side
//Entity is needed to "store" while DTO is needed to "show"
//I'm using only entity
@Entity
public class Account {

	@Id
	@Min(value = 1000000001, message = "Account number must be 10 digits")
	private long accountId;

	@NotEmpty(message = "Name must not be empty")
	private String customerName;

	@Column(unique = true)
	@NotEmpty(message = "Enter PAN number")
	@Pattern(regexp = "[A-Z]{5}[0-9]{4}[A-Z]{1}", message = " enter valid pan number")
	private String pan;

	@Past(message = "Date must be in past")
	private LocalDate dob;

	@NotEmpty
	private String accountType;

	@NotEmpty
	private String accountStatus;

	@Min(value = 1, message = "Balance must be >=1")
	private double accountBalance;

	@NotEmpty(message = "Enter phone number")
	@Pattern(regexp = "[6-9]{1}[0-9]{9}", message = "enter valid phonenumber")
	private String phoneNumber;

	@NotEmpty(message = "Enter Email Id")
	@Email(message = " Enter a Valid Email Id ")
	private String emailId;

	private double mab;

	public Account() {

	}

	public Account(@Min(value = 1000000001, message = "Account number must be 10 digits") long accountId,
			@NotEmpty(message = "Name must not be empty") String customerName,
			@NotEmpty(message = "Enter PAN number") @Pattern(regexp = "[A-Z]{5}[0-9]{4}[A-Z]{1}", message = " enter valid pan number") String pan,
			@Past(message = "Date must be in past") LocalDate dob, @NotEmpty String accountType,
			@NotEmpty String accountStatus, @Min(value = 1, message = "Balance must be >=1") double accountBalance,
			@NotEmpty(message = "Enter phone number") @Pattern(regexp = "[6-9]{1}[0-9]{9}", message = "enter valid phonenumber") String phoneNumber,
			@NotEmpty(message = "Enter Email Id") @Email(message = " Enter a Valid Email Id ") String emailId,
			double mab) {
		super();
		this.accountId = accountId;
		this.customerName = customerName;
		this.pan = pan;
		this.dob = dob;
		this.accountType = accountType;
		this.accountStatus = accountStatus;
		this.accountBalance = accountBalance;
		this.phoneNumber = phoneNumber;
		this.emailId = emailId;
		this.mab = mab;
	}

	public long getAccountId() {
		return accountId;
	}

	public void setAccountId(long accountId) {
		this.accountId = accountId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getPan() {
		return pan;
	}

	public void setPan(String pan) {
		this.pan = pan;
	}

	public LocalDate getDob() {
		return dob;
	}

	public void setDob(LocalDate dob) {
		this.dob = dob;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public String getAccountStatus() {
		return accountStatus;
	}

	public void setAccountStatus(String accountStatus) {
		this.accountStatus = accountStatus;
	}

	public double getAccountBalance() {
		return accountBalance;
	}

	public void setAccountBalance(double accountBalance) {
		this.accountBalance = accountBalance;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public double getMab() {
		return mab;
	}

	public void setMab(double mab) {
		this.mab = mab;
	}

}