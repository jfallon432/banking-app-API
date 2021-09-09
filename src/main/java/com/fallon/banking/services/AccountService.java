package com.fallon.banking.services;

import com.fallon.banking.enums.Role;
import com.fallon.banking.exceptions.DataSourceException;
import com.fallon.banking.exceptions.InvalidRequestException;
import com.fallon.banking.exceptions.ResourcePersistenceException;
import com.fallon.banking.models.AccountRole;
import com.fallon.banking.models.accounts.Account;
import com.fallon.banking.models.accounts.CheckingAccount;
import com.fallon.banking.models.accounts.SavingsAccount;
import com.fallon.banking.repositories.AccountRepository;
import com.fallon.banking.repositories.AccountRoleRepository;
import com.fallon.banking.repositories.UserRepository;
import com.fallon.banking.web.dtos.AccountDTO;
import com.fallon.banking.web.dtos.CreateAccountDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.function.Predicate;

import static com.fallon.banking.enums.Role.CUSTODIAN;

@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private final AccountRoleRepository accountRoleRepository;
    Predicate<CreateAccountDTO> isChecking;
    Predicate<CreateAccountDTO> isCheckingBalanceValid;
    Predicate<CreateAccountDTO> isSavings;
    Predicate<CreateAccountDTO> isSavingsBalanceValid;


    @PostConstruct
    public void init() {
        isChecking = createAccountDTO -> createAccountDTO.getType().equals("checking");
        isCheckingBalanceValid = isChecking.and(createAccountDTO -> createAccountDTO.getStartingBalance() >= 20);
        isSavings = createAccountDTO -> createAccountDTO.getType().equals("savings");
        isSavingsBalanceValid = createAccountDTO -> createAccountDTO.getStartingBalance() >= 200;
    }

    @Autowired
    public AccountService(AccountRepository accountRepository, UserRepository userRepository, AccountRoleRepository accountRoleRepository) {
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
        this.accountRoleRepository = accountRoleRepository;

    }

    @Transactional
    public Account createAccount(CreateAccountDTO createAccountDTO, int userID) throws InvalidRequestException, ResourcePersistenceException {
        AccountRole role = new AccountRole();
        Set<AccountRole> roles = new HashSet<>();
        Account account = null;

        role.setUser(userRepository.findById(userID).orElseThrow(() -> new InvalidRequestException("User not found")));
        role.setRole(CUSTODIAN);
        roles.add(role);

        if (isChecking.test(createAccountDTO)) {
            if (!isCheckingBalanceValid.test(createAccountDTO))
                throw new InvalidRequestException("$20 minimum for starting new accounts");
            account = new CheckingAccount(createAccountDTO);
        }
        if (isSavings.test(createAccountDTO)) {
            if (!isSavingsBalanceValid.test(createAccountDTO))
                throw new InvalidRequestException("$200 dollar minimum for savings accounts");
            account = new SavingsAccount(createAccountDTO);
        }
        if (account == null) throw new ResourcePersistenceException("Accounts must be of type 'checking' or 'savings'");
        role.setAccount(accountRepository.save(account));
        accountRoleRepository.save(role);
        return role.getAccount();


    }

    @Transactional
    public List<AccountDTO> getUserAccounts(int userId) {
        List<AccountDTO> userAccountRole = new LinkedList<>();
        List<AccountRole> accountRoles = accountRoleRepository.getAccountRoleByUserId(userId);


        accountRoles.forEach(accountRole -> {
            AccountDTO accountDTO = new AccountDTO(accountRepository.findById(accountRole.getAccount()
                    .getId()).orElseThrow(() -> new DataSourceException("not found")));

            accountDTO.setRole(accountRole.getRole());

            userAccountRole.add(accountDTO);


        });


        return userAccountRole;

    }


}
