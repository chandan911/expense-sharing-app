package com.technogise.expensesharingapp.models;

import java.util.Optional;

public class ActionResult<T> {
    private Boolean isSuccess;
    private Optional<T> maybeResult;
    private Optional<String> maybeErrorMessage;

    public ActionResult(T result) {
        this.isSuccess = true;
        this.maybeResult = Optional.of(result);
        this.maybeErrorMessage = Optional.empty();
    }

    private ActionResult(String errorMessage) {
        this.isSuccess = false;
        this.maybeResult = Optional.empty();
        this.maybeErrorMessage = Optional.of(errorMessage);
    }

    public static ActionResult error(String errorMessage) {
        return new ActionResult<>(errorMessage);
    }

    public Boolean isSuccess() {
        return isSuccess;
    }

    public T getResult() {
        return maybeResult.get();
    }

    public String getErrorMessage() {
        return maybeErrorMessage.get();
    }

    public void setSuccess(Boolean success) {
        isSuccess = success;
    }
}

