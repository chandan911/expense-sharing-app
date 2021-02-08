package com.technogise.expensesharingapp.auths;

import com.technogise.expensesharingapp.models.ResultEntity;
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
  public Optional<ResultEntity<String>> authenticateLoginRequest(UserAuthRequest userAuthRequest) {
    Optional<User> mayBeExistingUser = userService.getUserByPhoneNumber(userAuthRequest.getPhoneNumber());
    return mayBeExistingUser.map(user -> getAuthToken(user, userAuthRequest));
  }

  private ResultEntity<String> getAuthToken(User existingUser, UserAuthRequest userAuthRequest) {
    if (passwordEncoder.matches(userAuthRequest.getPassword(), existingUser.getPassword())) {
      String authToken = jwtTokenUtil.generateAuthTokenFor(existingUser);
      return new ResultEntity<>(authToken);
    } else return ResultEntity.error("Invalid Credentials");
  }

  @Override
  public Long validateToken(String token) {
    return jwtTokenUtil.validateToken(token);
  }
}
