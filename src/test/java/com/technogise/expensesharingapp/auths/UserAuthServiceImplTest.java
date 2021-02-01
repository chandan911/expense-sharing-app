package com.technogise.expensesharingapp.auths;

import com.technogise.expensesharingapp.models.ActionResult;
import com.technogise.expensesharingapp.models.User;
import com.technogise.expensesharingapp.models.UserAuthRequest;
import com.technogise.expensesharingapp.services.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

@SpringBootTest
@ActiveProfiles("test")
public class UserAuthServiceImplTest {
    @Mock
    private UserService mockUserService;

    @Mock
    private BCryptPasswordEncoder mockPasswordEncoder;

    @Mock
    private JwtTokenUtil mockJwtTokenUtil;

    @InjectMocks
    private UserAuthServiceImpl mockUserAuthServiceImpl;


    @Test
    public void testAuthenticateLoginRequestWithRightCredentials(){
        User user = new User("DemoUser","12345","9898989898");
        UserAuthRequest userAuthRequest = new UserAuthRequest(user.getPhoneNumber(),user.getPassword());
        Mockito.when(mockUserService.getUserByPhoneNumber(userAuthRequest.getPhoneNumber())).thenReturn(java.util.Optional.of(user));
        Mockito.when(mockJwtTokenUtil.generateAuthTokenFor(user)).thenReturn("12345");
        Mockito.when(mockPasswordEncoder.matches(userAuthRequest.getPassword(),user.getPassword())).thenReturn(true);
        Optional<ActionResult<String>> loginResult = mockUserAuthServiceImpl.authenticateLoginRequest(userAuthRequest);
        Assertions.assertTrue(loginResult.get().isSuccess());
        Assertions.assertEquals("12345",loginResult.get().getResult());
    }

    @Test
    public void testAuthenticateLoginRequestWithWrongCredentials(){
        User user = new User("DemoUser","12345","9898989898");
        UserAuthRequest userAuthRequest = new UserAuthRequest(user.getPhoneNumber(),user.getPassword());
        Mockito.when(mockUserService.getUserByPhoneNumber(userAuthRequest.getPhoneNumber())).thenReturn(java.util.Optional.of(user));
        Mockito.when(mockJwtTokenUtil.generateAuthTokenFor(user)).thenReturn("12345");
        Mockito.when(mockPasswordEncoder.matches(userAuthRequest.getPassword(),user.getPassword())).thenReturn(false);
        Optional<ActionResult<String>> loginResult = mockUserAuthServiceImpl.authenticateLoginRequest(userAuthRequest);
        Assertions.assertFalse(loginResult.get().isSuccess());
        Assertions.assertEquals("Invalid Credentials",loginResult.get().getErrorMessage());
    }
}

