package com.bank;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bank.entity.Account;
import com.bank.entity.Transaction;
import com.bank.service.BankService;

@SpringBootTest
class BankAppApplicationTests {

	@Autowired
	BankService bankService;
	
	LocalDate date=LocalDate.of(1999, 10, 01);
	
	LocalDateTime dateTime=LocalDateTime.now();

	@Test
	public void createAccountTest() {
		Account account=new Account(1000000004,"Devika","FYEPD2927D",date,"Savings","Active",10000,"9876543210","devika1@gmail.com",1000);
		assertFalse(bankService.validateAccountId(account.getAccountId()));
		assertFalse(bankService.validatePan(account.getPan()));
		assertTrue(bankService.validateAge(account.getDob()));
		bankService.createOrUpdateAcc(account);
	}
	
	@Test
	public void getAccountByIdTest() {
		Account account=new Account(1000000005,"Devika","FYEPD2927E",date,"Savings","Active",10000,"9876543210","devika2@gmail.com",1000);
		bankService.createOrUpdateAcc(account);	
		long id=1000000005;
		Account account1=bankService.getByIdAcc(id);
		assertEquals(1000000005, account1.getAccountId());
		double balance=account.getAccountBalance();
		assertEquals(10000, balance);
	}

	@Test
	public void getAllAccountTest() {
		
		List<Account> accounts=bankService.getAllAcc();
		Account account=accounts.get(0);
		assertEquals(1000000001, account.getAccountId());
		assertEquals("FYEPD2927A", account.getPan());
		assertEquals("sudhakarreddythakkolu975@gmail.com", account.getEmailId());
	}

	@Test
	public void getAccountBalanceTest() {
		long id=1000000003;
		Account account=bankService.getByIdAcc(id);
		System.out.println(account.getAccountBalance());
		assertEquals(10074, account.getAccountBalance());
	}
	
	@Test
	public void createTransactionTest() {
		Transaction transaction=new Transaction(1000000001,1000000002,1000);
		transaction.setTransactionAt(dateTime);
		assertEquals("valid", bankService.validateTransaction(transaction));
		bankService.createTransaction(transaction);
		bankService.updateAccountBalanceAfterTransaction(transaction);
		
	}
	
}
