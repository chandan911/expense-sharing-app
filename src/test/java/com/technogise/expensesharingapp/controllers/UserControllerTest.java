package com.technogise.expensesharingapp.controllers;

import com.technogise.expensesharingapp.auths.UserAuthService;
import com.technogise.expensesharingapp.exceptions.AuthFailedException;
import com.technogise.expensesharingapp.models.ResultEntity;
import com.technogise.expensesharingapp.models.User;
import com.technogise.expensesharingapp.models.UserAuthRequest;
import com.technogise.expensesharingapp.responseModels.AggregateDataResponse;
import com.technogise.expensesharingapp.responseModels.DebtResponse;
import com.technogise.expensesharingapp.responseModels.ExpenseResponse;
import com.technogise.expensesharingapp.services.DebtService;
import com.technogise.expensesharingapp.services.ExpenseService;
import com.technogise.expensesharingapp.services.UserService;
import com.technogise.expensesharingapp.util.ResponseGenerator;
import com.technogise.expensesharingapp.validators.Validator;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class UserControllerTest {

  @MockBean
  private ExpenseService mockExpenseService;

  @MockBean
  private DebtService mockDebtService;

  @MockBean
  private UserService mockUserService;

  @MockBean
  private Validator mockValidator;

  @MockBean
  private UserAuthService mockUserAuthService;

  @MockBean
  private ResponseGenerator mockResponseGenerator;

  @Autowired
  private MockMvc mockMvc;

  @Test
  void testGetAllUsers() throws Exception {
    User user1 = new User("shubham", "pass_1", "9304012345");
    User user2 = new User("sahil", "pass_2", "9304012346");
    User user3 = new User("satyam", "pass_3", "9304012347");

    List<User> expectedUsers = List.of(user1, user2, user3);

    Mockito.when(mockUserService.getAllUsers()).thenReturn(expectedUsers);

    mockMvc.perform(MockMvcRequestBuilders.get("/users")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().is(200))
        .andExpect(jsonPath("$[0].name", is(user1.getName())))
        .andExpect(jsonPath("$[1].name", is(user2.getName())))
        .andExpect(jsonPath("$[2].name", is(user3.getName())))
        .andExpect(jsonPath("$[0].phoneNumber", is(user1.getPhoneNumber())))
        .andExpect(jsonPath("$[1].phoneNumber", is(user2.getPhoneNumber())))
        .andExpect(jsonPath("$[2].phoneNumber", is(user3.getPhoneNumber())));
  }

  @Test
  void testCreateNewUser() throws Exception {
    final String userName = "test_user";
    final String password = "pass_word";
    final String phoneNumber = "7004843226";
    User user = new User(userName, password, phoneNumber);

    Mockito.when(mockValidator.validateUserName(userName)).thenReturn(true);
    Mockito.when(mockValidator.validateUserPassword(password)).thenReturn(true);
    Mockito.when(mockValidator.validateUserPhoneNumber(phoneNumber)).thenReturn(true);
    Mockito.when(mockUserService.createOrUpdate(user)).thenReturn(user);

    final String useRequestBody = "{\"id\":null,\"name\":\"test_user\",\"phoneNumber\":\"7004843226\",\"password\":\"pass_word\"}";
    mockMvc.perform(MockMvcRequestBuilders.post("/users")
        .content(useRequestBody)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().is(201))
        .andExpect(jsonPath("$.name", is(user.getName())))
        .andExpect(jsonPath("$.phoneNumber", is(user.getPhoneNumber())));
  }

  @Test
  void testCreateNewUserNameValidationFailure() throws Exception {
    final String userName = "";
    final String password = "pass_word";
    final String phoneNumber = "7004843226";
    User user = new User(userName, password, phoneNumber);

    Mockito.when(mockValidator.validateUserName(userName)).thenReturn(false);

    final String useRequestBody = "{\"id\":null,\"name\":\"\",\"phoneNumber\":\"7004843226\",\"password\":\"pass_word\"}";
    mockMvc.perform(MockMvcRequestBuilders.post("/users")
        .content(useRequestBody)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().is(400));
  }

  @Test
  void testCreateNewUserPasswordValidationFailure() throws Exception {
    final String userName = "test_name";
    final String password = "";
    final String phoneNumber = "7004843226";
    User user = new User(userName, password, phoneNumber);

    Mockito.when(mockValidator.validateUserName(userName)).thenReturn(true);
    Mockito.when(mockValidator.validateUserPassword(password)).thenReturn(false);

    final String useRequestBody = "{\"id\":null,\"name\":\"test_name\",\"phoneNumber\":\"7004843226\",\"password\":\"\"}";
    mockMvc.perform(MockMvcRequestBuilders.post("/users")
        .content(useRequestBody)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().is(400));
  }

  @Test
  void testCreateNewUserPhoneNumberValidationFailure() throws Exception {
    final String userName = "test_name";
    final String password = "pass_word";
    final String phoneNumber = "7004845543226";
    User user = new User(userName, password, phoneNumber);

    Mockito.when(mockValidator.validateUserName(userName)).thenReturn(true);
    Mockito.when(mockValidator.validateUserPassword(password)).thenReturn(true);
    Mockito.when(mockValidator.validateUserPhoneNumber(phoneNumber)).thenReturn(false);

    final String useRequestBody = "{\"id\":null,\"name\":\"test_name\",\"phoneNumber\":\"7004845543226\",\"password\":\"pass_word\"}";
    mockMvc.perform(MockMvcRequestBuilders.post("/users")
        .content(useRequestBody)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().is(400));
  }

  @Test
  void testCreateNewUserWithExistingPhoneNumber() throws Exception {
    final String userName = "test_user";
    final String password = "pass_word";
    final String phoneNumber = "7004843226";
    User user = new User(userName, password, phoneNumber);

    Mockito.when(mockValidator.validateUserName(userName)).thenReturn(true);
    Mockito.when(mockValidator.validateUserPassword(password)).thenReturn(true);
    Mockito.when(mockValidator.validateUserPhoneNumber(phoneNumber)).thenReturn(true);
    Mockito.when(mockUserService.createOrUpdate(user)).thenThrow(new DataIntegrityViolationException(""));

    final String useRequestBody = "{\"id\":null,\"name\":\"test_user\",\"phoneNumber\":\"7004843226\",\"password\":\"pass_word\"}";
    mockMvc.perform(MockMvcRequestBuilders.post("/users")
        .content(useRequestBody)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().is(409));
  }

  @Test
  void testLoginEntryPointWhenUserHaveNotRegister() throws Exception {

    UserAuthRequest userAuthRequest = new UserAuthRequest("9898989898", "12345");
    Mockito.when(mockUserAuthService.authenticateLoginRequest(userAuthRequest)).thenReturn(Optional.empty());

    final String useRequestBody = "{\"phoneNumber\":\"9898989898\",\"password\":\"12345\"}";
    mockMvc.perform(MockMvcRequestBuilders.post("/login")
        .content(useRequestBody)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
  }

  @Test
  void testLoginEntryPointWithRightCredentials() throws Exception {

    UserAuthRequest userAuthRequest = new UserAuthRequest("9898989898", "12345");
    ResultEntity<String> resultEntity = new ResultEntity<>("AuthToken");
    resultEntity.setSuccess(true);
    Mockito.when(mockUserAuthService.authenticateLoginRequest(userAuthRequest)).thenReturn(Optional.of(resultEntity));

    final String useRequestBody = "{\"phoneNumber\":\"9898989898\",\"password\":\"12345\"}";
    mockMvc.perform(MockMvcRequestBuilders.post("/login")
        .content(useRequestBody)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
  }

  @Test
  void testLoginEntryPointWithWrongPassword() throws Exception {

    UserAuthRequest userAuthRequest = new UserAuthRequest("9898989898", "12345");
    ResultEntity<String> resultEntity = ResultEntity.error("Invalid Credentials");
    resultEntity.setSuccess(false);
    Mockito.when(mockUserAuthService.authenticateLoginRequest(userAuthRequest)).thenReturn(Optional.of(resultEntity));

    final String useRequestBody = "{\"phoneNumber\":\"9898989898\",\"password\":\"12345\"}";
    mockMvc.perform(MockMvcRequestBuilders.post("/login")
        .content(useRequestBody)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isUnauthorized());
  }

  @Test
  void testGetAggregateDataWithInvalidToken() throws Exception {

    Mockito.when(mockUserAuthService.validateToken(any(String.class))).thenThrow(new AuthFailedException());

    mockMvc.perform(MockMvcRequestBuilders.get("/aggregated-data")
        .header("authToken", "dummyToken")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().is(401));
  }

  @Test
  void testGetAggregateDataWithValidToken() throws Exception {

    Date date = new Date();
    ExpenseResponse expenseResponse1 = new ExpenseResponse("Movie", 2600.0, "Shubham", date);
    ExpenseResponse expenseResponse2 = new ExpenseResponse("Dinner", 2800.0, "Satyam", date);
    ExpenseResponse expenseResponse3 = new ExpenseResponse("Lunch", 1700.0, "Satyam", date);
    List<ExpenseResponse> expenseResponses = List.of(expenseResponse1, expenseResponse2, expenseResponse3);

    DebtResponse debtResponse1 = new DebtResponse(1L, 105.0, "Shubham", "Amir");
    DebtResponse debtResponse2 = new DebtResponse(2L, 210.0, "Shubham", "Satyam");
    DebtResponse debtResponse3 = new DebtResponse(3L, 180.0, "Amir", "Satyam");
    List<DebtResponse> debtResponses = List.of(debtResponse1, debtResponse2, debtResponse3);

    User user = new User("sahu", "pass", "9304011012");

    User user1 = new User("chandan", "pass_1", "9304011010");
    User user2 = new User("sahil", "pass_2", "9304012346");
    User user3 = new User("satyam", "pass_3", "9304012347");
    List<User> users = new ArrayList<User>() {{
      add(user1);
      add(user2);
      add(user3);
    }};

    AggregateDataResponse expectedAggregateDataResponse = new AggregateDataResponse(expenseResponses, debtResponses, user, users);

    Mockito.when(mockUserAuthService.validateToken(any(String.class))).thenReturn(1L);
    Mockito.when(mockExpenseService.getAllExpensesByUserId(any(Long.class))).thenReturn(new ArrayList<>());
    Mockito.when(mockUserService.getUserById(any(Long.class))).thenReturn(Optional.of(new User()));
    Mockito.when(mockUserService.getAllUsers()).thenReturn(new ArrayList<User>());
    Mockito.when(mockResponseGenerator.aggregateResponseGenerator(any(), any(), any(), any()))
        .thenReturn(expectedAggregateDataResponse);


    mockMvc.perform(MockMvcRequestBuilders.get("/aggregated-data")
        .header("authToken", "dummyToken")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().is(200))
        .andExpect(jsonPath("$.expenses.[0].payerName", is(expenseResponse1.getPayerName())))
        .andExpect(jsonPath("$.expenses.[1].payerName", is(expenseResponse2.getPayerName())))
        .andExpect(jsonPath("$.expenses.[2].payerName", is(expenseResponse3.getPayerName())))
        .andExpect(jsonPath("$.debts.[0].creditor", is(debtResponse1.getCreditor())))
        .andExpect(jsonPath("$.debts.[1].creditor", is(debtResponse2.getCreditor())))
        .andExpect(jsonPath("$.debts.[2].creditor", is(debtResponse3.getCreditor())))
        .andExpect(jsonPath("$.otherUsers.[0].name", is(user1.getName())))
        .andExpect(jsonPath("$.otherUsers.[1].name", is(user2.getName())))
        .andExpect(jsonPath("$.otherUsers.[2].name", is(user3.getName())));
  }
}
