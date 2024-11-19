package com.votacao.infra.exception.handler;


import com.votacao.infra.exception.EntidadeEmUsoException;
import com.votacao.infra.exception.EntidadeNaoEncontradaException;
import com.votacao.infra.exception.NegocioException;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EntidadeNaoEncontradaException.class)
    public ResponseEntity<?> tratarEntidadeNaoEncontradaException(
            EntidadeNaoEncontradaException ex, WebRequest request) {

        HttpStatus status = HttpStatus.NOT_FOUND;
        TipoProblema tipoProblema = TipoProblema.ENTIDADE_NAO_ENCONTRADA;

        Problema problema = createProblemaBuilder(status, tipoProblema, ex.getMessage())
                .mensagemUsuario(ex.getMessage())
                .build();

        return handleExceptionInternal(ex, problema, new HttpHeaders(),
                status, request);
    }

    @ExceptionHandler(NegocioException.class)
    public ResponseEntity<?> tratarNegocioException(NegocioException ex, WebRequest request) {

        HttpStatus status = HttpStatus.BAD_REQUEST;
        TipoProblema tipoProblema = TipoProblema.ERRO_NEGOCIO;

        Problema problema = createProblemaBuilder(status, tipoProblema, ex.getMessage())
                .mensagemUsuario(ex.getMessage())
                .build();

        return handleExceptionInternal(ex, problema, new HttpHeaders(),
                status, request);
    }

    private Problema.ProblemaBuilder createProblemaBuilder(HttpStatus status, TipoProblema tipoProblema, String detail) {
        return Problema.builder().status(status.value())
                .dataHora(OffsetDateTime.now())
                .status(status.value())
                .type(tipoProblema.getUri())
                .title(tipoProblema.getTitulo())
                .detail(detail);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException ex, WebRequest request) {

        HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
        TipoProblema tipoProblema = TipoProblema.DADOS_INVALIDOS;
        String detail = "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente.";

        List<Problema.Campo> errors = new ArrayList<>();
        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            Problema.Campo campo = new Problema.Campo(violation.getPropertyPath().toString(), violation.getMessage());
            errors.add(campo);
        }

        Problema problema = createProblemaBuilder(status, tipoProblema, detail)
                .mensagemUsuario(detail)
                .campos(errors)
                .build();

        return handleExceptionInternal(ex, problema, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(EntidadeEmUsoException.class)
    public ResponseEntity<?> tratarEntidadeEmUsoException(
            EntidadeEmUsoException ex, WebRequest request) {

        HttpStatus status = HttpStatus.CONFLICT;
        TipoProblema tipoProblema = TipoProblema.ENTIDADE_EM_USO;

        Problema problema = createProblemaBuilder(status, tipoProblema, ex.getMessage())
                .mensagemUsuario(ex.getMessage())
                .build();

        return handleExceptionInternal(ex, problema, new HttpHeaders(),
                status, request);
    }

}
