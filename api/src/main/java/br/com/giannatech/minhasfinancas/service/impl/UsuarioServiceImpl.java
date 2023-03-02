package br.com.giannatech.minhasfinancas.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import br.com.giannatech.minhasfinancas.exceptions.RegraNegocioException;
import br.com.giannatech.minhasfinancas.exceptions.ErroAutenticacaoException;
import br.com.giannatech.minhasfinancas.models.entity.Usuario;
import br.com.giannatech.minhasfinancas.repository.UsuarioRepository;
import br.com.giannatech.minhasfinancas.service.UsuarioService;

@Service
public class UsuarioServiceImpl implements UsuarioService {

  private final UsuarioRepository repository;

  public UsuarioServiceImpl(UsuarioRepository repository) {
    this.repository = repository;
  }

  @Override
  public Usuario autenticar(String email, String senha) {
    return repository.findByEmailAndSenha(email, senha).orElseThrow(
        () -> new ErroAutenticacaoException("Não foi encontrado usuário com os dados informados."));

  }

  @Override
  @Transactional
  public Usuario salvarUsuario(Usuario usuario) {
    validarEmail(usuario.getEmail());
    return repository.save(usuario);
  }

  @Override
  public void validarEmail(String email) {
    if (repository.existsByEmail(email)) {
      throw new RegraNegocioException("Já existe um usuário cadastrado com este email");
    }

  }

}
