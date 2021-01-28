package com.technogise.expensesharingapp.controllers;

import com.technogise.expensesharingapp.auth.UserAuthService;
import com.technogise.expensesharingapp.models.UserAuthRequest;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class UserControllerTest {

    @MockBean
    private UserAuthService mockUserAuthService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testLoginEntryPointWhenUserHaveNotRegister() throws Exception {

        UserAuthRequest userAuthRequest = new UserAuthRequest("9898989898","12345");
        Mockito.when(mockUserAuthService.authenticateLoginRequest(userAuthRequest)).thenReturn(new ResponseEntity<String>("", HttpStatus.NOT_FOUND));

        final String useRequestBody = "{\"phoneNumber\":\"9898989898\",\"password\":\"12345\"}";
        mockMvc.perform(MockMvcRequestBuilders.post("/login")
                .content(useRequestBody)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void testLoginEntryPointWithRightCredentials() throws Exception {

        UserAuthRequest userAuthRequest = new UserAuthRequest("9898989898","12345");
        Mockito.when(mockUserAuthService.authenticateLoginRequest(userAuthRequest)).thenReturn(new ResponseEntity<String>("", HttpStatus.ACCEPTED));

        final String useRequestBody = "{\"phoneNumber\":\"9898989898\",\"password\":\"12345\"}";
        mockMvc.perform(MockMvcRequestBuilders.post("/login")
                .content(useRequestBody)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted());
    }

    @Test
    void testLoginEntryPointWithWrongPassword() throws Exception {

        UserAuthRequest userAuthRequest = new UserAuthRequest("9898989898","12345");
        Mockito.when(mockUserAuthService.authenticateLoginRequest(userAuthRequest)).thenReturn(new ResponseEntity<String>("", HttpStatus.UNAUTHORIZED));

        final String useRequestBody = "{\"phoneNumber\":\"9898989898\",\"password\":\"12345\"}";
        mockMvc.perform(MockMvcRequestBuilders.post("/login")
                .content(useRequestBody)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }
}
