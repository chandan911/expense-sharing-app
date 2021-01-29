package com.technogise.expensesharingapp.auth;

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

@SpringBootTest
@ActiveProfiles("test")
public class UserAuthServiceTest {
    @Mock
    private UserService mockUserService;

    @Mock
    private BCryptPasswordEncoder mockPasswordEncoder;

    @Mock
    private JwtTokenUtil mockJwtTokenUtil;

    @InjectMocks
    private UserAuthService mockUserAuthService;

    @Test
    public void testAuthenticateLoginRequestWhenEnteredPhoneNumberNotPresentInDatabase(){
        User user = new User("DemoUser","12345","9898989898");
        Mockito.when(mockUserService.getUserByPhoneNumber(user.getPhoneNumber())).thenReturn(java.util.Optional.ofNullable(null));
        Mockito.when(mockJwtTokenUtil.generateAuthTokenFor(user)).thenReturn("12345");
        Mockito.when(mockPasswordEncoder.matches(user.getPassword(),mockPasswordEncoder.encode(user.getPassword())))
                .thenReturn(true);
        ResponseEntity<String> loginResponse = mockUserAuthService.authenticateLoginRequest(new UserAuthRequest(user.getPhoneNumber(), user.getPassword()));
        Assertions.assertEquals(HttpStatus.NOT_FOUND,loginResponse.getStatusCode());
    }

    @Test
    public void testAuthenticateLoginRequestWithRightCredentials(){
        User user = new User("DemoUser","12345","9898989898");
        UserAuthRequest userAuthRequest = new UserAuthRequest(user.getPhoneNumber(),user.getPassword());
        Mockito.when(mockUserService.getUserByPhoneNumber(userAuthRequest.getPhoneNumber())).thenReturn(java.util.Optional.of(user));
        Mockito.when(mockJwtTokenUtil.generateAuthTokenFor(user)).thenReturn("12345");
        Mockito.when(mockPasswordEncoder.matches(userAuthRequest.getPassword(),user.getPassword())).thenReturn(true);
        ResponseEntity<String> loginResponse = mockUserAuthService.authenticateLoginRequest(userAuthRequest);
        Assertions.assertEquals(HttpStatus.OK,loginResponse.getStatusCode());
        Assertions.assertEquals("12345",loginResponse.getBody());
    }

    @Test
    public void testAuthenticateLoginRequestWithWrongCredentials(){
        User user = new User("DemoUser","12345","9898989898");
        UserAuthRequest userAuthRequest = new UserAuthRequest(user.getPhoneNumber(),user.getPassword());
        Mockito.when(mockUserService.getUserByPhoneNumber(userAuthRequest.getPhoneNumber())).thenReturn(java.util.Optional.of(user));
        Mockito.when(mockJwtTokenUtil.generateAuthTokenFor(user)).thenReturn("12345");
        Mockito.when(mockPasswordEncoder.matches(userAuthRequest.getPassword(),user.getPassword())).thenReturn(false);
        ResponseEntity<String> loginResponse = mockUserAuthService.authenticateLoginRequest(userAuthRequest);
        Assertions.assertEquals(HttpStatus.UNAUTHORIZED,loginResponse.getStatusCode());
    }
}