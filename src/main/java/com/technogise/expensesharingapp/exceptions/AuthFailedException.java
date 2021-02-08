package com.technogise.expensesharingapp.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNAUTHORIZED, reason = "User not authorized")
public class AuthFailedException extends RuntimeException {
}
