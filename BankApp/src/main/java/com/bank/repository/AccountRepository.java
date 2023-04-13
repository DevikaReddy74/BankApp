package com.bank.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bank.entity.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

	Account findByAccountIdEquals(long id);

	Account findByPanEquals(String pan);
	
	@Query("from Account where mab>=1000 AND accountBalance>0 AND accountStatus='Active'") 
	List<Account> checkMab();

}
