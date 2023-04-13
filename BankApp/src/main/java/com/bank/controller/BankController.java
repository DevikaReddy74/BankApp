package com.bank.controller;

import java.time.LocalDateTime;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bank.entity.Account;
import com.bank.entity.Transaction;
import com.bank.service.BankService;
import com.bank.service.EmailService;

//@Controller and @responseBody
//we can send and request the object data in JSON or XML format
@RestController
@CrossOrigin("*")
@RequestMapping("/BankApp")
public class BankController {
	
	@Autowired
	private BankService bankService;
	
	@Autowired
	private EmailService emailService;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(BankController.class);
	
//	@RequestMapping(value = "/Account/add", method = RequestMethod.POST)
//	ResponseEntity is used to represent the Http response output like status code, headers, and body
//	RequestBody annotation maps the HttpRequest body into object
	@PostMapping("/Account/add")
	public ResponseEntity<Object> createAccount(@Valid @RequestBody Account account) {
		
		LOGGER.info("************** Creating An Account ****************");
		
		String message;
		
//		Checking weather the account number is exist or not and return true if exist
		LOGGER.info("Checking weather the Account Id exist or not");
		boolean account1 = bankService.validateAccountId(account.getAccountId());
		if(account1) {		
			message="Account number already exists!!";
			LOGGER.debug(message);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
		}
		
//		Checking weather the pan number is unique or not and returns true if exist
		LOGGER.info("Checking weather the PAN Number exist or not");
		boolean account2 = bankService.validatePan(account.getPan());
		if (account2) {
			message="PAN number already exists!!";
			LOGGER.debug(message);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
		}
		
//		Checking weather the age is between >18 and <60 and returns true if age is b/w 18 and 60
		LOGGER.info("Checking weather the age is between >18 and <60");
		boolean validAge=bankService.validateAge(account.getDob());

		if(validAge != true) {
			message="Age must be >18 and <60 to register an Account";
			LOGGER.debug(message);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
		}
		
		bankService.createOrUpdateAcc(account);
		message="New Account Created Successfully";
		LOGGER.info(message);		
		return ResponseEntity.status(HttpStatus.CREATED).body(message);
//		return new ResponseEntity<Account>(account,HttpStatus.CREATED);		
	}
	
	@GetMapping("/Account/getAll")
	public ResponseEntity<List<Account>> getAllAccount(){
		
		LOGGER.info("*********Getting all the Accounts**********");
		List<Account> accounts=bankService.getAllAcc();
		LOGGER.info("Get All Accounts Successfully");
		return new ResponseEntity<List<Account>>(accounts,HttpStatus.OK);
	}
	
	@GetMapping("/Account/get/{id}")
	public ResponseEntity<Account> getAccountById(@PathVariable("id") long id){
		LOGGER.info("***********Getting the Account By Id***********");
		Account account=bankService.getByIdAcc(id);
		LOGGER.info("Get the Account Successfully");
		return new ResponseEntity<Account>(account,HttpStatus.OK);
	}
	
	@GetMapping("/Account/getBal/{id}")
	public ResponseEntity<Double> getAccountBalance(@PathVariable("id") long id){
		LOGGER.info("***********Getting the Account Balance By Account Id***********");
		Account account=bankService.getByIdAcc(id);
		double balance=account.getAccountBalance();
		LOGGER.info("Get the Account Balance Successfully");
		return new ResponseEntity<Double>(balance,HttpStatus.OK);
	}

	
//	***************** Transactions ********************
	
	@PostMapping("/Transaction/add")
	public ResponseEntity<Object> createTransaction(@Valid @RequestBody Transaction transaction){
		
		LOGGER.info("*************Transaction************");
		
		LOGGER.info("Checking weather all the details are valid for a transaction");
		String message=bankService.validateTransaction(transaction);
		if(message=="valid") {
			
			transaction.setTransactionAt(LocalDateTime.now());
			bankService.createTransaction(transaction);
			LOGGER.info("Transaction Successfull");
			bankService.updateAccountBalanceAfterTransaction(transaction);
			LOGGER.info("Updated the Balance After Transaction");
			return ResponseEntity.status(HttpStatus.OK).body("Transaction Successful");
		}else {
			LOGGER.debug(message);
			return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(message);
		}
		
	}
	
	@GetMapping("/Transaction/getAll")
	public ResponseEntity<List<Transaction>> getAllTransactions(){
		
		LOGGER.info("***********Getting All The Transactions********");
		List<Transaction> transactions=bankService.getAllTrans();
		LOGGER.info("Get All the Transactions Successfully");
		return new ResponseEntity<List<Transaction>>(transactions,HttpStatus.OK);
	}
	
	@GetMapping("/Transaction/getAll/from/{fromId}")
	public ResponseEntity<Object> getAllTransactionsByFromId(@PathVariable("fromId") long fromId){
		LOGGER.info("*********Getting All the Debits From an Account***********");
		
		boolean valid=bankService.validateAccountId(fromId);
		if(valid == true) {			
			List<Transaction> transactions=bankService.findTransactionByFromAccountIdEquals(fromId);
			LOGGER.info("Success");
			return ResponseEntity.status(HttpStatus.OK).body(transactions);
		}else {
			LOGGER.debug("Enter a Valid From Account Id");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Enter a Valid From Account Id");
		}
	}
	
	@GetMapping("/Transaction/getAll/to/{toId}")
	public ResponseEntity<Object> getAllTransactionsByToId(@PathVariable("toId") long toId){
		LOGGER.info("*******Getting all the Credits To an Account*******");
		
		boolean valid=bankService.validateAccountId(toId);
		if(valid == true) {			
			List<Transaction> transactions=bankService.findTransactionByToAccountIdEquals(toId);
			LOGGER.info("Success");
			return ResponseEntity.status(HttpStatus.OK).body(transactions);
		}else {
			LOGGER.debug("Enter a Valid To Account Id");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Enter a Valid To Account Id");
		}
	}

	@GetMapping("/Transaction/getAll/{id}")
	public ResponseEntity<Object> getAllTransactionsOfAnAccount(@PathVariable("id") long id){
		LOGGER.info("*********Getting all credits and Debits of an Account********");
		
		boolean valid=bankService.validateAccountId(id);
		if(valid == true) {			
			List<Transaction> transactions=bankService.getAllCreditsAndDebitsOfAnAccount(id);
			LOGGER.info("Success");
			return ResponseEntity.status(HttpStatus.OK).body(transactions);
		}else {
			LOGGER.debug("Enter a Valid Account Id");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Enter a Valid Account Id");
		}
	}
	
	@GetMapping("/sendEmail")
	public String sendPromotionalEmails(){
		LOGGER.info("****** Sending Promotional email");
		String message=emailService.sendEmailAfterCheckingMab();
		LOGGER.info("Promotional email sent Successfully");
		return message;
	}
	
	
}
