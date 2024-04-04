package br.com.minio.minio.upload;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

@ControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<MensagemTratamentoErro> objectNotFound(ObjectNotFoundException ex, HttpServletRequest request) {
        MensagemTratamentoErro error = new MensagemTratamentoErro(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(), ex.getMessage(),
                request.getRequestURI());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);

    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public  ResponseEntity<MensagemTratamentoErro> handleMaxSizeException(
            MaxUploadSizeExceededException ex,
            HttpServletRequest request,
            HttpServletResponse response) {

        MensagemTratamentoErro error = new MensagemTratamentoErro(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(), ex.getMessage(),
                request.getRequestURI());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }


   

}
