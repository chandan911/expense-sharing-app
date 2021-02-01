package com.technogise.expensesharingapp.auths;

import com.technogise.expensesharingapp.models.ActionResult;
import com.technogise.expensesharingapp.models.User;
import com.technogise.expensesharingapp.models.UserAuthRequest;
import com.technogise.expensesharingapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserAuthServiceImpl implements UserAuthService {
    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    public Optional<ActionResult<String>> authenticateLoginRequest(UserAuthRequest userAuthRequest) {
        Optional<User> mayBeExistingUser = userService.getUserByPhoneNumber(userAuthRequest.getPhoneNumber());
        return mayBeExistingUser.map(user -> getAuthToken(user, userAuthRequest));
    }

    private ActionResult<String> getAuthToken(User existingUser, UserAuthRequest userAuthRequest) {
        if (passwordEncoder.matches(userAuthRequest.getPassword(), existingUser.getPassword())) {
            String authToken = jwtTokenUtil.generateAuthTokenFor(existingUser);
            return new ActionResult<>(authToken);
        } else return ActionResult.error("Invalid Credentials");
    }
}
