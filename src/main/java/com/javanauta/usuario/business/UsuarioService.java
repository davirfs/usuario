package com.javanauta.usuario.business;

import com.javanauta.usuario.business.dto.UsuarioDTO;
import com.javanauta.usuario.business.mapper.UsuarioMapper;
import com.javanauta.usuario.infrastructure.entity.Usuario;
import com.javanauta.usuario.infrastructure.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;

    public UsuarioDTO salvaUsuario(UsuarioDTO usuarioDTO){
        Usuario usuario = usuarioMapper.paraUsuario(usuarioDTO);
        return usuarioMapper.paraUsuarioDto(usuario = usuarioRepository.save(usuario));
    }
}
