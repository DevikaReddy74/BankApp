package com.bank.entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Min;



@Entity
public class Transaction {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long transactionId;
	
//	@Max(value = 2147483647)
	private long fromAccountId;
	
	private long toAccountId;
	
	@Min(1)
	private double transactionAmount;
	
	private LocalDateTime transactionAt;

	public Transaction() {

	}

	public Transaction(long fromAccountId, long toAccountId, @Min(1) double transactionAmount) {

		this.fromAccountId = fromAccountId;
		this.toAccountId = toAccountId;
		this.transactionAmount = transactionAmount;
	}

	public long getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(long transactionId) {
		this.transactionId = transactionId;
	}

	public long getFromAccountId() {
		return fromAccountId;
	}

	public void setFromAccountId(long fromAccountId) {
		this.fromAccountId = fromAccountId;
	}

	public long getToAccountId() {
		return toAccountId;
	}

	public void setToAccountId(long toAccountId) {
		this.toAccountId = toAccountId;
	}

	public double getTransactionAmount() {
		return transactionAmount;
	}

	public void setTransactionAmount(double transactionAmount) {
		this.transactionAmount = transactionAmount;
	}

	public LocalDateTime getTransactionAt() {
		return transactionAt;
	}

	public void setTransactionAt(LocalDateTime transactionAt) {
		this.transactionAt = transactionAt;
	}
	
	
	
}
