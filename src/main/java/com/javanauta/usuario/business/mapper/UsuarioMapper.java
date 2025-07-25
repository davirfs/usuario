package com.javanauta.usuario.business.mapper;

import com.javanauta.usuario.business.dto.EnderecoDTO;
import com.javanauta.usuario.business.dto.TelefoneDTO;
import com.javanauta.usuario.business.dto.UsuarioDTO;
import com.javanauta.usuario.infrastructure.entity.Endereco;
import com.javanauta.usuario.infrastructure.entity.Telefone;
import com.javanauta.usuario.infrastructure.entity.Usuario;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UsuarioMapper {

    public Usuario paraUsuario(UsuarioDTO usuarioDTO){
        return Usuario.builder()
                .nome(usuarioDTO.getNome())
                .email(usuarioDTO.getEmail())
                .senha(usuarioDTO.getSenha())
                .enderecos(paraListaEndereco(usuarioDTO.getEnderecos()))
                .telefones(paraListaTelefone(usuarioDTO.getTelefones()))
                .build();
    }

    public List<Endereco> paraListaEndereco(List<EnderecoDTO> enderecoDTO){
        return enderecoDTO.stream().map(this::paraEndereco).toList();
    }

    public Endereco paraEndereco(EnderecoDTO enderecoDTO){
        return Endereco.builder()
                .rua(enderecoDTO.getRua())
                .numero(enderecoDTO.getNumero())
                .complemento(enderecoDTO.getComplemento())
                .cidade(enderecoDTO.getCidade())
                .estado(enderecoDTO.getEstado())
                .cep(enderecoDTO.getCep())
                .build();
    }

    public List<Telefone> paraListaTelefone(List<TelefoneDTO> telefoneDTO){
        return telefoneDTO.stream().map(this::paraTelefone).toList();
    }

    public Telefone paraTelefone(TelefoneDTO telefoneDTO){
         return Telefone.builder()
                .numero(telefoneDTO.getNumero())
                .ddd(telefoneDTO.getDdd())
                .build();
    }

    public UsuarioDTO paraUsuarioDto(Usuario usuario){
        return UsuarioDTO.builder()
                .nome(usuario.getNome())
                .email(usuario.getEmail())
                .senha(usuario.getSenha())
                .enderecos(paraListaEnderecoDto(usuario.getEnderecos()))
                .telefones(paraListaTelefoneDto(usuario.getTelefones()))
                .build();
    }

    public List<EnderecoDTO> paraListaEnderecoDto(List<Endereco> endereco){
        return endereco.stream().map(this::paraEnderecoDto).toList();
    }

    public EnderecoDTO paraEnderecoDto(Endereco endereco){
        return EnderecoDTO.builder()
                .id(endereco.getId())
                .rua(endereco.getRua())
                .numero(endereco.getNumero())
                .complemento(endereco.getComplemento())
                .cidade(endereco.getCidade())
                .estado(endereco.getEstado())
                .cep(endereco.getCep())
                .build();
    }

    public List<TelefoneDTO> paraListaTelefoneDto(List<Telefone> telefone){
        return telefone.stream().map(this::paraTelefoneDto).toList();
    }

    public TelefoneDTO paraTelefoneDto(Telefone telefone){
        return TelefoneDTO.builder()
                .id(telefone.getId())
                .numero(telefone.getNumero())
                .ddd(telefone.getDdd())
                .build();
    }

    public Usuario updateUsuario(UsuarioDTO usuarioDTO, Usuario usuario){
        return Usuario.builder()
                .nome(usuarioDTO.getNome() != null ? usuarioDTO.getNome() : usuario.getNome())
                .id(usuario.getId())
                .senha(usuarioDTO.getSenha() != null ? usuarioDTO.getSenha() : usuario.getSenha())
                .email(usuarioDTO.getEmail() != null ? usuarioDTO.getEmail() : usuario.getEmail())
                .enderecos(usuario.getEnderecos())
                .telefones(usuario.getTelefones())
                .build();
    }

    public Endereco updateEndereco(EnderecoDTO enderecoDTO, Endereco endereco){
        return Endereco.builder()
                .id(endereco.getId())
                .rua(enderecoDTO.getRua() != null ? enderecoDTO.getRua() : endereco.getRua())
                .numero(enderecoDTO.getNumero() != null ? enderecoDTO.getNumero() : endereco.getNumero())
                .complemento(enderecoDTO.getComplemento() != null ? enderecoDTO.getComplemento() : endereco.getComplemento())
                .cidade(enderecoDTO.getCidade() != null ? enderecoDTO.getCidade() : endereco.getCidade())
                .estado(enderecoDTO.getEstado() != null ? enderecoDTO.getEstado() : endereco.getEstado())
                .cep(enderecoDTO.getCep() != null ? enderecoDTO.getCep() : endereco.getCep())
                .build();
    }

    public Telefone updateTelefone(TelefoneDTO telefoneDTO, Telefone telefone){
        return Telefone.builder()
                .id(telefone.getId())
                .numero(telefoneDTO.getNumero() != null ? telefoneDTO.getNumero() : telefone.getNumero())
                .ddd(telefoneDTO.getDdd() != null ? telefoneDTO.getDdd() : telefone.getDdd())
                .build();
    }

    public Endereco paraEnderecoEntity(EnderecoDTO enderecoDTO, Long idUsuario){
        return Endereco.builder()
                .rua(enderecoDTO.getRua())
                .numero(enderecoDTO.getNumero())
                .complemento(enderecoDTO.getComplemento())
                .estado(enderecoDTO.getEstado())
                .cidade(enderecoDTO.getCidade())
                .cep(enderecoDTO.getCep())
                .usuario_id(idUsuario)
                .build();

    }

    public Telefone paraTelefoneEntity(TelefoneDTO telefoneDTO, Long idUsuario){
        return Telefone.builder()
                .numero(telefoneDTO.getNumero())
                .ddd(telefoneDTO.getDdd())
                .id(idUsuario)
                .build();
    }
}
