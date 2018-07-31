package com.epam.mylibrary.validator;

class EqualValidator {

    static boolean isEqualValid(String text1, String text2) {
        return text1 != null && text1.equals(text2);
    }
}
