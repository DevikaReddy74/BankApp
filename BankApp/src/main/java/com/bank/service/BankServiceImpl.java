package com.bank.service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bank.entity.Account;
import com.bank.entity.Transaction;
import com.bank.repository.AccountRepository;
import com.bank.repository.TransactionRepository;

@Service
public class BankServiceImpl implements BankService {
	
	
	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private TransactionRepository transactionRepository;
	
	
	@Override
	public void createOrUpdateAcc(Account account) {
		accountRepository.save(account);		
	}
	

	@Override
	public List<Account> getAllAcc() {
		return accountRepository.findAll();
	}

	@Override
	public Account getByIdAcc(long id) {
		return accountRepository.findById(id).get();
	}

//	return true if accountId exist
	@Override
	public boolean validateAccountId(long id) {
		Account account =accountRepository.findByAccountIdEquals(id);
		if(account != null) {		
			return true;
		}else {
			return false;
		}
	}
	
//	returns true if pan no. exist
	@Override
	public boolean validatePan(String pan) {
		Account account =accountRepository.findByPanEquals(pan);
		if(account != null) {
			return true;
		}else {
			return false;
		}
	}
	
//	return true if age is b/w >18 and <60
	@Override
	public boolean validateAge(LocalDate date) {
		LocalDate now=LocalDate.now();
		int age=Period.between(date, now).getYears();
		if(age>18 && age<60) {
			return true;
		}else {
			return false;
		}
	}

	
	@Override
	public void updateAccountBalanceAfterTransaction(@Valid Transaction transaction) {
		long fromId=transaction.getFromAccountId();
		long toId=transaction.getToAccountId();
		Account account1=getByIdAcc(fromId);
		Account account2=getByIdAcc(toId);
		double amount1=account1.getAccountBalance()-transaction.getTransactionAmount();
		double amount2=account2.getAccountBalance()+transaction.getTransactionAmount();
		account1.setAccountBalance(amount1);
		account2.setAccountBalance(amount2);
		accountRepository.save(account1);
		accountRepository.save(account2);		
	}
	
	
	
	
	
	@Override
	public void createTransaction(Transaction transaction) {
		transactionRepository.save(transaction);
	}

	@Override
	public List<Transaction> findTransactionByFromAccountIdEquals(long fromAccountId) {
		return transactionRepository.findByFromAccountIdEquals(fromAccountId);
	}


	@Override
	public List<Transaction> findTransactionByToAccountIdEquals(long toAccountId) {
		return transactionRepository.findByToAccountIdEquals(toAccountId);
	}
	
//	returns valid if all the details are valid for making a transaction
	@Override
	public String validateTransaction(Transaction transaction) {
		
		String message="valid";
//		check weather the From Account Id and To Account Id is present or not
		long fromId=transaction.getFromAccountId();
		long toId=transaction.getToAccountId();
		Account account1=accountRepository.findByAccountIdEquals(fromId);
		Account account2=accountRepository.findByAccountIdEquals(toId);

		if(account1 == null) {
			message="Enter a valid From Account Id";
		}else if(account2==null) {
			message="Enter a valid To Account Id";
		}else if(fromId == toId) {
			message="From Account Id and To Account Id must not be equal";
		}else if(account1 != null) {
//			Checking weather the account has a sufficient balance for transaction
			double balance=account1.getAccountBalance()-transaction.getTransactionAmount();
			if(balance<1) {
				message="No sufficient balance. Your balance is "+account1.getAccountBalance();
			}
		}
		return message;
	}


	@Override
	public List<Transaction> getAllTrans() {
		return transactionRepository.findAll();
	}

	@Override
	public List<Transaction> getAllCreditsAndDebitsOfAnAccount(long id){
		return transactionRepository.findByFromAccountIdOrToAccountId(id, id);
	}



}
