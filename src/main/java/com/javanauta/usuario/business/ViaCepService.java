package com.javanauta.usuario.business;

import com.javanauta.usuario.infrastructure.client.ViaCEPClient;
import com.javanauta.usuario.infrastructure.client.ViaCepDTO;
import com.javanauta.usuario.infrastructure.exceptions.IllegalArgumentException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ViaCepService {

    private final ViaCEPClient viaCEPClient;

    public ViaCepDTO buscaDadosEndereco(String cep){
        return viaCEPClient.buscaDadosEndereco(processarCEP(cep));
    }

    private String processarCEP(String cep){
        String cepFormato = cep.replace(" ", "")
                .replace("-", "");

        if (!cepFormato.matches("\\d+") || !Objects.equals(cepFormato.length(), 8)){
            throw new IllegalArgumentException("O cep contém caractéres inválidos, favor, verificar");
        }

        return cepFormato;
    }

}
