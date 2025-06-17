package com.javanauta.usuario.business.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EnderecoDTO {
    private String rua;
    private Long numero;
    private String complemento;
    private String cidade;
    private String estado;
    private String cep;
}
