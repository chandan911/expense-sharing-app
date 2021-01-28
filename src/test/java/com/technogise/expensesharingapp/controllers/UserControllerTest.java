package com.technogise.expensesharingapp.controllers;

import com.technogise.expensesharingapp.models.User;
import com.technogise.expensesharingapp.services.UserService;
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

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class UserControllerTest {

  @MockBean
  private UserService mockUserService;

  @MockBean
  private Validator mockValidator;

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
}
