package com.sys.ims.exception;

import com.sys.ims.util.ApiResponse;
import com.sys.ims.util.Constants;
import com.sys.ims.util.ErrorDetails;
import com.sys.ims.util.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.SQLSyntaxErrorException;
import java.util.*;

//@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @Autowired
    Utility utility;

    @ExceptionHandler(NoSuchElementException.class)
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public ResponseEntity<ApiResponse> noRecordFoundException(NoSuchElementException ex) {
        return new ResponseEntity<>(utility.buildApiResponse(HttpStatus.NOT_FOUND.value(), Constants.RECORD_NOT_FOUND, ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    @ResponseStatus(code = HttpStatus.CONFLICT)
    public ResponseEntity<ApiResponse> userAlreadyExistException(SQLIntegrityConstraintViolationException ex) {
        return new ResponseEntity<>(utility.buildApiResponse(HttpStatus.CONFLICT.value(), Constants.USER_ALREADY_EXIST, null), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ResponseEntity<ApiResponse> userAlreadyExistException(IllegalArgumentException ex) {
        return new ResponseEntity<>(utility.buildApiResponse(HttpStatus.BAD_REQUEST.value(), Constants.RECORD_NOT_FOUND, null), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SQLSyntaxErrorException.class)
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ApiResponse> userAlreadyExistException(SQLSyntaxErrorException ex) {
        return new ResponseEntity<>(utility.buildApiResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), Constants.INTERNAL_SERVER_ERROR, null), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NullPointerException.class)
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public ResponseEntity<ApiResponse> noRecordFoundException(NullPointerException ex) {
        return new ResponseEntity<>(utility.buildApiResponse(HttpStatus.NOT_FOUND.value(), Constants.RECORD_NOT_FOUND, ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException ex,
                                                                  final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
        final List<ErrorDetails> errors = new ArrayList<>();
        for (final FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(new ErrorDetails(error.getField(), error.getDefaultMessage()));
        }
        return handleExceptionInternal(ex, utility.buildApiResponse(HttpStatus.BAD_REQUEST.value(), Constants.API_RESPONSE_BAD_REQUEST_FIELD, errors), headers, HttpStatus.BAD_REQUEST, request);
    }

    @Override
    protected ResponseEntity<Object> handleBindException(final BindException ex, final HttpHeaders headers,
                                                         final HttpStatus status, final WebRequest request) {
        final List<ErrorDetails> errors = new ArrayList<>();
        for (final FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(new ErrorDetails(error.getField(), error.getDefaultMessage()));
        }

        return handleExceptionInternal(ex, new ApiResponse(HttpStatus.BAD_REQUEST.value(), Constants.API_RESPONSE_BAD_REQUEST_FIELD, errors), headers, HttpStatus.BAD_REQUEST, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(final HttpMediaTypeNotSupportedException ex,
                                                                     final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
        final StringBuilder builder = new StringBuilder();
        builder.append(ex.getContentType());
        builder.append(" media type is not supported. Supported media types are ");
        ex.getSupportedMediaTypes().forEach(t -> builder.append(t + " "));
        return new ResponseEntity<>(utility.buildApiResponse(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value(), "Unsupported Media Type", builder.substring(0, builder.length() - 2)), new HttpHeaders(), HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }

    // 413
    @ExceptionHandler({MaxUploadSizeExceededException.class})
    public ResponseEntity<Object> handleFileSizeExceed(final MaxUploadSizeExceededException ex,
                                                       final WebRequest request) {
        return new ResponseEntity<>(utility.buildApiResponse(HttpStatus.PAYLOAD_TOO_LARGE.value(), Constants.API_RESPONSE_BAD_REQUEST_FIELD, "File size exceeded than " + Constants.FILE_SIZE_MB + " MB"), new HttpHeaders(), 413);
    }

    @ExceptionHandler(BaseException.EmailExistException.class)
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<ApiResponse> emailExistException(BaseException.EmailExistException ex) {
        Map<String, String> propertyError = new HashMap<>();
        propertyError.put("fieldName","email");
        propertyError.put("message",ex.getMessage());
        return new ResponseEntity<>(utility.buildApiResponse(HttpStatus.CONFLICT.value(), ex.getMessage(),propertyError ), HttpStatus.OK);
    }

    @ExceptionHandler(BaseException.NameExistException.class)
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<ApiResponse> nameExistException(BaseException.NameExistException ex) {
        Map<String, String> propertyError = new HashMap<>();
        propertyError.put("fieldName","name");
        propertyError.put("message",ex.getMessage());
        return new ResponseEntity<>(utility.buildApiResponse(HttpStatus.CONFLICT.value(), ex.getMessage(),propertyError ), HttpStatus.OK);
    }

    @ExceptionHandler(BaseException.ContactExistException.class)
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<ApiResponse> contactExistException(BaseException.ContactExistException ex) {
        Map<String, String> propertyError = new HashMap<>();
        propertyError.put("fieldName","contact");
        propertyError.put("message",ex.getMessage());
        return new ResponseEntity<>(utility.buildApiResponse(HttpStatus.CONFLICT.value(), ex.getMessage(),propertyError ), HttpStatus.OK);
    }
}
