package com.epam.mylibrary.constants;

public class Const {

    // ERROR MESSAGES
    public static final String TEXT_INPUT_ERROR = "textInputError";
    public static final String EMAIL_EXIST_ERROR = "emailExistError";
    public static final String PASSWORD_INVALID_ERROR = "passwordPatternError";
    public static final String PASSWORD_MATCH_ERROR = "passwordMatchError";
    public static final String PASSWORD_UPDATE_SUCCESS = "passwordUpdateSuccess";
    public static final String LOGIN_ERROR = "loginError";
    public static final String BOOK_ADD_SUCCESS = "bookAddSuccess";
    public static final String NO_AUTHOR_ERROR = "noAuthorError";
    public static final String AUTHOR_ADD_SUCCESS = "authorAddSuccess";
    public static final String UPDATE_SUCCESS = "updateSuccess";
    public static final String REGISTRATION_SUCCESS = "registrationSuccess";


    // Paths
    public static final String FORWARD_NEW_AUTHOR_FORM = "new-author-form";
    public static final String FORWARD_BOOK_INFO = "book-info";
    public static final String FORWARD_CATALOGUE = "catalogue";
    public static final String FORWARD_PROFILE_FORM = "edit-profile-form";
    public static final String FORWARD_EDIT_BOOK_FORM = "edit-book-form";
    public static final String FORWARD_READER_PROFILE = "reader";
    public static final String FORWARD_LIBRARIAN_PROFILE = "librarian";
    public static final String FORWARD_START = "login";
    public static final String FORWARD_NEW_READER_FORM = "new-reader-form";

    public static final String REDIRECT_PROFILE = "profile";
    public static final String REDIRECT_CATALOGUE = "show-catalogue";
    public static final String REDIRECT_AUTHOR_FORM = "new-author";
    public static final String REDIRECT_PROFILE_EDIT_FORM = "edit-profile";
    public static final String REDIRECT_BOOK_EDIT_FORM = "edit-book";
    public static final String REDIRECT_START_PAGE = "start";
    public static final String REDIRECT_READER_FORM = "new-reader";


    // REQUEST ATTRIBUTES
    public static final String AUTHOR_ADD_MESSAGE = "authorAddMessage";
    public static final String BOOK_FORM_MESSAGE = "bookFormMessage";
    public static final String PROFILE_UPDATE_MESSAGE = "profileUpdateMessage";
    public static final String PASSWORD_UPDATE_MESSAGE = "passwordUpdateMessage";
    public static final String LOGIN_MESSAGE = "loginMessage";
    public static final String REGISTRATION_MESSAGE = "registrationMessage";

    public static final String ALL_AUTHORS = "allAuthors";
    public static final String ALL_STATUSES = "allStatuses";
    public static final String CATALOGUE = "catalogue";
    public static final String USER = "user";
    public static final String ROLE = "role";
    public static final String LANGUAGE = "language";
    public static final String ORDERS = "orders";
    public static final String LABELS = "labels";
    public static final String PASSWORD = "password";
    public static final String BOOK = "book";


    // REQUEST PARAMETERS
    public final static String PARAM_LANGUAGE = "language";
    public final static String PARAM_EMAIL = "email";
    public final static String PARAM_PASSWORD = "password";
    public final static String PARAM_PASSWORD_REPEATED = "passwordRepeated";
    public final static String PARAM_NAME = "nameFirst";
    public final static String PARAM_SURNAME = "nameLast";
    public final static String PARAM_ORDER_ID = "idOrder";
    public final static String PARAM_STATUS = "status";
    public final static String PARAM_BOOK_ID = "idBook";
    public final static String PARAM_TITLE = "title";
    public final static String PARAM_PUBLISHER = "publisher";
    public final static String PARAM_COPIES = "numberCopies";
    public final static String PARAM_AUTHORS = "selectedAuthors";


    // PARAMETERS FOR CONNECTION POOL
    public static final String DB_FILENAME = "database";
    public static final String DB_DRIVER = "driver";
    public static final String DB_URL = "url";
    public static final String DB_LOGIN = "login";
    public static final String DB_PASSWORD = "password";
    public static final String DB_POOL_SIZE = "poolSize";


    // NAMES OF TABLE COLUMNS IN DATABASE
    public static final String USER_ID = "id_user";
    public static final String USER_EMAIL = "email";
    public static final String USER_PASSWORD = "password";
    public static final String USER_NAME_FIRST = "name_first";
    public static final String USER_NAME_LAST = "name_last";
    public static final String USER_ROLE = "role";
    public static final String USER_DATE_REGISTERED = "date_registered";
    public static final String BOOK_ID = "id_book";
    public static final String BOOK_TITLE = "title";
    public static final String BOOK_PUBLISHER = "publisher";
    public static final String BOOK_NUMBER_COPIES = "number_copies";
    public static final String AUTHOR_ID = "id_author";
    public static final String AUTHOR_NAME_FIRST = "name_first";
    public static final String AUTHOR_NAME_LAST = "name_last";
    public static final String ORDER_ID = "id_order";
    public static final String ORDER_DATE = "date";
    public static final String ORDER_STATUS = "status";
    public static final String LABEL_NAME = "label";
    public static final String LABEL_TEXT = "text";
}
