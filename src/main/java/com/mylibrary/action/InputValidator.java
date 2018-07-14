package com.mylibrary.action;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputValidator {

    public static boolean validateEmail(String email) {
        boolean isValid = false;
        if(email != null) {
            final String emailRegex = "^[\\w-+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-z]{2,})$";
            Pattern pattern = Pattern.compile(emailRegex);
            Matcher matcher = pattern.matcher(email);
            isValid = matcher.matches();
        }
        return isValid;
    }

    public static boolean validatePassword(String password) {
        boolean isValid = false;
        if(password != null) {
            final String passwordRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
            Pattern pattern = Pattern.compile(passwordRegex);
            Matcher matcher = pattern.matcher(password);
            isValid = matcher.matches();
        }
        return isValid;
    }

    public static boolean validateText(String text) {
        boolean isValid = false;
        if(text != null && !text.trim().isEmpty()) {
            isValid = true;
        }
        return isValid;
    }

    public static boolean validateNumber(String number) {
        boolean isValid = false;
        if(number != null) {
            final String passwordRegex = "[0-9]+";
            Pattern pattern = Pattern.compile(passwordRegex);
            Matcher matcher = pattern.matcher(number);
            isValid = matcher.matches();
        }
        return isValid;
    }

    public static boolean validateInteger(String stringInteger) {
        boolean isValid;
        try {
            Integer.parseInt(stringInteger);
            isValid = true;
        } catch (NumberFormatException e) {
            isValid = false;
        }
        return isValid;
    }
}