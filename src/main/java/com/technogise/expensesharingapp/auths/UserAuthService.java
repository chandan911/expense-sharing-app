package com.technogise.expensesharingapp.auths;

import com.technogise.expensesharingapp.models.ActionResult;
import com.technogise.expensesharingapp.models.UserAuthRequest;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component("userAuthService")
public interface UserAuthService {
    Optional<ActionResult<String>> authenticateLoginRequest(UserAuthRequest userAuthRequest);
}
