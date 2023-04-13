package com.bank.service;

import java.time.LocalDate;
import java.util.List;

import javax.validation.Valid;

import com.bank.entity.Account;
import com.bank.entity.Transaction;

public interface BankService {
	
	public void createOrUpdateAcc(Account account);
	public List<Account> getAllAcc();
	public Account getByIdAcc(long id);
	boolean validateAccountId(long id);
	boolean validatePan(String pan);
	public boolean validateAge(LocalDate date);
	public void updateAccountBalanceAfterTransaction(@Valid Transaction transaction);

	
	
	public void createTransaction(Transaction transaction);
	public List<Transaction> findTransactionByFromAccountIdEquals(long fromAccountId);
	public List<Transaction> findTransactionByToAccountIdEquals(long toAccountId);
	public String validateTransaction(Transaction transaction);
	public List<Transaction> getAllTrans();
	public List<Transaction> getAllCreditsAndDebitsOfAnAccount(long id);
	
}
