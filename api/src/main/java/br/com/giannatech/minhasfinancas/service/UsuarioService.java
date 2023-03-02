package br.com.giannatech.minhasfinancas.service;

import br.com.giannatech.minhasfinancas.models.entity.Usuario;

public interface UsuarioService {

  Usuario autenticar(String email, String senha);

  Usuario salvarUsuario(Usuario usuario);

  void validarEmail(String email);
}
