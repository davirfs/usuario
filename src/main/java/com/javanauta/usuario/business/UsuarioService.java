package com.javanauta.usuario.business;

import com.javanauta.usuario.business.dto.EnderecoDTO;
import com.javanauta.usuario.business.dto.TelefoneDTO;
import com.javanauta.usuario.business.dto.UsuarioDTO;
import com.javanauta.usuario.business.mapper.UsuarioMapper;
import com.javanauta.usuario.infrastructure.entity.Endereco;
import com.javanauta.usuario.infrastructure.entity.Telefone;
import com.javanauta.usuario.infrastructure.entity.Usuario;
import com.javanauta.usuario.infrastructure.exceptions.ConflictException;
import com.javanauta.usuario.infrastructure.exceptions.ResourceNotFoundException;
import com.javanauta.usuario.infrastructure.exceptions.UnauthorizedException;
import com.javanauta.usuario.infrastructure.repository.EnderecoRepository;
import com.javanauta.usuario.infrastructure.repository.TelefoneRepository;
import com.javanauta.usuario.infrastructure.repository.UsuarioRepository;
import com.javanauta.usuario.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final EnderecoRepository enderecoRepository;
    private final TelefoneRepository telefoneRepository;
    private final AuthenticationManager authenticationManager;

    public UsuarioDTO salvaUsuario(UsuarioDTO usuarioDTO){
        emailExiste(usuarioDTO.getEmail());
        usuarioDTO.setSenha(passwordEncoder.encode(usuarioDTO.getSenha()));
        Usuario usuario = usuarioMapper.paraUsuario(usuarioDTO);
        return usuarioMapper.paraUsuarioDto(usuarioRepository.save(usuario));
    }

    public String autenticarUsuario(UsuarioDTO usuarioDTO){
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(usuarioDTO.getEmail(), usuarioDTO.getSenha()));
            return jwtUtil.generateToken(authentication.getName());
        }catch (BadCredentialsException | UsernameNotFoundException | AuthorizationDeniedException e){
            throw new UnauthorizedException("Usuário ou senha inválidos: ", e.getCause());
        }
    }

    public UsuarioDTO buscaUsuarioPorEmail(String email){
        try {
            return usuarioMapper.paraUsuarioDto(
                    usuarioRepository.findByEmail(email).orElseThrow(
                    () -> new ResourceNotFoundException("Email não encontrado: " + email)
                    ));
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Email não encontrado: " + email);
        }
    }

    public void deletaUsuarioPorEmail(String email){
        usuarioRepository.deleteByEmail(email);
    }

    public void emailExiste(String email){
        try{
            boolean existe = verificaEmailExistente(email);
            if (existe){
                throw new ConflictException("Email já cadastrado!" + email);
            }
        } catch (ConflictException e) {
            throw new ConflictException("Email já cadastrado!", e.getCause());
        }
    }

    public boolean verificaEmailExistente(String email){
        return usuarioRepository.existsByEmail(email);
    }

    public UsuarioDTO atualizaDadosUsuario(String token, UsuarioDTO usuarioDTO){
        String email = jwtUtil.extractUsername(token.substring(7));

        usuarioDTO.setSenha(usuarioDTO.getSenha() != null ? passwordEncoder.encode(usuarioDTO.getSenha()) : null);

        Usuario usuarioEntity = usuarioRepository.findByEmail(email).orElseThrow(() ->
                new ResourceNotFoundException("Email não localizado!"));

        Usuario usuario = usuarioMapper.updateUsuario(usuarioDTO, usuarioEntity);

        return usuarioMapper.paraUsuarioDto(usuarioRepository.save(usuario));
    }

    public EnderecoDTO atualizaEndereco(Long idEndereco, EnderecoDTO enderecoDTO){

        Endereco enderecoEntity = enderecoRepository.findById(idEndereco).orElseThrow(
                () -> new ResourceNotFoundException("Id não econtrado: " + idEndereco)
        );

        Endereco endereco = usuarioMapper.updateEndereco(enderecoDTO, enderecoEntity);

        return usuarioMapper.paraEnderecoDto(enderecoRepository.save(endereco));
    }

    public TelefoneDTO atualizaTelefone(Long idTelefone, TelefoneDTO telefoneDTO){
        Telefone entity = telefoneRepository.findById(idTelefone).orElseThrow(
                () -> new ResourceNotFoundException("Id não encontrado: " + idTelefone)
        );

        Telefone telefone = usuarioMapper.updateTelefone(telefoneDTO, entity);

        return usuarioMapper.paraTelefoneDto(telefoneRepository.save(telefone));
    }

    public EnderecoDTO cadastroEndereco(String token, EnderecoDTO enderecoDTO){
        String email = jwtUtil.extractUsername(token.substring(7));

        Usuario usuario = usuarioRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("Email não encontrado: " + email)
        );

        Endereco endereco = usuarioMapper.paraEnderecoEntity(enderecoDTO, usuario.getId());
        Endereco enderecoEntity = enderecoRepository.save(endereco);

        return usuarioMapper.paraEnderecoDto(enderecoEntity);
    }

    public TelefoneDTO cadastroTelefone(String token, TelefoneDTO telefoneDTO){
        String email = jwtUtil.extractUsername(token.substring(7));

        Usuario usuario = usuarioRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("Email não enconstrado: " + email)
        );

        Telefone telefone = usuarioMapper.paraTelefoneEntity(telefoneDTO, usuario.getId());
        Telefone telefoneEntity = telefoneRepository.save(telefone);

        return usuarioMapper.paraTelefoneDto(telefoneEntity);
    }
}
