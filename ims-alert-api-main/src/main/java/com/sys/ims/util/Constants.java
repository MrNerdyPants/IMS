package com.sys.ims.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Component
public class Constants {
    public static final String SECRET_KEY = "E4F844C9969B182DEC9BF60094CBD643A3C3465801238B11F22873B9F5B94F4E";
    public static final String ADMIN_USER = "ADMIN";
    public static final String USER_USER = "USER";
    public static final String RECORD_NOT_FOUND = "Record not found";

    public static final String USER_ALREADY_EXISTS = "User Already Exists";

    public static final String USER_ALREADY_EXIST = "User Already exist";
    public static final String UPLOAD_DIR = "./uploads/";
    public static final String CV_FILE_PROPERTY = "cvFile";
    //====================EXCEPTIONS MESSAGE=================================
    public static final String INTERNAL_SERVER_ERROR = "Something went wrong. We will work on it.";
    public static final long FILE_SIZE_MB = 10;
    public static final long FILE_SIZE_BYTES = 1048576 * FILE_SIZE_MB;//"1048576" = 1 MB
    public static final String API_RESPONSE_BAD_REQUEST_FIELD = "Please verify field with valid data";
    public static final String API_RESPONSE_SUCCESS = "Success";
    public static final String API_RESPONSE_LOGIN_ALREADY_IN_USE = "Login Id/Username already in use!";
    public static final String API_RESPONSE_EMAIL_ALREADY_IN_USE = "Email already in use!";
    public static final String API_RESPONSE_NAME_ALREADY_IN_USE = "Name already in use!";
    public static final String API_RESPONSE_SHORT_NAME_ALREADY_IN_USE = "Short Name already in use!";
    public static final String API_RESPONSE_MODEL_TITLE_ALREADY_IN_USE = "Model Title already in use!";
    public static final String API_RESPONSE_CONTACT_ALREADY_IN_USE = "Contact number already in use!";
    public static final String API_RESPONSE_RECORD_NOT_FOUND = "Record not found!";
    //====================VALIDATION & Pattern=========================================
    public static final String REGEXP_ONLY_CHARACTERS_OR_SPACE = "^[a-zA-Z ]+$";

    public static final String REGEXP_FOR_PASSWORD = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[*.!@$%^&(){}[]:;<>,.?/~_+-=|*]]).{6,14}$";
    public static final String OLD_PASSWORD_DOES_NOT_MATCH = "Old password does not match";
    public static final String PASSWORD_MATCH = "New and old password should not be same.";
    public static final String API_RESPONSE_FILE_NOT_FOUND = "File not found!";
    public static final String API_RESPONSE_ERROR_COMMENTS_TOO_LARGE = "Comment is too long max allowed 200 characters";
    public static final String API_RESPONSE_ERROR_INPUT_TEXT_TOO_LARGE = "Text is too long max allowed 50 characters";
}
