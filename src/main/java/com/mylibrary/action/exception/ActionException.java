package com.mylibrary.action.exception;

public class ActionException extends Exception {

    public ActionException() {
        super();
    }

    public ActionException(String message) {
        super(message);
    }

    public ActionException(Exception e) {
        super(e);
    }

    public ActionException(String message, Exception e) {
        super(message, e);
    }
}
