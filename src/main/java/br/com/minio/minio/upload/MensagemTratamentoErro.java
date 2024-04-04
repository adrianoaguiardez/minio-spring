package br.com.minio.minio.upload;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class MensagemTratamentoErro {

    private LocalDateTime dataTempo;

    private Integer status;

    private String erro;

    private String path;
    
}
