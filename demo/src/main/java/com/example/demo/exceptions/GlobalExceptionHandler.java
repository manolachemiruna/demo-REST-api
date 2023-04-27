package com.example.demo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice // asa marcam clasa "globala" care se ocupa de exception handler

//avem  @ExceptionHandler pentru CustomException si RuntimeException
//daca in aplicatie apare AnotherException, clasa noastra nu va face nimic in legatura cu exceptia asta
//pentru ca nu am creat nicio metoda care sa fie adnotata cu @ExceptionHandler(AnotherException.class)
public class GlobalExceptionHandler {


    //de fiecare data cand in aplicatie apare o exceptie de tip CustomException
    //se va executa metoda handleConflictCustomException care practic returneaza catre frontend
    //un obiect de tipul ExceptionResponseDto
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ExceptionResponseDto> handleConflictCustomException(CustomException exception){

        ExceptionResponseDto responseDto = new ExceptionResponseDto(
                exception.getHttpStatus().value(),
                exception.getHttpStatus().getReasonPhrase(),
                exception.getMessage()
        );

        return ResponseEntity.ok().body(responseDto);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ExceptionResponseDto> handleConflictRuntimeException(RuntimeException exception){

        ExceptionResponseDto responseDto = new ExceptionResponseDto(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Something went wrong",
                "There is an internal server issue"
        );

        return ResponseEntity.ok().body(responseDto);
    }

}
