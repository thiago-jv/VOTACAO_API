package com.votacao.infra.exception.handler;


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

import java.time.OffsetDateTime;

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


}
