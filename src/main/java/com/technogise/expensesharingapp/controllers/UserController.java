package com.technogise.expensesharingapp.controllers;

import com.technogise.expensesharingapp.auths.UserAuthService;
import com.technogise.expensesharingapp.exceptions.ResourceNotFoundException;
import com.technogise.expensesharingapp.models.*;
import com.technogise.expensesharingapp.repositories.DebtRepository;
import com.technogise.expensesharingapp.responseModels.AggregateDataResponse;
import com.technogise.expensesharingapp.responseModels.ExpenseDebtResponse;
import com.technogise.expensesharingapp.services.DebtService;
import com.technogise.expensesharingapp.services.ExpenseDebtService;
import com.technogise.expensesharingapp.services.ExpenseService;
import com.technogise.expensesharingapp.services.UserService;
import com.technogise.expensesharingapp.util.ResponseGenerator;
import com.technogise.expensesharingapp.validators.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class UserController {

  private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

  @Autowired
  private UserService userService;

  @Autowired
  private Validator validator;

  @Autowired
  private UserAuthService userAuthService;

  @Autowired
  private ExpenseService expenseService;

  @Autowired
  private DebtService debtService;

  @Autowired
  private ResponseGenerator responseGenerator;

  @Autowired
  private ExpenseDebtService expenseDebtService;


  @CrossOrigin(origins = "*")
  @GetMapping("/users")
  public List<User> getAllUsers() {
    return userService.getAllUsers();
  }

  @CrossOrigin(origins = "*")
  @PostMapping(path = "/users", consumes = "application/json", produces = "application/json")
  public ResponseEntity<?> createOrUpdate(@RequestBody User user) {
    if (validator.validateUserName(user.getName()) &&
        validator.validateUserPassword(user.getPassword()) &&
        validator.validateUserPhoneNumber(user.getPhoneNumber())) {
      try {
        User returnedUser = userService.createOrUpdate(user);
        return new ResponseEntity<User>(returnedUser, HttpStatus.CREATED);
      } catch (DataIntegrityViolationException exception) {
        return new ResponseEntity<String>("Phone number already exists!", HttpStatus.CONFLICT);
      } catch (RuntimeException exception) {
        LOGGER.error(exception.getMessage(), exception.getCause());
        return new ResponseEntity<String>("An unexpected error occur!", HttpStatus.INTERNAL_SERVER_ERROR);
      }
    } else {
      return new ResponseEntity<String>("Invalid data!", HttpStatus.BAD_REQUEST);
    }
  }

  @CrossOrigin(origins = "*")
  @PostMapping(path = "/login", consumes = "application/json", produces = "application/text")
  public ResponseEntity<String> login(@RequestBody UserAuthRequest userAuthRequest) {
    Optional<ResultEntity<String>> maybeResult = userAuthService.authenticateLoginRequest(userAuthRequest);
    return maybeResult.map(result -> {
      if (result.isSuccess()) {
        return new ResponseEntity<>(result.getResult(), HttpStatus.OK);
      } else return new ResponseEntity<>(result.getErrorMessage(), HttpStatus.UNAUTHORIZED);
    }).orElseThrow(ResourceNotFoundException::new);
  }

  @CrossOrigin(origins = "*")
  @GetMapping(path = "/aggregated-data", produces = "application/json")
  public ResponseEntity<?> getAggregateData(@RequestHeader("authToken") String authToken) {
    Long userId = userAuthService.validateToken(authToken);
    List<Expense> expenses = expenseService.getAllExpensesByUserId(userId);
    List<Debt> debts = debtService.getAllDebtsByUserId(userId);
    User user = userService.getUserById(userId).get();
    List<User> allUsers = userService.getAllUsers();
    AggregateDataResponse aggregateDataResponse = responseGenerator.aggregateResponseGenerator(expenses, debts, user, allUsers);
    return new ResponseEntity<AggregateDataResponse>(aggregateDataResponse, HttpStatus.OK);
  }


  @PostMapping(path = "/expenses", consumes = "application/json", produces ="application/json")
  public ResponseEntity<?> expenses(@RequestHeader("authToken") String authToken, @RequestBody AddExpense addExpense) {

    Long payerId = userAuthService.validateToken(authToken);
    if(validator.validateUserId(addExpense.getPayerId()) &&
       addExpense.getAmount() > 0 &&
       addExpense.getDescription().length() > 0 &&
       addExpense.getDebtorId().size() >= 1 &&
       validator.validateDebtorList(addExpense.getDebtorId())) {
       try {
        Expense expense = new Expense(addExpense.getDescription(), addExpense.getAmount(), addExpense.getPayerId());
        expense = expenseService.createExpense(expense);

        ArrayList<Long> debtorId = addExpense.getDebtorId();
        for(Integer id  = 0; id < debtorId.size();id++) {
          ExpenseDebtor expenseDebtor = new ExpenseDebtor(expense.getId(), debtorId.get(id));
          if(expenseDebtor.getDebtorId() != addExpense.getPayerId()) {
            expenseDebtService.createExpenseDebt(expenseDebtor);
            Double debtAmount = (addExpense.getAmount()/debtorId.size());
            debtService.updateDebtRepository(addExpense.getPayerId(), expenseDebtor.getDebtorId(), debtAmount);
          }
        }
        List<Expense> expenses = expenseService.getAllExpensesByUserId(payerId);
        List<Debt> debts = debtService.getAllDebtsByUserId(payerId);
        User user = userService.getUserById(payerId).get();
        ExpenseDebtResponse expenseDebtResponse = responseGenerator.expenseDebtResponseGenerator(expenses, debts, user);
        return new ResponseEntity<ExpenseDebtResponse>(expenseDebtResponse, HttpStatus.OK);
      } catch (RuntimeException exception) {
        LOGGER.error(exception.getMessage(), exception.getCause());
        return new ResponseEntity<String>("An unexpected error occured!", HttpStatus.INTERNAL_SERVER_ERROR);
      }
    } else {
      return new ResponseEntity<String>("Invalid expense data", HttpStatus.BAD_REQUEST);
    }
  }
  @Autowired
  private DebtRepository debtRepository;

  @CrossOrigin(origins = "*")
  @PostMapping(path = "/add-debt", consumes = "application/json", produces = "application/json")
  public ResponseEntity<?> addDebt(@RequestBody Debt debt) {
    return new ResponseEntity<Debt>(debtRepository.save(debt), HttpStatus.CREATED);
  }
  @CrossOrigin(origins = "*")
  @GetMapping(path = "/show-debt")
  public ResponseEntity<?> showDebts() {
    return new ResponseEntity<List<Debt>>(debtRepository.findAll(), HttpStatus.ACCEPTED);
  }
}
