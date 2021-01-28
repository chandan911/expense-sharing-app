package com.technogise.expensesharingapp.auth;

import com.technogise.expensesharingapp.models.User;
import com.technogise.expensesharingapp.models.UserAuthRequest;
import com.technogise.expensesharingapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component("UserAuthService")
public class UserAuthService {
    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    public ResponseEntity<String> authenticateLoginRequest(UserAuthRequest userAuthRequest){
        Optional<User> mayBeExistingUser = userService.getUserByPhoneNumber(userAuthRequest.getPhoneNumber());
        if(mayBeExistingUser.isPresent()){
            User existingUser = mayBeExistingUser.get();
            if(passwordEncoder.matches(userAuthRequest.getPassword(),existingUser.getPassword())) {
                String authToken = jwtTokenUtil.generateAuthTokenFor(existingUser);
                return (new ResponseEntity<String>(authToken, HttpStatus.ACCEPTED));
            }else {
                return (new ResponseEntity<String>("Incorrect Password", HttpStatus.UNAUTHORIZED));
            }
        }
        return (new ResponseEntity<String>("No Data Available, Sign up instead", HttpStatus.NOT_FOUND));
    }
}

