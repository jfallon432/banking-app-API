package com.fallon.banking.services;

import com.fallon.banking.enums.Role;
import com.fallon.banking.exceptions.InvalidRequestException;
import com.fallon.banking.exceptions.ResourcePersistenceException;
import com.fallon.banking.models.User;
import com.fallon.banking.repositories.UserRepository;
import com.fallon.banking.web.dtos.AuthenticatedDTO;
import com.fallon.banking.web.dtos.LoginUserDTO;
import com.fallon.banking.web.dtos.RegisterUserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Period;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.regex.Pattern;

import static com.fallon.banking.enums.Role.ADULT;
import static com.fallon.banking.enums.Role.MINOR;

@Service
@Transactional
public class UserService {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;

    }

    @Transactional
    public void register(RegisterUserDTO registerUserDTO) throws ResourcePersistenceException, InvalidRequestException {
        registerUserDTO.setPassword(passwordEncoder.encode(registerUserDTO.getPassword()));
        User newUser = new User(registerUserDTO);

        if (isEmailTaken.test(userRepository, newUser)) throw new ResourcePersistenceException("this email is taken");
        if (isUsernameTaken.test(userRepository, newUser))
            throw new ResourcePersistenceException("this username taken");

        if(isAdult.test(newUser)) newUser.setAge(ADULT);
        else newUser.setAge(MINOR);
        if (isUserValid(newUser)) {
            userRepository.save(newUser);
        }

    }

    @Transactional
    public AuthenticatedDTO login(LoginUserDTO loginUserDTO) {
        User user = userRepository.getByUsernameAndPassword(loginUserDTO.getUsername(), loginUserDTO.getPassword())
                .orElseThrow(() -> new InvalidRequestException("User not found"));

        AuthenticatedDTO authenticatedDTO = new AuthenticatedDTO(user);

        return authenticatedDTO;
    }

    private boolean isUserValid(User user) throws InvalidRequestException {
        if (!isEmailValid.test(user)) throw new InvalidRequestException("this email is not valid");
        if (user.getFirstName() == null || isBlank.test(user.getFirstName()))
            throw new InvalidRequestException("missing first name");
        if (user.getUsername() == null || isBlank.test(user.getUsername()))
            throw new InvalidRequestException("missing username");
        if (user.getLastName() == null || isBlank.test(user.getLastName()))
            throw new InvalidRequestException("missing last name");
        if (user.getDob() == null || isBlank.test(user.getDob().toString()))
            throw new InvalidRequestException("missing Data of Birth");


        return true;
    }


    private static BiPredicate<UserRepository, User> isEmailTaken = (userRepository, user) -> userRepository.existsByEmail(user.getEmail());

    private static BiPredicate<UserRepository, User> isUsernameTaken = (userRepository, user) -> userRepository.existByUsername(user.getUsername());

    private static Predicate<User> isEmailValid = email -> Pattern.compile("^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$")
            .matcher(email.getEmail())
            .matches();

    private static Predicate<String> isBlank = name -> name.trim().equals("");

    private static Predicate<User> isAdult = user -> Period.between(user.getDob(), LocalDate.now()).getYears() >= 18;


}
