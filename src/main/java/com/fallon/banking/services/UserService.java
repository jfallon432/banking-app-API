package com.fallon.banking.services;

import com.fallon.banking.exceptions.InvalidRequestException;
import com.fallon.banking.exceptions.ResourcePersistenceException;
import com.fallon.banking.models.User;
import com.fallon.banking.repositories.UserRepository;
import com.fallon.banking.web.dtos.RegisterAccountDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Period;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Transactional
public class UserService {
    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;

    }

    @Transactional
    public void register(RegisterAccountDTO registerAccountDTO) throws ResourcePersistenceException, InvalidRequestException{

        User newUser = new User(registerAccountDTO);

        if(isEmailTaken.test(userRepository, newUser)) throw new ResourcePersistenceException("this email is taken");
        if(isUsernameTaken.test(userRepository, newUser)) throw new ResourcePersistenceException("this username taken");
        if (isUserValid(newUser)) {
            userRepository.save(newUser);
        }

    }

    private boolean isUserValid(User user) throws InvalidRequestException {
        if(!isEmailValid.test(user)) throw new InvalidRequestException("this email is not valid");
        if(user.getFirstName() == null || isBlank.test(user.getFirstName())) throw new InvalidRequestException("missing first name");
        if(user.getUsername() == null || isBlank.test(user.getUsername())) throw new InvalidRequestException("missing username");
        if(user.getLastName() == null || isBlank.test(user.getLastName()) ) throw new InvalidRequestException("missing last name");
        if(user.getDob() == null || isBlank.test(user.getDob().toString())) throw new InvalidRequestException("missing Data of Birth");
        if(!isAdult.test(user)) throw  new InvalidRequestException("user is not of age");

        return true;
    }


    private static BiPredicate<UserRepository, User> isEmailTaken= (userRepository, user) -> userRepository.existsByEmail(user.getEmail());

    private static BiPredicate<UserRepository, User> isUsernameTaken = (userRepository, user) -> userRepository.existByUsername(user.getUsername());

    private static Predicate<User> isEmailValid = email -> Pattern.compile("^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$")
            .matcher(email.getEmail())
            .matches();

    private static Predicate<String> isBlank = name -> name.trim().equals("");

    private static Predicate<User> isAdult = user -> Period.between(user.getDob(), LocalDate.now()).getYears() >= 18;








}
