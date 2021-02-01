package com.technogise.expensesharingapp.auths;

import com.technogise.expensesharingapp.models.ResultEntity;
import com.technogise.expensesharingapp.models.UserAuthRequest;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component("userAuthService")
public interface UserAuthService {
  Optional<ResultEntity<String>> authenticateLoginRequest(UserAuthRequest userAuthRequest);
}
