package com.fallon.banking.services;


import com.fallon.banking.exceptions.InvalidRequestException;
import com.fallon.banking.exceptions.ResourcePersistenceException;
import com.fallon.banking.models.User;
import com.fallon.banking.models.accounts.Account;
import com.fallon.banking.models.accounts.CheckingAccount;
import com.fallon.banking.models.accounts.SavingsAccount;
import com.fallon.banking.repositories.AccountRepository;
import com.fallon.banking.repositories.UserRepository;
import com.fallon.banking.web.dtos.CreateAccountDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    Predicate<CreateAccountDTO> isChecking;
    Predicate<CreateAccountDTO> isCheckingBalanceValid;
    Predicate<CreateAccountDTO> isSavings;
    Predicate<CreateAccountDTO> isSavingsBalanceValid;


    @PostConstruct
    public void init(){
        isChecking = createAccountDTO -> createAccountDTO.getType().equals("checking");
        isCheckingBalanceValid = isChecking.and(createAccountDTO -> createAccountDTO.getStartingBalance()>=20);
        isSavings = createAccountDTO -> createAccountDTO.getType().equals("savings");
        isSavingsBalanceValid = createAccountDTO -> createAccountDTO.getStartingBalance()>=200;
    }

    @Autowired
    public AccountService(AccountRepository accountRepository, UserRepository userRepository){
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;

    }

    @Transactional
    public Account createAccount(CreateAccountDTO createAccountDTO, int userID) throws InvalidRequestException, ResourcePersistenceException{
        Set<User> users = new HashSet<>();
        Account account = null;
        users.add(userRepository.findById(userID).orElseThrow(() -> new InvalidRequestException("User not found")));

        if(isChecking.test(createAccountDTO)){
            if (!isCheckingBalanceValid.test(createAccountDTO)) throw new InvalidRequestException("$20 minimum for starting new accounts");
            account = new CheckingAccount(createAccountDTO);
        }
        if(isSavings.test(createAccountDTO)){
            if(!isSavingsBalanceValid.test(createAccountDTO)) throw new InvalidRequestException("$200 dollar minimum for savings accounts");
            account = new SavingsAccount(createAccountDTO);
        }
        if(account == null) throw new ResourcePersistenceException("Accounts must be of type 'checking' or 'savings'");
        account.setUsers(users);
        return accountRepository.save(account);

    }






}
