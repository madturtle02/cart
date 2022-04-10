package com.technova.shopping_cart.TechNova.Cart.globalexception;

import com.technova.shopping_cart.TechNova.Cart.exception.UserNameNotFound;
import com.technova.shopping_cart.TechNova.Cart.utils.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
@Slf4j
public class GlobalException extends ResponseEntityExceptionHandler {
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request){
        List<String> errorList = new ArrayList<>();
        List<ObjectError> errors = ex.getBindingResult().getAllErrors();
        for(ObjectError err:errors){
            errorList.add(err.getDefaultMessage());
            log.info("error is: {}"+err);
        }
        return ApiResponse.generateResponse(HttpStatus.BAD_REQUEST.value(), "field error",null,errorList);
    }
    @ExceptionHandler(UserNameNotFound.class)
    public ResponseEntity<Object> handlerException(Exception ex){
        return ApiResponse.generateResponse(HttpStatus.BAD_REQUEST.value(), "Username exception found",null,ex.getMessage());
    }
}
