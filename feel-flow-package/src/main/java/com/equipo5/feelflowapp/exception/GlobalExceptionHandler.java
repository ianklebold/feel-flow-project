package com.equipo5.feelflowapp.exception;

import com.equipo5.feelflowapp.dto.response.ErrorResponseDto;
import com.equipo5.feelflowapp.exception.badrequest.badge.BadgeIsNotPossibleAssignException;
import com.equipo5.feelflowapp.exception.badrequest.invitation.InvitationException;
import com.equipo5.feelflowapp.exception.badrequest.module.ModuleException;
import com.equipo5.feelflowapp.exception.badrequest.user.UserException;
import com.equipo5.feelflowapp.exception.notfound.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler{

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleBindErrors(MethodArgumentNotValidException exception){

        var errorList = exception.getFieldErrors().stream()
                .map(fieldError -> {
                    Map<String, String > errorMap = new HashMap<>();
                    errorMap.put(fieldError.getField(), fieldError.getDefaultMessage());
                    return errorMap;
                }).toList();

        return ResponseEntity.badRequest().body(errorList);
    }


    @ExceptionHandler(BadgeIsNotPossibleAssignException.class)
    public ResponseEntity<ErrorResponseDto> handleBadgeIsNotPossibleAssignException(BadgeIsNotPossibleAssignException exception, WebRequest webRequest){

        ErrorResponseDto errorResponseDto = new ErrorResponseDto(exception,webRequest.getDescription(false),HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(errorResponseDto,HttpStatus.BAD_REQUEST);
    }



    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleGlobalNotFoundException(NotFoundException exception, WebRequest webRequest){

        ErrorResponseDto errorResponseDto = new ErrorResponseDto(exception,webRequest.getDescription(false),HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(errorResponseDto,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvitationException.class)
    public ResponseEntity<ErrorResponseDto> handleGlobalInvitationException(InvitationException exception, WebRequest webRequest){

        ErrorResponseDto errorResponseDto = new ErrorResponseDto(exception,webRequest.getDescription(false),HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(errorResponseDto,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ModuleException.class)
    public ResponseEntity<ErrorResponseDto> handleModuleException(ModuleException exception, WebRequest webRequest){


        ErrorResponseDto errorResponseDto = new ErrorResponseDto(exception,webRequest.getDescription(false),HttpStatus.BAD_REQUEST);


        return new ResponseEntity<>(errorResponseDto,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleGlobalException(Exception exception, WebRequest webRequest){

        ErrorResponseDto errorResponseDto = new ErrorResponseDto(exception,webRequest.getDescription(false),HttpStatus.INTERNAL_SERVER_ERROR);

        return new ResponseEntity<>(errorResponseDto,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UserException.class)
    public ResponseEntity<ErrorResponseDto> handleGlobalException(UserException exception, WebRequest webRequest){

        ErrorResponseDto errorResponseDto = new ErrorResponseDto(exception,webRequest.getDescription(false),HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(errorResponseDto,HttpStatus.BAD_REQUEST);
    }

}
