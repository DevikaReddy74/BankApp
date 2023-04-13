package com.bank.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.bank.entity.Account;
import com.bank.repository.AccountRepository;

@Service
public class EmailService {

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private JavaMailSender javaMailSender;

	@Value("${spring.mail.username}")
	private String sender;

	public String sendEmailAfterCheckingMab() {

//		check mab>=1000 accounts to send an promotional emails
		List<Account> accounts = accountRepository.checkMab();
		
		if (!accounts.isEmpty()) {
//			System.out.println(accounts);	

			List<String> emialList = accounts.stream().map(Account::getEmailId).collect(Collectors.toList());

			String email = emialList.get(0);
//			Sending an email
			try {
				// Creating a simple mail message
				SimpleMailMessage mailMessage = new SimpleMailMessage();

				// Setting up necessary details
				mailMessage.setFrom(sender);
				mailMessage.setTo(email);
				mailMessage.setSubject("Promotional Email From MNO Bank");
				mailMessage.setText(
						"Dear Sir/Mam,\nGreetings from MNO bank,\n\nWe want to bring to your notice that we have an ongoing NFO which is "
								+ "MNO flexicap fund managed by Mr MNO.\nIf interested write back to us at ....@gmail.com.\n\nRegards,\nMNO Bank.");

				// Sending the mail. Uncomment the below line to sent an email
	            javaMailSender.send(mailMessage);

				return "Promotional email sent successfully";
			}
			// Catch block to handle the exceptions
			catch (Exception e) {
				System.out.println(e);
				return "Error while Sending Mail";
			}
		}else {
			return "No Active accounts maintain $1000 MAB to sent an Promotional Email";
		}

	}
}
