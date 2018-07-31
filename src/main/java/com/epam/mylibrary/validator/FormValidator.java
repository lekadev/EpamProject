package com.epam.mylibrary.validator;

import com.epam.mylibrary.constants.Const;
import javax.servlet.http.HttpServletRequest;

public class FormValidator {

    public static boolean isNewUserFormValid(HttpServletRequest req) {
        boolean isFormValid;
        String email = req.getParameter(Const.PARAM_EMAIL);
        String password = req.getParameter(Const.PARAM_PASSWORD);
        String passwordRepeated = req.getParameter(Const.PARAM_PASSWORD_REPEATED);
        String nameFirst = req.getParameter(Const.PARAM_NAME);
        String nameLast = req.getParameter(Const.PARAM_SURNAME);
        boolean isFieldsValid = FieldValidator.isEmailValid(email) &&
                                        FieldValidator.isTextValid(nameFirst) &&
                                            FieldValidator.isTextValid(nameLast);
        boolean isPasswordValid = FieldValidator.isPasswordValid(password) &&
                                    FieldValidator.isPasswordValid(passwordRepeated);
        boolean isPasswordMatch = FieldValidator.isEqualValid(password, passwordRepeated);
        if (!isFieldsValid) {
            req.getSession().setAttribute(Const.REGISTRATION_MESSAGE, Const.TEXT_INPUT_ERROR);
        } else if (!isPasswordValid) {
            req.getSession().setAttribute(Const.REGISTRATION_MESSAGE, Const.PASSWORD_INVALID_ERROR);
        } else if (!isPasswordMatch) {
            req.getSession().setAttribute(Const.REGISTRATION_MESSAGE, Const.PASSWORD_MATCH_ERROR);
        }
        isFormValid = isFieldsValid && isPasswordValid && isPasswordMatch;
        return isFormValid;
    }

    public static boolean isEditProfileFormValid(HttpServletRequest req) {
        boolean isFormValid;
        String nameFirstNew = req.getParameter(Const.PARAM_NAME);
        String nameLastNew = req.getParameter(Const.PARAM_SURNAME);
        isFormValid = FieldValidator.isTextValid(nameFirstNew) && FieldValidator.isTextValid(nameLastNew);
        if (!isFormValid) {
            req.getSession().setAttribute(Const.PROFILE_UPDATE_MESSAGE, Const.TEXT_INPUT_ERROR);
        }
        return isFormValid;
    }

    public static boolean isPasswordFormValid(HttpServletRequest req) {
        boolean isFormValid;
        String password = req.getParameter(Const.PARAM_PASSWORD);
        String passwordRepeated = req.getParameter(Const.PARAM_PASSWORD_REPEATED);
        boolean isFieldsValid = FieldValidator.isPasswordValid(password) &&
                                    FieldValidator.isPasswordValid(passwordRepeated);

        if (!isFieldsValid) {
            req.getSession().setAttribute(Const.PASSWORD_UPDATE_MESSAGE, Const.PASSWORD_INVALID_ERROR);
        }
        boolean isPasswordsMatch = FieldValidator.isEqualValid(password, passwordRepeated);
        if (!isPasswordsMatch) {
            req.getSession().setAttribute(Const.PASSWORD_UPDATE_MESSAGE, Const.PASSWORD_MATCH_ERROR);
        }
        isFormValid = isFieldsValid && isPasswordsMatch;
        return isFormValid;
    }

    public static boolean isBookFormValid(HttpServletRequest req) {
        boolean isFormValid;
        String title = req.getParameter(Const.PARAM_TITLE);
        String publisher = req.getParameter(Const.PARAM_PUBLISHER);
        String numberCopies = req.getParameter(Const.PARAM_COPIES);
        String[] idAuthors = req.getParameterValues(Const.PARAM_AUTHORS);
        boolean isFieldsValid = FieldValidator.isTextValid(title) &&
                                    FieldValidator.isTextValid(publisher) &&
                                        FieldValidator.isIntegerValid(numberCopies);
        boolean isAuthorSelected = (idAuthors != null);
        if (!isFieldsValid) {
            req.getSession().setAttribute(Const.BOOK_FORM_MESSAGE, Const.TEXT_INPUT_ERROR);
        } else if (!isAuthorSelected) {
            req.getSession().setAttribute(Const.BOOK_FORM_MESSAGE, Const.NO_AUTHOR_ERROR);
        }
        isFormValid = isFieldsValid && isAuthorSelected;
        return isFormValid;
    }

    public static boolean isAuthorFormValid(HttpServletRequest req) {
        boolean isFormValid;
        String nameFirst = req.getParameter(Const.PARAM_NAME);
        String nameLast = req.getParameter(Const.PARAM_SURNAME);
        isFormValid = FieldValidator.isTextValid(nameFirst) &&
                                    FieldValidator.isTextValid(nameLast);
        if (!isFormValid) {
            req.getSession().setAttribute(Const.AUTHOR_ADD_MESSAGE, Const.TEXT_INPUT_ERROR);
        }
        return isFormValid;
    }
}
