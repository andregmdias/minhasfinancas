package br.com.giannatech.minhasfinancas.resources;

import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import br.com.giannatech.minhasfinancas.dto.UsuarioDto;
import br.com.giannatech.minhasfinancas.exceptions.RegraNegocioException;
import br.com.giannatech.minhasfinancas.models.entity.Usuario;
import br.com.giannatech.minhasfinancas.service.UsuarioService;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioResource {

  private UsuarioService service;

  public UsuarioResource(UsuarioService service) {
    this.service = service;
  }

  @PostMapping
  public ResponseEntity<Object> salvar(@Valid @RequestBody UsuarioDto dto) {

    var usuarioParams =
        Usuario.builder().nome(dto.getNome()).email(dto.getEmail()).senha(dto.getSenha()).build();

    try {
      var usuarioSalvo = service.salvarUsuario(usuarioParams);
      return new ResponseEntity<>(usuarioSalvo, HttpStatus.CREATED);
    } catch (RegraNegocioException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }
}
